package works.hop.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import works.hop.web.handler.*;

import static works.hop.eztag.server.App.runApp;

@Configuration
@ComponentScan(basePackages = {"works.hop.web", "works.hop.game"})
public class Application {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

    public static void main(String[] args) {
        runApp(args, app -> {
            app.route("/game")
                    .get("/{id}", ctx.getBean(GameById.class))
                    .post("/", ctx.getBean(CreateGame.class))
                    .put("/{id}", ctx.getBean(UpdateGame.class))
                    .delete("/{id}", ctx.getBean(ResetGame.class))
                    .patch("/{id}", ctx.getBean(StartGame.class))
                    .route("/player")
                    .get("/email/{email}", ctx.getBean(PlayerByEmail.class))
                    .get("/id/{id}", ctx.getBean(PlayerById.class))
                    .post("/", ctx.getBean(CreatePlayer.class))
                    .put("/{id}", ctx.getBean(UpdatePlayer.class))
                    .delete("/{id}", ctx.getBean(DeletePlayer.class));
        });
    }
}