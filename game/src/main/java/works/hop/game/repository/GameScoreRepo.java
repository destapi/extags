package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.GameScore;
import works.hop.game.repository.mapper.GameScoreRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class GameScoreRepo {

    final JdbcTemplate jdbcTemplate;

    public GameScoreRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GameScore getByQuestonRef(long playerRef, long gameRef, long questionRef) {
        String SELECT_BY_QUE_ID = "select * from GameScore where gameRef=? and playerRef=? and questionRef = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_QUE_ID, new GameScoreRowMapper(), gameRef, playerRef, questionRef);
    }

    public List<GameScore> getByPlayerRef(long playerRef, long gameRef) {
        String SELECT_BY_EMAIL = "select * from GameScore where gameRef=? and playerRef=?";
        return jdbcTemplate.query(SELECT_BY_EMAIL, new GameScoreRowMapper(), playerRef, gameRef);
    }

    public List<GameScore> getByGameRef(long gameRef) {
        String SELECT_BY_EMAIL = "select * from GameScore where gameRef = ?";
        return jdbcTemplate.query(SELECT_BY_EMAIL, new GameScoreRowMapper(), gameRef);
    }

    public GameScore createGameScore(GameScore gameScore) {
        String INSERT_ENTITY_SQL = "insert into GameScore (gameRef, playerRef, questionRef, response, pointsEarned, timePosted) " +
                "values (?, ?, ?, ?, ?, now())";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setLong(1, gameScore.getGameRef());
            ps.setLong(2, gameScore.getPlayerRef());
            ps.setLong(3, gameScore.getQuestionRef());
            ps.setString(4, Objects.requireNonNull(gameScore.getResponse()));
            ps.setInt(5, gameScore.getPointsEarned());
            return ps;
        });
        return gameScore;
    }

    public GameScore updateGameScore(GameScore gameScore) {
        String UPDATE_ENTITY_SQL = "update GameScore set response=?, pointsEarned=? where gameRef=? and playerRef=? and questionRef=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNull(gameScore.getResponse()));
            ps.setInt(2, gameScore.getPointsEarned());
            ps.setLong(3, gameScore.getGameRef());
            ps.setLong(4, gameScore.getPlayerRef());
            ps.setLong(5, gameScore.getQuestionRef());
            return ps;
        });

        return gameScore;
    }

    public int clearGameScores(long gameRef) {
        String CLEAR_ENTITIES_SQL = "delete from GameScore where gameRef=?";
        return this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CLEAR_ENTITIES_SQL);
            ps.setLong(1, gameRef);
            return ps;
        });
    }
}
