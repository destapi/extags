package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Game;
import works.hop.game.model.GameStatus;
import works.hop.game.repository.mapper.GameRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Repository
public class GameRepo {

    final JdbcTemplate jdbcTemplate;

    public GameRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Game getById(Long id) {
        String SELECT_BY_ID = "select * from Game where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new GameRowMapper(), id);
    }

    public Game createGame(Game game) {
        String INSERT_ENTITY_SQL = "insert into Game (title, description, organizerRef) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Objects.requireNonNull(game.getTitle()));
            ps.setString(2, Objects.requireNonNullElse(game.getDescription(), ""));
            ps.setLong(3, game.getOrganizerRef());
            return ps;
        }, keyHolder);

        game.setId((long) keyHolder.getKeyList().get(0).get("id"));
        return game;
    }

    public Game updateGame(Game game) {
        String UPDATE_ENTITY_SQL = "update Game set title = ?, description=?, gameStatus=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNullElse(game.getTitle(), ""));
            ps.setString(2, Objects.requireNonNullElse(game.getDescription(), ""));
            ps.setString(3, Objects.requireNonNull(game.getGameStatus().name()));
            ps.setLong(4, game.getId());
            return ps;
        });

        return game;
    }

    public void startGame(long gameId) {
        String UPDATE_ENTITY_SQL = "update Game set gameStatus=?, timeStarted=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, GameStatus.STARTED.name());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, gameId);
            return ps;
        });
    }

    public void endGame(long gameId) {
        String UPDATE_ENTITY_SQL = "update Game set gameStatus=?, timeEnded=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, GameStatus.COMPLETED.name());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, gameId);
            return ps;
        });
    }

    public void joinGame(long gameId, long playerId) {
        String UPDATE_ENTITY_SQL = "update Game set gameStatus=?, timeEnded=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, GameStatus.COMPLETED.name());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(2, gameId);
            return ps;
        });
    }
}
