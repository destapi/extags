package works.hop.web.handler.game;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Game;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("UpdateGame")
@RequiredArgsConstructor
public class UpdateGame extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Game>() {
            }.getType();
            Game game = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Game> updatedGame = gameService.updateGame(game);
            return gson.toJson(updatedGame);
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
