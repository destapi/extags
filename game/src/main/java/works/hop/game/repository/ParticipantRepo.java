package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Participant;
import works.hop.game.repository.mapper.ParticipantRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class ParticipantRepo {

    final JdbcTemplate jdbcTemplate;

    public ParticipantRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Participant getById(Long id) {
        String SELECT_BY_ID = "select * from Participant where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new ParticipantRowMapper(), id);
    }

    public Participant getByEmail(String emailAddress) {
        String SELECT_BY_EMAIL = "select * from Participant where emailAddress = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, new ParticipantRowMapper(), emailAddress);
    }

    public Participant createPlayer(Participant participant) {
        String INSERT_ENTITY_SQL = "insert into Participant (firstName, lastName, screenName, emailAddress, city, state) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Objects.requireNonNullElse(participant.getFirstName(), ""));
            ps.setString(2, Objects.requireNonNullElse(participant.getLastName(), ""));
            ps.setString(3, Objects.requireNonNull(participant.getScreenName()));
            ps.setString(4, Objects.requireNonNull(participant.getEmailAddress()));
            ps.setString(5, Objects.requireNonNullElse(participant.getCity(), ""));
            ps.setString(6, Objects.requireNonNullElse(participant.getState(), ""));
            return ps;
        }, keyHolder);

        participant.setId((long) keyHolder.getKeyList().get(0).get("id"));
        return participant;
    }

    public Participant updatePlayer(Participant participant) {
        String UPDATE_ENTITY_SQL = "update Participant set firstName = ?, lastName=?, screenName=?, city=?, state=? where emailAddress = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNullElse(participant.getFirstName(), ""));
            ps.setString(2, Objects.requireNonNullElse(participant.getLastName(), ""));
            ps.setString(3, Objects.requireNonNull(participant.getScreenName()));
            ps.setString(4, Objects.requireNonNullElse(participant.getCity(), ""));
            ps.setString(5, Objects.requireNonNullElse(participant.getState(), ""));
            ps.setString(6, Objects.requireNonNull(participant.getEmailAddress()));
            return ps;
        });

        return participant;
    }
}
