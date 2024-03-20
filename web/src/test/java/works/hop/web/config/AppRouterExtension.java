package works.hop.web.config;

import org.junit.jupiter.api.extension.*;
import works.hop.eztag.server.App;
import works.hop.eztag.server.router.AppRouter;
import works.hop.eztag.server.router.Router;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Supplier;

public class AppRouterExtension implements AfterAllCallback, ParameterResolver {

    ExecutorService executor;
    Supplier<Void> stopServer;
    App app;

    public AppRouterExtension(){
        executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> App.runApp(new String[]{}, (app) -> { this.app = app;}, (supplier) -> {
            this.stopServer = supplier;
        }));
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                this.executor.shutdownNow()));
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        stopServer.get();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(TestRouter.class) && Router.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Router resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return app.route("/");
    }
}
