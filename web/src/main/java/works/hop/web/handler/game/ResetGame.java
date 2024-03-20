package works.hop.web.handler;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IGameService;

@Component("ResetGame")
@RequiredArgsConstructor
public class ResetGame extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            long playerId = Long.parseLong(param("id"));
            return gson.toJson(gameService.resetGame(playerId));
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
