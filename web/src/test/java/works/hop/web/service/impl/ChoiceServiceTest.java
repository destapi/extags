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
import works.hop.game.model.Choice;
import works.hop.game.repository.ChoiceRepo;
import works.hop.web.config.TestWebConfig;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class ChoiceServiceTest {

    ChoiceService choiceService;
    @Mock
    ChoiceRepo choiceRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        choiceService = new ChoiceService(choiceRepo, validator);
    }

    @Test
    void validator_when_choice_entity_is_missing_required_fields() {
        Choice choice = new Choice();
        Map<String, String> violations = choiceService.validate(choice);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void validator_when_choice_entity_is_NOT_missing_required_fields() {
        Choice choice = new Choice();
        choice.setChoiceValue("something cool");
        choice.setOrdinal(1);
        choice.setQuestionRef(1L);
        Map<String, String> violations = choiceService.validate(choice);
        assertThat(violations).isEmpty();
    }

    @Test
    void questionChoices() {
    }

    @Test
    void addChoice() {
    }

    @Test
    void updateChoice() {
    }

    @Test
    void removeChoice() {
    }
}