package works.hop.eztag.server.router;

import works.hop.eztag.server.handler.ReqHandler;

public interface Router extends RouteSearch {

    Router get(String path, ReqHandler handler);

    Router delete(String path, ReqHandler handler);

    Router post(String path, ReqHandler handler);

    Router put(String path, ReqHandler handler);

    Router patch(String path, ReqHandler handler);

    void handle(String method, String path, ReqHandler handler);

    Router route(String context);
}
