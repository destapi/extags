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
import works.hop.game.model.Clue;
import works.hop.game.repository.ClueRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IClueService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class ClueServiceTest {

    IClueService clueService;
    @Mock
    ClueRepo clueRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clueService = new ClueService(clueRepo, validator);
    }

    @Test
    void validator_when_entity_is_missing_required_fields() {
        Clue clue = new Clue();
        Map<String, String> violations = clueService.validate(clue);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        Clue clue = new Clue();
        clue.setClueValue("something cool");
        clue.setOrdinal(1);
        clue.setQuestionRef(1L);
        Map<String, String> violations = clueService.validate(clue);
        assertThat(violations).isEmpty();
    }
}