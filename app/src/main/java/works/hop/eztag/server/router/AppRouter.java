package works.hop.eztag.server.router;

import works.hop.eztag.server.App;
import works.hop.eztag.server.handler.IReqHandler;
import works.hop.eztag.server.handler.ReqHandler;

public class AppRouter implements Router {

    final RouteSearch router = new MethodRouteSearch();
    final App app;

    public AppRouter(App app) {
        this.app = app;
    }

    public Router get(String path, ReqHandler handler) {
        this.handle("get", path, handler);
        return this;
    }

    public Router delete(String path, ReqHandler handler) {
        this.handle("delete", path, handler);
        return this;
    }

    public Router post(String path, ReqHandler handler) {
        this.handle("post", path, handler);
        return this;
    }

    public Router put(String path, ReqHandler handler) {
        this.handle("put", path, handler);
        return this;
    }

    public Router patch(String path, ReqHandler handler) {
        this.handle("patch", path, handler);
        return this;
    }

    public void handle(String method, String path, ReqHandler handler) {
        handler.path(path); //MUST be set for routing to function
        store(method, path, handler);
    }

    @Override
    public Router route(String context) {
        return app.route(context);
    }

    @Override
    public void store(String method, String path, IReqHandler handler) {
        router.store(method, path, handler);
    }

    @Override
    public IReqHandler fetch(String method, String path) {
        return router.fetch(method, path);
    }
}
