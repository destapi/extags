package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.GameScore;
import works.hop.game.repository.mapper.GameScoreRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class GameScoreRepo {

    final JdbcTemplate jdbcTemplate;

    public GameScoreRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GameScore getById(Long id) {
        String SELECT_BY_ID = "select * from GameScore where gameRef=? and playerRef=? and ";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new GameScoreRowMapper(), id);
    }

    public GameScore getByEmail(String emailAddress) {
        String SELECT_BY_EMAIL = "select * from GameScore where emailAddress = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, new GameScoreRowMapper(), emailAddress);
    }

    public GameScore createPlayer(GameScore gameScore) {
        String INSERT_ENTITY_SQL = "insert into GameScore (gameRef, questionRef, questionRef, response, pointsEarned, timePosted) " +
                "values (?, ?, ?, ?, ?, ?, now())";
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

    public GameScore updatePlayer(GameScore gameScore) {
        String UPDATE_ENTITY_SQL = "update GameScore set response=?, pointsEarned=? where gameRef=?, questionRef=?, questionRef=?";
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
}
