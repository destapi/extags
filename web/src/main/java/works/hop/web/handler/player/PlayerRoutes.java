package works.hop.web.handler.player;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class PlayerRoutes implements Consumer<App> {

    final String path = "/player";
    final PlayerByEmail playerByEmail;
    final PlayerById playerById;
    final CreatePlayer createPlayer;
    final UpdatePlayer updatePlayer;
    final DeletePlayer deletePlayer;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .get("/email/{email}", playerByEmail)
                .get("/id/{id}", playerById)
                .post("/", createPlayer)
                .put("/{id}", updatePlayer)
                .delete("/{id}", deletePlayer);
    }
}
