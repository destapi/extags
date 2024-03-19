package works.hop.eztag.server.router;

import works.hop.eztag.server.handler.IReqHandler;

public interface RouteSearch {

    void store(String method, String path, IReqHandler handler);

    IReqHandler fetch(String method, String path);
}
