package works.hop.web.handler.team;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Player;
import works.hop.game.model.Team;
import works.hop.web.service.IResult;
import works.hop.web.service.ITeamService;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.Objects;

@Component("RegisterTeam")
@RequiredArgsConstructor
public class JoinTeam extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            long teamId = Long.parseLong(Objects.requireNonNull(param("teamId"), "Team id is not an optional field when joining a team"));
            long playerId = Long.parseLong(Objects.requireNonNull(param("playerId"), "Player id is not an optional field when joining a team"));
            IResult<Void> teamJoined = teamService.joinTeam(teamId, playerId);
            return gson.toJson(teamJoined);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
