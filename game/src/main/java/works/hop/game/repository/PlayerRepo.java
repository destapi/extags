package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;
import works.hop.game.repository.mapper.PlayerRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class PlayerRepo {

    final JdbcTemplate jdbcTemplate;

    public PlayerRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Player getById(Long id) {
        String SELECT_BY_ID = "select * from Player where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new PlayerRowMapper(), id);
    }

    public Player getByEmail(String emailAddress) {
        String SELECT_BY_EMAIL = "select * from Player where emailAddress = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, new PlayerRowMapper(), emailAddress);
    }

    public Player createPlayer(Player player) {
        String INSERT_ENTITY_SQL = "insert into Player (firstName, lastName, screenName, emailAddress, city, state) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Objects.requireNonNullElse(player.getFirstName(), ""));
            ps.setString(2, Objects.requireNonNullElse(player.getLastName(), ""));
            ps.setString(3, Objects.requireNonNull(player.getScreenName()));
            ps.setString(4, Objects.requireNonNull(player.getEmailAddress()));
            ps.setString(5, Objects.requireNonNullElse(player.getCity(), ""));
            ps.setString(6, Objects.requireNonNullElse(player.getState(), ""));
            return ps;
        }, keyHolder);

        player.setId((long) keyHolder.getKeyList().get(0).get("id"));
        return player;
    }

    public Player updatePlayer(Player player) {
        String UPDATE_ENTITY_SQL = "update Player set firstName = ?, lastName=?, screenName=?, city=?, state=? where emailAddress = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNullElse(player.getFirstName(), ""));
            ps.setString(2, Objects.requireNonNullElse(player.getLastName(), ""));
            ps.setString(3, Objects.requireNonNull(player.getScreenName()));
            ps.setString(4, Objects.requireNonNullElse(player.getCity(), ""));
            ps.setString(5, Objects.requireNonNullElse(player.getState(), ""));
            ps.setString(6, Objects.requireNonNull(player.getEmailAddress()));
            return ps;
        });

        return player;
    }

    public void updateStatus(long playerRef, PlayerStatus status) {
        String UPDATE_STATUS_SQL = "update Player set playerStatus = ? where id=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_STATUS_SQL);
            ps.setString(1, Objects.requireNonNull(status).name());
            ps.setLong(2, playerRef);
            return ps;
        });
    }

    public void removePlayer(long playerRef) {
        String REMOVE_ENTITY_SQL = "delete from Player where id=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(REMOVE_ENTITY_SQL);
            ps.setLong(1, playerRef);
            return ps;
        });
    }
}
