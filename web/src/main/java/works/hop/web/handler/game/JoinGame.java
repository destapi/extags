package works.hop.web.handler.game;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;

@Component("JoinGame")
@RequiredArgsConstructor
public class JoinGame extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String gameId = request.getParameter("gameId");
            String playerId = request.getParameter("playerId");
            IResult<Void> updatedGame = gameService.joinGame(Long.parseLong(gameId), Long.parseLong(playerId));
            return gson.toJson(updatedGame);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
