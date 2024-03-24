package works.hop.web.handler.team;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.handler.ReqHandler;
import works.hop.web.service.ITeamService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeleteTeam extends ReqHandler {

    final Gson gson;
    final ITeamService teamService;

    @Override
    public String handle(HttpServletRequest request, HttpServletResponse response) {
        try {
            long teamId = Long.parseLong(Objects.requireNonNull(param("teamId"), "Team id is not optional when removing a team"));
            return gson.toJson(teamService.deleteTeam(teamId));
        } catch (Exception e) {
            response.setStatus(500);
            return e.getMessage();
        }
    }
}
