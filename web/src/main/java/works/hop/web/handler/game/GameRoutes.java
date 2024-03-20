package works.hop.web.handler.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class GameRoutes implements Consumer<App>  {

    final String path = "/game";
    final GameById gameById;
    final GamesByOrganizer gamesByOrganizer;
    final CreateGame createGame;
    final UpdateGame updateGame;
    final ResetGame resetGame;
    final StartGame startGame;
    final EndGame endGame;
    final JoinGame joinGame;
    final LeaveGame leaveGame;
    final GameParticipants gameParticipants;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .get("/id/{gameId}", gameById)
                .get("/organizer/{organizerId}", gameById)
                .get("/participants/{gameId}", gameParticipants)
                .post("/", createGame)
                .put("/", updateGame)
                .delete("/{gameId}", resetGame)
                .patch("/{gameId}/start", startGame)
                .patch("/{gameId}/end", endGame)
                .patch("/{gameId}/join", joinGame)
                .patch("/{gameId}/leave", leaveGame);
    }
}
