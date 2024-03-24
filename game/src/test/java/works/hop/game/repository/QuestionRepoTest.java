package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.RepoTestConfig;
import works.hop.game.model.Player;
import works.hop.game.model.Question;
import works.hop.game.model.QuestionType;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepoTestConfig.class)
class QuestionRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    QuestionRepo questionRepo;
    PlayerRepo playerRepo;

    @BeforeEach
    void setUp() {
        questionRepo = new QuestionRepo(embeddedDataSource);
        playerRepo = new PlayerRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateQuestion() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Question newQuestion = new Question();
        newQuestion.setQuestion("1+1");
        newQuestion.setCreatedByRef(crazyeyes.getId());
        newQuestion.setAnswer("2");
        newQuestion.setAnswerReason("com'on man");
        newQuestion = questionRepo.createQuestion(newQuestion);
        assertThat(newQuestion.getId()).isNotZero();
        assertThat(newQuestion.getQuestionType()).isNull();

        newQuestion.setQuestionType(QuestionType.ENGLISH);
        newQuestion.setAnswer("It's always the same");
        newQuestion = questionRepo.updateQuestion(newQuestion);
        Question updated = questionRepo.getById(newQuestion.getId());
        assertThat(updated.getQuestionType()).isEqualTo(newQuestion.getQuestionType());
        assertThat(updated.getAnswer()).isEqualTo(newQuestion.getAnswer());
    }

    @Test
    void createAndRemoveQuestion() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Question newQuestion = new Question();
        newQuestion.setQuestion("once upon a time");
        newQuestion.setCreatedByRef(crazyeyes.getId());
        newQuestion.setAnswer("2");
        newQuestion.setAnswerReason("there was a wolf");
        newQuestion = questionRepo.createQuestion(newQuestion);
        assertThat(newQuestion.getId()).isNotZero();
        assertThat(newQuestion.getQuestionType()).isNull();

        List<Question> ques1 = questionRepo.getByAuthor(crazyeyes.getId());
        assertThat(ques1).isNotEmpty();

        questionRepo.removeQuestion(newQuestion.getId());
        List<Question> ques2 = questionRepo.getByAuthor(crazyeyes.getId());
        assertThat(ques2.size()).isEqualTo(ques1.size() - 1);
    }
}