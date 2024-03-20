package works.hop.web.service.impl;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.model.Player;
import works.hop.game.model.StepStatus;
import works.hop.game.repository.PlayerRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IPlayerService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class PlayerServiceTest {

    IPlayerService playerService;
    @Mock
    PlayerRepo playerRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        playerService = new PlayerService(playerRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        Player player = new Player();
        Map<String, String> violations = playerService.validate(player);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        Player player = new Player();
        player.setScreenName("jimmy");
        player.setEmailAddress("jimmy.black@email.com");
        player.setCity("Pittsburgh");
        player.setState("PA");
        Map<String, String> violations = playerService.validate(player);
        assertThat(violations).isEmpty();
    }
}