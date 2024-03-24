package works.hop.web.handler.player;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Player;
import works.hop.web.service.IPlayerService;
import works.hop.web.service.IResult;

@Component
@RequiredArgsConstructor
public class PlayerById extends ReqHandler {

    final Gson gson;
    final IPlayerService playerService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String playerId = param("id");
            IResult<Player> byEmail = playerService.getById(Long.parseLong(playerId));
            return gson.toJson(byEmail);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
