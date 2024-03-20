package works.hop.web.handler;

import com.google.gson.Gson;
import org.eclipse.jetty.client.util.StringRequestContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.eztag.client.AppClient;
import works.hop.eztag.server.router.Router;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;
import works.hop.web.config.AppConfig;
import works.hop.web.config.RouterExtension;
import works.hop.web.config.WebTestRouter;
import works.hop.web.service.IPlayerService;

import java.util.Collections;

@ExtendWith({SpringExtension.class, RouterExtension.class})
@ContextConfiguration(classes = AppConfig.class)
class CreatePlayerTest {

    CreatePlayer handler;
    @Mock
    IPlayerService service;
    @Autowired
    Gson gson;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new CreatePlayer(gson, service);
    }

    @Test
    void handle(@WebTestRouter Router router) throws Exception {
        // add route handler
        router.post("/player", handler);

        // create target entity
        Player newPlayer = new Player();
        newPlayer.setPlayerStatus(PlayerStatus.UNVERIFIED);
        newPlayer.setEmailAddress("jimmy.snakeeyes@email.com");
        newPlayer.setScreenName("jimbob");
        newPlayer.setCity("Jacksonville");
        newPlayer.setState("FL");

        // fire up request to testing server
        AppClient.request("post",
                "http://localhost:3030/player/",
                Collections.emptyMap(),
                new StringRequestContent("application/json", gson.toJson(newPlayer)),
                response -> {
                    System.out.println(response.getContentAsString());
                });
    }
}