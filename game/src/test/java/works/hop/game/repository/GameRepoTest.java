package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Game;
import works.hop.game.model.GameStatus;
import works.hop.game.model.Participant;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class GameRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    GameRepo gameRepo;
    ParticipantRepo participantRepo;

    @BeforeEach
    void setUp(){
        gameRepo = new GameRepo(embeddedDataSource);
        participantRepo = new ParticipantRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateGame() {
        Participant crazyeyes = participantRepo.getByEmail("jimmy.crazyeyes@email.com");
        Game newGame = new Game();
        newGame.setTitle("the ultimate challenge");
        newGame.setOrganizerRef(crazyeyes.getId());
        newGame = gameRepo.createGame(newGame);
        assertThat(newGame.getId()).isNotZero();
        assertThat(newGame.getDescription()).isNull();
        assertThat(newGame.getGameStatus()).isNull();

        newGame.setGameStatus(GameStatus.STARTED);
        newGame.setDescription("rock and rolling");
        newGame = gameRepo.updateGame(newGame);
        Game updated = gameRepo.getById(newGame.getId());
        assertThat(updated.getGameStatus()).isEqualTo(newGame.getGameStatus());
        assertThat(updated.getDescription()).isEqualTo(newGame.getDescription());
    }
}