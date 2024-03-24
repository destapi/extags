package works.hop.web.handler.player;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Player;
import works.hop.web.service.IPlayerService;
import works.hop.web.service.IResult;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component
@RequiredArgsConstructor
public class CreatePlayer extends ReqHandler {

    final Gson gson;
    final IPlayerService playerService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Player>() {
            }.getType();
            Player player = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Player> newPlayer = playerService.createNewPlayer(player);
            return gson.toJson(newPlayer);
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
