package works.hop.eztag.server.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import works.hop.eztag.server.router.PathParams;

public interface IReqHandler {

    PathParams params();

    void params(PathParams params);

    String path();

    void path(String path);

    String param(String name);

    String handle(HttpServletRequest request, HttpServletResponse response);
}
