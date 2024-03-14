package works.hop.eztag.server.router;

import works.hop.eztag.server.handler.ReqHandler;

public interface ReqRouter {

    void store(String method, String path, ReqHandler handler);

    ReqHandler fetch(String method, String path);
}
