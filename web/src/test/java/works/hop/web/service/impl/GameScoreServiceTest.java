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
import works.hop.game.model.GameScore;
import works.hop.game.repository.GameScoreRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IGameScoreService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class GameScoreServiceTest {

    IGameScoreService scoreService;
    @Mock
    GameScoreRepo scoreRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        scoreService = new GameScoreService(scoreRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        GameScore score = new GameScore();
        Map<String, String> violations = scoreService.validate(score);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        GameScore score = new GameScore();
        score.setQuestionRef(1L);
        score.setGameRef(2L);
        score.setPlayerRef(1L);
        score.setResponse("my fav color");
        Map<String, String> violations = scoreService.validate(score);
        assertThat(violations).isEmpty();
    }
}