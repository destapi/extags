package works.hop.web.handler.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import works.hop.eztag.server.App;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class QuestionRoutes implements Consumer<App> {

    final String path = "/question";
    final CreateQuestion createQuestion;

    @Override
    public void accept(App app) {
        app.route(this.path)
                .post("/", createQuestion);
    }
}
