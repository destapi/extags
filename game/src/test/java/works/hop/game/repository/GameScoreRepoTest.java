package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Game;
import works.hop.game.model.GameScore;
import works.hop.game.model.Player;
import works.hop.game.model.Question;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class GameScoreRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    GameScoreRepo gameScoreRepo;
    GameRepo gameRepo;
    PlayerRepo playerRepo;
    QuestionRepo questionRepo;

    @BeforeEach
    void setUp() {
        gameScoreRepo = new GameScoreRepo(embeddedDataSource);
        playerRepo = new PlayerRepo(embeddedDataSource);
        gameRepo = new GameRepo(embeddedDataSource);
        questionRepo = new QuestionRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateGameScore() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        List<Game> games = gameRepo.getByOrganizer(crazyeyes.getId());
        Game game = games.get(0);
        Question question = questionRepo.getByValue(crazyeyes.getId(), "1 + 1");

        GameScore newGameScore = new GameScore();
        newGameScore.setPlayerRef(crazyeyes.getId());
        newGameScore.setGameRef(game.getId());
        newGameScore.setQuestionRef(question.getId());
        newGameScore.setResponse("Something good");
        newGameScore = gameScoreRepo.createGameScore(newGameScore);
        assertThat(newGameScore.getPointsEarned()).isEqualTo(0);

        newGameScore.setResponse("rock and rolling");
        newGameScore.setPointsEarned(1000);
        newGameScore = gameScoreRepo.updateGameScore(newGameScore);
        GameScore updated = gameScoreRepo.getByQuestionRef(crazyeyes.getId(), game.getId(), question.getId());
        assertThat(updated.getPointsEarned()).isEqualTo(newGameScore.getPointsEarned());
        assertThat(updated.getResponse()).isEqualTo(newGameScore.getResponse());
    }

    @Test
    void clearGameScores() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Player bigfoot = playerRepo.getByEmail("casssie.bigfoot@email.com");
        List<Game> games = gameRepo.getByOrganizer(crazyeyes.getId());
        Game game = games.get(0);
        Question question = questionRepo.getByValue(crazyeyes.getId(), "1 + 1");

        GameScore newGameScore1 = new GameScore();
        newGameScore1.setPlayerRef(bigfoot.getId());
        newGameScore1.setGameRef(game.getId());
        newGameScore1.setQuestionRef(question.getId());
        newGameScore1.setResponse("Something good");
        newGameScore1 = gameScoreRepo.createGameScore(newGameScore1);

        GameScore newGameScore2 = new GameScore();
        newGameScore2.setPlayerRef(crazyeyes.getId());
        newGameScore2.setGameRef(game.getId());
        newGameScore2.setQuestionRef(question.getId());
        newGameScore2.setResponse("Something must be right");
        newGameScore2 = gameScoreRepo.createGameScore(newGameScore2);

        List<GameScore> scoresBy1 = gameScoreRepo.getByPlayerRef(game.getId(), bigfoot.getId());
        assertThat(scoresBy1).hasSize(1);
        assertThat(scoresBy1.get(0).getPlayerRef()).isEqualTo(bigfoot.getId());

        List<GameScore> currentScores = gameScoreRepo.getByGameRef(game.getId());
        assertThat(currentScores).hasSize(2);

        // clear scores
        int cleared = gameScoreRepo.clearGameScores(game.getId());
        assertThat(cleared).isEqualTo(2);
    }
}