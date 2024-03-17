package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Game;
import works.hop.game.repository.GameRepo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Service("createGame")
@RequiredArgsConstructor
public class UpdateGame extends ReqHandler {

    final Gson gson;
    final GameRepo gameRepo;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Game>() {
            }.getType();
            Game gameInfo = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            return gson.toJson(gameRepo.updateGame(gameInfo));
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
