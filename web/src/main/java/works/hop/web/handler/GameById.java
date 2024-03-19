package works.hop.web.handler;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Game;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;

@Component("gameById")
@RequiredArgsConstructor
public class GameById extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String playerId = param("id");
            IResult<Game> byEmail = gameService.getById(Long.parseLong(playerId));
            return gson.toJson(byEmail);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
