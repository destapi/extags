package works.hop.eztag.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.cli.*;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import works.hop.eztag.server.handler.IReqHandler;
import works.hop.eztag.server.router.AppRouter;
import works.hop.eztag.server.router.Router;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class App {

    static ContextHandlerCollection contextCollection = new ContextHandlerCollection();

    public static void runApp(String[] args, Consumer<App> consumer) {
        runApp(args, consumer, (supplier -> {
            Runtime.getRuntime().addShutdownHook(new Thread(supplier::get));
        }));
    }

    public static void runApp(String[] args, Consumer<App> consumer, Consumer<Supplier<Void>> stop) {
        final Options options = new Options();
        options.addOption(new Option("keystorePath", false, "path to keystore file."));
        options.addOption(new Option("keystorePassword", false, "keystore password"));
        options.addOption(new Option("securePort", true, "port for ssl connection"));
        options.addOption(new Option("selectors", true, "number of selector threads"));
        options.addOption(new Option("acceptors", true, "number of acceptor threads"));
        options.addOption(new Option("welcomeFiles", true, "comma-separated values of welcome files"));
        options.addOption(new Option("p", "port", true, "port for non-ssl connection"));
        options.addOption(new Option("h", "host", true, "dns name or ip address of server"));
        options.addOption(new Option("r", "resourcesDir", true, "static resources directory path"));

        try {
            CommandLineParser parser = new DefaultParser();
            // parse the command line arguments
            CommandLine line = parser.parse(options, args);

            try {
                App app = new App();
                Server server = app.configureServer(line);
                consumer.accept(app);
                server.start();
                configureStop(stop, server);
            } catch (Exception e) {
                throw new RuntimeException("Could not start server successfully", e);
            }
        } catch (ParseException exp) {
            // oops, something went wrong
            System.err.println("Parsing failed.  Reason: " + exp.getMessage());
        }
    }

    private static void configureStop(Consumer<Supplier<Void>> stop, Server server) {
        if(stop != null) {
            stop.accept(() -> {
                new Thread(() -> {
                    try {
                        server.stop();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                return null;
            });
        }
    }

    private static ConnectionFactory configureSsl(HttpConnectionFactory https, CommandLine cmd) {
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath(cmd.getOptionValue("keystorePath", "eztag/.env/server-keystore"));
        sslContextFactory.setKeyStorePassword(cmd.getOptionValue("keystorePassword", "changeme"));
        return new SslConnectionFactory(sslContextFactory, https.getProtocol());
    }

    private void configureConnector(Server server, CommandLine cmd) {
        // The plain HTTP configuration.
        HttpConfiguration plainConfig = new HttpConfiguration();
        // The secure HTTP configuration.
        HttpConfiguration secureConfig = new HttpConfiguration(plainConfig);

        // The number of acceptor threads.
        int acceptors = Optional.ofNullable(cmd.getOptionValue("acceptors")).map(Integer::parseInt).orElse(1);
        // The number of selectors.
        int selectors = Optional.ofNullable(cmd.getOptionValue("selectors")).map(Integer::parseInt).orElse(1);
        // Create a ServerConnector instance.
        // First, create the secure connector for HTTPS and HTTP/2.
        HttpConnectionFactory https = new HttpConnectionFactory(secureConfig);
        HTTP2ServerConnectionFactory http2 = new HTTP2ServerConnectionFactory(secureConfig);
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol(https.getProtocol());
        ConnectionFactory ssl = configureSsl(https, cmd);
        ServerConnector secureConnector = new ServerConnector(server, acceptors, selectors, ssl, alpn, http2, https);
        secureConnector.setPort(Optional.ofNullable(cmd.getOptionValue("securePort")).map(Integer::parseInt).orElse(8443));

        // Second, create the plain connector for HTTP.
        HttpConnectionFactory http = new HttpConnectionFactory(plainConfig);
        ServerConnector plainConnector = new ServerConnector(server, acceptors, selectors, http);
        server.addConnector(plainConnector);

        // Configure TCP/IP parameters.
        // The port to listen to.
        plainConnector.setPort(Optional.ofNullable(cmd.getOptionValue("port")).map(Integer::parseInt).orElse(8088));
        // The address to bind to.
        plainConnector.setHost(cmd.getOptionValue("host", "127.0.0.1"));

        // The TCP accept queue size.
        plainConnector.setAcceptQueueSize(128);
        // Add the Connector to the Server
        server.addConnector(plainConnector);
    }

    private void resourceHandler(HandlerList list, CommandLine cmd) throws IOException {
        // Create and configure a ResourceHandler.
        ResourceHandler handler = new ResourceHandler();
        // Configure the directory where static resources are located.
        handler.setBaseResource(Resource.newResource(cmd.getOptionValue("resourcesDir", "www/todos")));
        // Configure directory listing.
        handler.setDirAllowed(false);
        // Configure welcome files.
        handler.setWelcomeFiles(Optional.ofNullable(cmd.getOptionValue("welcomeFiles")).map(w -> w.split(",")).orElse(new String[]{"index.html"}));
        // Configure whether to accept range requests.
        handler.setAcceptRanges(true);
        list.addHandler(handler);
    }

    public Server configureServer(CommandLine cmd) throws Exception {
        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName(cmd.getOptionValue("serverName", "eztag-server"));

        // Create a Server instance.
        Server server = new Server(threadPool);

        // Create a ServerConnector to accept connections from clients.
        configureConnector(server, cmd);

        // Create a ContextHandlerCollection to hold contexts.
        HandlerList handlerList = new HandlerList();
        resourceHandler(handlerList, cmd);
        handlerList.addHandler(contextCollection);
        handlerList.addHandler(new DefaultHandler());

        // server.setDefaultHandler(new DefaultHandler(false, true));
        server.setHandler(handlerList);
        return server;
    }

    public Router route(String context) {
        Router router = new AppRouter(App.this);
        ContextHandler contextHandler = new ContextHandler(context);
        contextCollection.addHandler(contextHandler);
        contextHandler.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                try {
                    IReqHandler handler = router.fetch(baseRequest.getMethod(), target);
                    String result = handler.handle(request, response);
                    response.getWriter().write(result);
                    baseRequest.setHandled(true);
                } catch (Exception e) {
                    response.sendError(500, e.getLocalizedMessage());
                }
            }
        });
        return router;
    }
}
