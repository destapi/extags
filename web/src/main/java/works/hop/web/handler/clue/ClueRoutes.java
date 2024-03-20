package works.hop.web.handler.choice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class ChoiceRoutes implements Consumer<App> {

    final String path = "/choice";
    final QuestionChoices questionChoices;
    final CreateChoice createChoice;
    final UpdateChoice updateChoice;
    final DeleteChoice deleteChoice;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .get("/{questionId}", questionChoices)
                .post("/", createChoice)
                .put("/", updateChoice)
                .delete("/{ordinal}/question/{questionId}", deleteChoice);
    }
}
