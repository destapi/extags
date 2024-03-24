package works.hop.web.handler.team;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Player;
import works.hop.web.service.ITeamService;
import works.hop.web.service.IResult;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeamMembers extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String teamId = request.getParameter("teamId");
            IResult<List<Player>> updatedTeam = teamService.teamMembers(Long.parseLong(teamId));
            return gson.toJson(updatedTeam);
        } catch (Throwable e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
