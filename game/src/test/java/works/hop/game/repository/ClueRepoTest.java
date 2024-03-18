package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Clue;
import works.hop.game.model.Participant;
import works.hop.game.model.Question;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class ClueRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    ClueRepo clueRepo;
    ParticipantRepo participantRepo;
    QuestionRepo questionRepo;

    @BeforeEach
    void setUp(){
        clueRepo = new ClueRepo(embeddedDataSource);
        participantRepo = new ParticipantRepo(embeddedDataSource);
        questionRepo = new QuestionRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateClue() {
        Participant crazyeyes = participantRepo.getByEmail("jimmy.crazyeyes@email.com");
        Clue newClue = new Clue();
        newClue.setOrdinal(2);
        newClue.setQuestionRef(2L);
        newClue.setExplanation("it's two");
        newClue.setClueValue("2");
        newClue = clueRepo.createClue(newClue);

        Question que = questionRepo.getByValue(crazyeyes.getId(), "1 + 1");

        newClue.setQuestionRef(que.getId());
        newClue.setClueValue("7");
        newClue = clueRepo.updateClue(newClue);
        List<Clue> updated = clueRepo.getByRef(newClue.getQuestionRef());
        assertThat(updated).hasSize(4);
        assertThat(updated.stream().filter(v -> v.getClueValue().equals("7")).findFirst()).isPresent();
    }
}