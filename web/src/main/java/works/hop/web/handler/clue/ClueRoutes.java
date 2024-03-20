package works.hop.web.handler.clue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ClueRoutes implements Consumer<App> {

    final String path = "/clue";
    final QuestionClues questionClues;
    final CreateClue createClue;
    final UpdateClue updateClue;
    final DeleteClue deleteClue;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .get("/{questionId}", questionClues)
                .post("/", createClue)
                .put("/", updateClue)
                .delete("/{ordinal}/question/{questionId}", deleteClue);
    }
}
