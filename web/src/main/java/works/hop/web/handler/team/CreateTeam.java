package works.hop.web.handler.team;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.game.model.Team;
import works.hop.game.model.Player;
import works.hop.web.service.ITeamService;
import works.hop.web.service.IResult;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

@Component("CreateTeam")
@RequiredArgsConstructor
public class CreateTeam extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            Type mapType = new TypeToken<Player>() {
            }.getType();
            Team team = gson.fromJson(
                    new InputStreamReader(request.getInputStream()), mapType);
            IResult<Team> newTeam = teamService.createNewTeam(team);
            return gson.toJson(newTeam);
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
