package works.hop.web.handler.game;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;

@Component("StartGame")
@RequiredArgsConstructor
public class StartGame extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String gameId = request.getParameter("id");
            IResult<Void> updatedGame = gameService.startGame(Long.parseLong(gameId));
            return gson.toJson(updatedGame);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
