package works.hop.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;

@Component
@RequiredArgsConstructor
public class HealthCheck extends ReqHandler {

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        return "Up";
    }
}
