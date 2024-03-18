package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Game;
import works.hop.game.model.Player;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static works.hop.game.model.GameStatus.COMPLETED;
import static works.hop.game.model.GameStatus.STARTED;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class GameRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    GameRepo gameRepo;
    PlayerRepo playerRepo;

    @BeforeEach
    void setUp() {
        gameRepo = new GameRepo(embeddedDataSource);
        playerRepo = new PlayerRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateGame() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Game newGame = new Game();
        newGame.setTitle("the ultimate challenge");
        newGame.setOrganizerRef(crazyeyes.getId());
        newGame = gameRepo.createGame(newGame);
        assertThat(newGame.getId()).isNotZero();
        assertThat(newGame.getDescription()).isNull();
        assertThat(newGame.getOrganizer()).isNull();

        newGame.setDescription("rock and rolling");
        newGame = gameRepo.updateGame(newGame);
        Game updated = gameRepo.getById(newGame.getId());
        assertThat(updated.getOrganizer()).isNotNull();
        assertThat(updated.getOrganizer().getEmailAddress()).isEqualTo("jimmy.crazyeyes@email.com");
        assertThat(updated.getDescription()).isEqualTo(newGame.getDescription());
    }

    @Test
    void startAndEndGame() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Game newGame = new Game();
        newGame.setTitle("the end and beginning");
        newGame.setOrganizerRef(crazyeyes.getId());
        newGame = gameRepo.createGame(newGame);
        assertThat(newGame.getId()).isNotZero();

        gameRepo.startGame(newGame.getId());
        Game updated1 = gameRepo.getById(newGame.getId());
        assertThat(updated1.getGameStatus()).isEqualTo(STARTED);

        gameRepo.endGame(newGame.getId());
        Game updated2 = gameRepo.getById(newGame.getId());
        assertThat(updated2.getGameStatus()).isEqualTo(COMPLETED);
    }
}