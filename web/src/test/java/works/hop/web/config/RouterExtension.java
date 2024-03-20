package works.hop.web.config;

import org.junit.jupiter.api.extension.*;
import works.hop.eztag.server.App;
import works.hop.eztag.server.router.Router;

import java.util.function.Supplier;

public class RouterExtension implements AfterAllCallback, ParameterResolver {

    Supplier<Void> stopServer;
    App app;

    public RouterExtension() {
        new Thread(() -> App.runApp(
                new String[]{"--p", "3030", "--h", "localhost"},
                (app) -> {
                    this.app = app;
                },
                (supplier) -> {
                    this.stopServer = supplier;
                })).start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        stopServer.get();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(WebTestRouter.class) && Router.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Router resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return app.route("/");
    }
}
