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
import works.hop.game.model.GameStep;
import works.hop.game.model.StepStatus;
import works.hop.game.repository.GameStepRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IGameStepService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class GameStepServiceTest {

    IGameStepService stepService;
    @Mock
    GameStepRepo stepRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stepService = new GameStepService(stepRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        GameStep step = new GameStep();
        Map<String, String> violations = stepService.validate(step);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        GameStep step = new GameStep();
        step.setQuestionRef(1L);
        step.setGameRef(2L);
        step.setGroupNum(1);
        step.setQuestionNum(1);
        step.setStepStatus(StepStatus.AWAITING_NEXT);
        Map<String, String> violations = stepService.validate(step);
        assertThat(violations).isEmpty();
    }
}