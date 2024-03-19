package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Game;
import works.hop.game.model.GameStatus;
import works.hop.game.model.Player;
import works.hop.game.repository.mapper.GameRowMapper;
import works.hop.game.repository.mapper.PlayerRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class GameRepo {

    final JdbcTemplate jdbcTemplate;

    public GameRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Game getById(Long id) {
        String SELECT_BY_ID = "select * from Game where id = ?";
        Game game = jdbcTemplate.queryForObject(SELECT_BY_ID, new GameRowMapper(), id);
        String SELECT_CAPTAIN_BY_ID = "select * from Player where id = ?";
        Player organizer = jdbcTemplate.queryForObject(SELECT_CAPTAIN_BY_ID, new PlayerRowMapper(),
                Objects.requireNonNull(game).getOrganizerRef());
        game.setOrganizer(organizer);
        return game;
    }

    public List<Game> getByOrganizer(long organizerRef) {
        String SELECT_BY_ID = "select * from Game where organizerRef = ?";
        return jdbcTemplate.query(SELECT_BY_ID, new GameRowMapper(), organizerRef);
    }

    public List<Player> getParticipants(long gameId) {
        String SELECT_GAME_PARTICIPANTS = "select * from Player where id in (select playerRef from Participant where gameRef = ?)";
        return jdbcTemplate.query(SELECT_GAME_PARTICIPANTS, new PlayerRowMapper(), gameId);
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
        String UPDATE_ENTITY_SQL = "update Game set title = ?, description=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNullElse(game.getTitle(), ""));
            ps.setString(2, Objects.requireNonNullElse(game.getDescription(), ""));
            ps.setLong(3, game.getId());
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
            ps.setLong(3, gameId);
            return ps;
        });
    }

    public void endGame(long gameId) {
        String UPDATE_ENTITY_SQL = "update Game set gameStatus=?, timeEnded=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, GameStatus.COMPLETED.name());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(3, gameId);
            return ps;
        });
    }

    public void resetGame(long gameId) {
        String UPDATE_ENTITY_SQL = "update Game set gameStatus = ?, timestarted = ?, timeEnded = ? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, GameStatus.READY.name());
            ps.setTimestamp(2, null);
            ps.setTimestamp(3, null);
            ps.setLong(4, gameId);
            return ps;
        });
    }

    public void joinGame(long gameRef, long playerRef) {
        String INSERT_ENTITY_SQL = "insert into Participant (gameRef, playerRef, timeJoined) values (?, ?, now()) ";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_ENTITY_SQL);
            ps.setLong(1, gameRef);
            ps.setLong(2, playerRef);
            return ps;
        });
    }

    public void leaveGame(long gameRef, long playerRef) {
        String DELETE_ENTITY_SQL = "delete from Participant where gameRef = ? and playerRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(DELETE_ENTITY_SQL);
            ps.setLong(1, gameRef);
            ps.setLong(2, playerRef);
            return ps;
        });
    }

    public void clearParticipants(long gameRef) {
        String DELETE_ENTITY_SQL = "delete from Participant where gameRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(DELETE_ENTITY_SQL);
            ps.setLong(1, gameRef);
            return ps;
        });
    }
}
