package works.hop.web.handler.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TeamRoutes implements Consumer<App> {

    final String path = "/team";
    final TeamById teamById;
    final CreateTeam createTeam;
    final TeamMembers teamMembers;
    final UpdateTeam updateTeam;
    final JoinTeam joinTeam;
    final LeaveTeam leaveTeam;
    final DeleteTeam deleteTeam;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .get("/id/{teamId}", teamById)
                .get("/members/{teamId}", teamMembers)
                .post("/", createTeam)
                .put("/", updateTeam)
                .patch("/{teamId}/join/{playerId}", joinTeam)
                .patch("/{teamId}/leave/{playerId}", leaveTeam)
                .delete("/{ordinal}/question/{questionId}", deleteTeam);
    }
}
