package works.hop.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import works.hop.eztag.server.handler.ReqHandler;

import static works.hop.eztag.server.App.runApp;

@Configuration
@ComponentScan(basePackages = {"works.hop.web", "works.hop.eztag"})
public class Application {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

    public static void main(String[] args) {
        runApp(args, app -> {
            app.route("/game")
                    .get("/{id}", ctx.getBean("gameById", ReqHandler.class))
                    .post("/", ctx.getBean("createGame", ReqHandler.class))
                    .put("/{id}", ctx.getBean("updateGame", ReqHandler.class))
                    .delete("/{id}", ctx.getBean("deleteGame", ReqHandler.class))
                    .patch("/{id}", ctx.getBean("startGame", ReqHandler.class))
                    .route("/player")
                    .get("/email/{emailAddress}", ctx.getBean("playerByEmail", ReqHandler.class))
                    .get("/id/{id}", ctx.getBean("playerById", ReqHandler.class))
                    .post("/", ctx.getBean("createPlayer", ReqHandler.class))
                    .put("/{id}", ctx.getBean("updatePlayer", ReqHandler.class))
                    .delete("/{id}", ctx.getBean("deletePlayer", ReqHandler.class));
        });
    }
}