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
import works.hop.web.config.AppConfig;
import works.hop.web.config.AppRouterExtension;
import works.hop.web.config.TestRouter;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IPlayerService;

import java.util.Map;

@ExtendWith({SpringExtension.class, AppRouterExtension.class})
@ContextConfiguration(classes = TestWebConfig.class)
class CreatePlayerTest {

    CreatePlayer handler;
    @Mock
    IPlayerService service;
    @Autowired
    Gson gson;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        handler = new CreatePlayer(gson, service);
    }

    @Test
    void handle(@TestRouter Router router) throws Exception {
        router.post("/players", handler);
        AppClient.request("post",
                "/players",
                Map.of("Content-Type", "application/json"),
                new StringRequestContent("", ""),
                response -> {

                });
    }
}