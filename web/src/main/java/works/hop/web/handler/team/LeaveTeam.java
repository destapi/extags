package works.hop.web.handler.team;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.IResult;
import works.hop.web.service.ITeamService;

import java.util.Objects;

@Component("RegisterTeam")
@RequiredArgsConstructor
public class LeaveTeam extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            long teamId = Long.parseLong(Objects.requireNonNull(param("teamId"), "Team id is not an optional field when leaving a team"));
            long playerId = Long.parseLong(Objects.requireNonNull(param("playerId"), "Player id is not an optional field when leaving a team"));
            IResult<Void> leftTeam = teamService.leaveTeam(teamId, playerId);
            return gson.toJson(leftTeam);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
