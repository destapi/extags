package works.hop.web.handler.team;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Team;
import works.hop.web.service.ITeamService;
import works.hop.web.service.IResult;

import java.util.List;

@Component("QuestionTeams")
@RequiredArgsConstructor
public class TeamById extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;
    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            String teamId = request.getParameter("teamId");
            IResult<Team> currentTeams = teamService.getById(Long.parseLong(teamId));
            return gson.toJson(currentTeams);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
