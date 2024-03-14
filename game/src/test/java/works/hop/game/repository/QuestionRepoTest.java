package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Participant;
import works.hop.game.model.Question;
import works.hop.game.model.QuestionType;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class QuestionRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    QuestionRepo gameRepo;
    ParticipantRepo participantRepo;

    @BeforeEach
    void setUp() {
        gameRepo = new QuestionRepo(embeddedDataSource);
        participantRepo = new ParticipantRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateQuestion() {
        Participant crazyeyes = participantRepo.getByEmail("jimmy.crazyeyes@email.com");
        Question newQuestion = new Question();
        newQuestion.setQuestion("1+1");
        newQuestion.setCreatedByRef(crazyeyes.getId());
        newQuestion.setAnswer("2");
        newQuestion.setAnswerReason("com'on man");
        newQuestion = gameRepo.createQuestion(newQuestion);
        assertThat(newQuestion.getId()).isNotZero();
        assertThat(newQuestion.getQuestionType()).isNull();
        assertThat(newQuestion.getMaxPoints()).isZero();

        newQuestion.setQuestionType(QuestionType.ENGLISH);
        newQuestion.setMaxPoints(100);
        newQuestion = gameRepo.updateQuestion(newQuestion);
        Question updated = gameRepo.getById(newQuestion.getId());
        assertThat(updated.getQuestionType()).isEqualTo(newQuestion.getQuestionType());
        assertThat(updated.getMaxPoints()).isEqualTo(newQuestion.getMaxPoints());
    }
}