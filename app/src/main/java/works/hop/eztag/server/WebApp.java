package works.hop.eztag.server;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.*;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import works.hop.eztag.server.handler.AbstractReqHandler;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.eztag.server.handler.TodosHtmxHandler;
import works.hop.eztag.server.handler.TodosJsonHandler;
import works.hop.eztag.server.router.MethodReqRouter;
import works.hop.eztag.server.router.ReqRouter;

import java.io.IOException;
import java.util.function.Consumer;

public class WebApp {

    static ReqRouter router = new MethodReqRouter();
    static ContextHandlerCollection contextCollection = new ContextHandlerCollection();

    public static void runApp(String[] args, Consumer<WebApp> consumer) {
        try {
            WebApp app = new WebApp();
            Server server = app.configureServer();
            consumer.accept(app);
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Could not start server successfully", e);
        }
    }

    private static ConnectionFactory configureSsl(HttpConnectionFactory https) {
        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath("eztag/.env/server-keystore");
        sslContextFactory.setKeyStorePassword("changeme");
        return new SslConnectionFactory(sslContextFactory, https.getProtocol());
    }

    public static void main(String[] args) throws Exception {
        runApp(args, (app) -> {
        });
    }

    private void handler(String path, AbstractHandler handler) {
        ContextHandler todosJson = new ContextHandler(path);
        todosJson.setHandler(handler);
        contextCollection.addHandler(todosJson);
    }

    private void configureConnector(Server server) {
        // The plain HTTP configuration.
        HttpConfiguration plainConfig = new HttpConfiguration();
        // The secure HTTP configuration.
        HttpConfiguration secureConfig = new HttpConfiguration(plainConfig);

        // The number of acceptor threads.
        int acceptors = 1;
        // The number of selectors.
        int selectors = 1;
        // Create a ServerConnector instance.
        // First, create the secure connector for HTTPS and HTTP/2.
        HttpConnectionFactory https = new HttpConnectionFactory(secureConfig);
        HTTP2ServerConnectionFactory http2 = new HTTP2ServerConnectionFactory(secureConfig);
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol(https.getProtocol());
        ConnectionFactory ssl = configureSsl(https);
        ServerConnector secureConnector = new ServerConnector(server, acceptors, selectors, ssl, alpn, http2, https);
        secureConnector.setPort(8443);

        // Second, create the plain connector for HTTP.
        HttpConnectionFactory http = new HttpConnectionFactory(plainConfig);
        ServerConnector plainConnector = new ServerConnector(server, acceptors, selectors, http);
        server.addConnector(plainConnector);

        // Configure TCP/IP parameters.
        // The port to listen to.
        plainConnector.setPort(8080);
        // The address to bind to.
        plainConnector.setHost("127.0.0.1");

        // The TCP accept queue size.
        plainConnector.setAcceptQueueSize(128);
        // Add the Connector to the Server
        server.addConnector(plainConnector);
    }

    private void resourceHandler(HandlerList list) throws IOException {
        // Create and configure a ResourceHandler.
        ResourceHandler handler = new ResourceHandler();
        // Configure the directory where static resources are located.
        handler.setBaseResource(Resource.newResource("www/todos"));
        // Configure directory listing.
        handler.setDirAllowed(false);
        // Configure welcome files.
        handler.setWelcomeFiles(new String[]{"index.html"});
        // Configure whether to accept range requests.
        handler.setAcceptRanges(true);
        list.addHandler(handler);
    }

    public Server configureServer() throws Exception {
        // Create and configure a ThreadPool.
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("eztag-server");

        // Create a Server instance.
        Server server = new Server(threadPool);

        // Create a ServerConnector to accept connections from clients.
        configureConnector(server);

        // Create a ContextHandlerCollection to hold contexts.
        HandlerList handlerList = new HandlerList();
        resourceHandler(handlerList);
        handler("/todos", new TodosJsonHandler());
        handler("/htmx", new TodosHtmxHandler());
        handlerList.addHandler(contextCollection);
        handlerList.addHandler(new DefaultHandler());

        // server.setDefaultHandler(new DefaultHandler(false, true));
        server.setHandler(handlerList);
        return server;
    }

    public void handle(String method, String path, AbstractReqHandler handler) {
        handler.path(path); //MUST be set for routing to function
        router.store(method, path, handler);
    }

    public WebApp get(String path, AbstractReqHandler handler) {
        this.handle("get", path, handler);
        return this;
    }

    public WebApp delete(String path, AbstractReqHandler handler) {
        this.handle("delete", path, handler);
        return this;
    }

    public WebApp post(String path, AbstractReqHandler handler) {
        this.handle("post", path, handler);
        return this;
    }

    public WebApp put(String path, AbstractReqHandler handler) {
        this.handle("put", path, handler);
        return this;
    }

    public WebApp patch(String path, AbstractReqHandler handler) {
        this.handle("patch", path, handler);
        return this;
    }

    public WebApp route(String context) {
        ContextHandler contextHandler = new ContextHandler(context);
        contextCollection.addHandler(contextHandler);
        contextHandler.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                try {
                    ReqHandler handler = router.fetch(baseRequest.getMethod(), target);
                    String result = handler.handle(request, response);
                    response.getWriter().write(result);
                    baseRequest.setHandled(true);
                } catch (Exception e) {
                    response.sendError(500, e.getLocalizedMessage());
                }
            }
        });
        return this;
    }
}
