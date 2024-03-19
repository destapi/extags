package works.hop.web.handler;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Player;
import works.hop.game.repository.PlayerRepo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("createPlayer")
@RequiredArgsConstructor
public class CreatePlayer extends ReqHandler {

    final Gson gson;
    final PlayerRepo playerRepo;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Player>() {
            }.getType();
            Player playerInfo = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            return gson.toJson(playerRepo.createPlayer(playerInfo));
        } catch (IOException e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
