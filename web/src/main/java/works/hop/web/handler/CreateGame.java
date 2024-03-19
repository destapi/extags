package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Game;
import works.hop.game.repository.GameRepo;
import works.hop.web.service.IGameService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("createGame")
@RequiredArgsConstructor
public class CreateGame extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Game>() {
            }.getType();
            Game gameInfo = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            return gson.toJson(gameService.createGame(gameInfo));
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
