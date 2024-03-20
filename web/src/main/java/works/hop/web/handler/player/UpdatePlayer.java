package works.hop.web.handler;

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

@Component("UpdatePlayer")
@RequiredArgsConstructor
public class UpdatePlayer extends ReqHandler {

    final Gson gson;
    final IPlayerService playerService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Player>() {
            }.getType();
            Player player = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Player> updatedPlayer = playerService.updatePlayer(player);
            return gson.toJson(updatedPlayer);
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
