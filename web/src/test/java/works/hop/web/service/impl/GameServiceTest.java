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
import works.hop.game.model.Game;
import works.hop.game.model.GameStatus;
import works.hop.game.repository.GameRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IGameService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class GameServiceTest {

    IGameService gameService;
    @Mock
    GameRepo gameRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameService = new GameService(gameRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        Game game = new Game();
        Map<String, String> violations = gameService.validate(game);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        Game game = new Game();
        game.setTitle("Never giving up");
        game.setOrganizerRef(1L);
        game.setGameStatus(GameStatus.READY);
        Map<String, String> violations = gameService.validate(game);
        assertThat(violations).isEmpty();
    }
}