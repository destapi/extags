package works.hop.web.handler.game;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Game;
import works.hop.game.model.Player;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;
import works.hop.web.service.ITeamService;

import java.util.List;
import java.util.Objects;

@Component("GamesByOrganizer")
@RequiredArgsConstructor
public class GamesByOrganizer extends ReqHandler {

    final Gson gson;
    final IGameService gameService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String organizerId = request.getParameter("organizerId");
            IResult<List<Game>> updatedTeam = gameService.getByOrganizer(Objects.requireNonNull(Long.parseLong(organizerId), "Organizer id is not optional"));
            return gson.toJson(updatedTeam);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
