package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Participant;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class ParticipantRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    ParticipantRepo participantRepo;

    @BeforeEach
    void setUp() {
        participantRepo = new ParticipantRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateUser() {
        Participant newPlayer = new Participant();
        newPlayer.setScreenName("weassle");
        newPlayer.setFirstName("cassie");
        newPlayer.setEmailAddress("cassie.weassle@email.com");
        newPlayer = participantRepo.createPlayer(newPlayer);
        assertThat(newPlayer.getId()).isNotZero();
        assertThat(newPlayer.getCity()).isNull();
        assertThat(newPlayer.getLastName()).isNull();

        newPlayer.setLastName("kadzo");
        newPlayer.setCity("madison");
        newPlayer = participantRepo.updatePlayer(newPlayer);
        Participant updated = participantRepo.getById(newPlayer.getId());
        assertThat(updated.getCity()).isEqualTo(newPlayer.getCity());
        assertThat(updated.getLastName()).isEqualTo(newPlayer.getLastName());
    }
}