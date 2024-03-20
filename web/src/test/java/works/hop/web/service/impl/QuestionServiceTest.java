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
import works.hop.game.model.Question;
import works.hop.game.model.QuestionType;
import works.hop.game.repository.QuestionRepo;
import works.hop.web.config.TestWebConfig;
import works.hop.web.service.IQuestionService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestWebConfig.class)
class QuestionServiceTest {

    IQuestionService questionService;
    @Mock
    QuestionRepo questionRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService(questionRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        Question question = new Question();
        Map<String, String> violations = questionService.validate(question);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        Question question = new Question();
        question.setQuestion("what is the time");
        question.setMaxPoints(1000);
        question.setAnswer("you got this");
        question.setQuestionType(QuestionType.HISTORY);
        Map<String, String> violations = questionService.validate(question);
        assertThat(violations).isEmpty();
    }
}