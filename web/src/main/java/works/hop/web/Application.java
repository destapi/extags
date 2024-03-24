package works.hop.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import works.hop.web.handler.*;
import works.hop.web.handler.choice.ChoiceRoutes;
import works.hop.web.handler.clue.ClueRoutes;
import works.hop.web.handler.game.GameRoutes;
import works.hop.web.handler.player.PlayerRoutes;
import works.hop.web.handler.question.QuestionRoutes;
import works.hop.web.handler.team.TeamRoutes;

import static works.hop.eztag.server.App.runApp;

@Configuration
@ComponentScan(basePackages = {"works.hop.web", "works.hop.game"})
public class Application {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

    public static void main(String[] args) {
        runApp(args, app -> {
            app.route("/health")
                    .get("/", ctx.getBean(HealthCheck.class));
            ctx.getBean(ChoiceRoutes.class).accept(app);
            ctx.getBean(ClueRoutes.class).accept(app);
            ctx.getBean(GameRoutes.class).accept(app);
            ctx.getBean(PlayerRoutes.class).accept(app);
            ctx.getBean(QuestionRoutes.class).accept(app);
            ctx.getBean(TeamRoutes.class).accept(app);
        });
    }
}