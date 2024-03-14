package works.hop.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import works.hop.eztag.server.handler.AbstractReqHandler;

import static works.hop.eztag.server.WebApp.runApp;

@Configuration
@ComponentScan(basePackages = {"works.hop.web", "works.hop.app"})
public class Application {

    private static final ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

    public static void main(String[] args) {
        runApp(args, app -> {
            app.route("/game")
                    .get("/{id}", ctx.getBean("gameById", AbstractReqHandler.class))
                    .post("/", ctx.getBean("createGame", AbstractReqHandler.class))
                    .put("/{id}", ctx.getBean("updateGame", AbstractReqHandler.class))
                    .delete("/{id}", ctx.getBean("deleteGame", AbstractReqHandler.class))
                    .patch("/{id}", ctx.getBean("startGame", AbstractReqHandler.class))
                    .route("/player")
                    .get("/email/{emailAddress}", ctx.getBean("playerByEmail", AbstractReqHandler.class))
                    .get("/id/{id}", ctx.getBean("playerById", AbstractReqHandler.class))
                    .post("/", ctx.getBean("createPlayer", AbstractReqHandler.class))
                    .put("/{id}", ctx.getBean("updatePlayer", AbstractReqHandler.class))
                    .delete("/{id}", ctx.getBean("deletePlayer", AbstractReqHandler.class));
        });
    }

    public static <T> T getBean(String beanName, T beanType) {
        return (T) ctx.getBean(beanName, beanType);
    }
}