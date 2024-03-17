package works.hop.eztag.server.handler;

import works.hop.eztag.server.router.PathParams;

public abstract class ReqHandler implements IReqHandler {

    private String path;
    private PathParams params = new PathParams();

    @Override
    public PathParams params() {
        return this.params;
    }

    @Override
    public void params(PathParams params) {
        this.params = params;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public void path(String path) {
        this.path = path;
    }

    @Override
    public String param(String name) {
        return params().get(name);
    }
}
