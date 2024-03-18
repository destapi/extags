package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Player;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class PlayerRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    PlayerRepo playerRepo;

    @BeforeEach
    void setUp() {
        playerRepo = new PlayerRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdatePlayer() {
        Player newPlayer = new Player();
        newPlayer.setScreenName("weassle");
        newPlayer.setFirstName("cassie");
        newPlayer.setEmailAddress("cassie.weassle@email.com");
        newPlayer = playerRepo.createPlayer(newPlayer);
        assertThat(newPlayer.getId()).isNotZero();
        assertThat(newPlayer.getCity()).isNull();
        assertThat(newPlayer.getLastName()).isNull();

        newPlayer.setLastName("kadzo");
        newPlayer.setCity("madison");
        newPlayer = playerRepo.updatePlayer(newPlayer);
        Player updated = playerRepo.getById(newPlayer.getId());
        assertThat(updated.getCity()).isEqualTo(newPlayer.getCity());
        assertThat(updated.getLastName()).isEqualTo(newPlayer.getLastName());
    }

    @Test
    void createAndRemovePlayer() {
        Player newPlayer = new Player();
        newPlayer.setScreenName("mogli");
        newPlayer.setFirstName("janice");
        newPlayer.setEmailAddress("janice.mogli@email.com");
        final Player created = playerRepo.createPlayer(newPlayer);
        assertThat(created.getId()).isNotZero();

        //retrieve player
        Player found = playerRepo.getById(newPlayer.getId());
        assertThat(found.getId()).isEqualTo(newPlayer.getId());
        assertThat(found.getFirstName()).isEqualTo(newPlayer.getFirstName());
        
        // delete player
        playerRepo.removePlayer(newPlayer.getId());

        //now retrieve same player
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
        .isThrownBy(() -> playerRepo.getById(newPlayer.getId()));
    }
}