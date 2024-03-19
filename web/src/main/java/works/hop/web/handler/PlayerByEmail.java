package works.hop.web.handler;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IPlayerService;

@Component("playerByEmail")
@RequiredArgsConstructor
public class PlayerByEmail extends ReqHandler {

    final Gson gson;
    final IPlayerService playerService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String emailAddress = param("email");
            return gson.toJson(playerService.getByEmailAddress(emailAddress));
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
