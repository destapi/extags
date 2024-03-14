package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Participant;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet rs, int rowNum) throws SQLException {
        Participant participant = new Participant();
        participant.setCity(rs.getString("city"));
        participant.setEmailAddress(rs.getString("emailAddress"));
        participant.setLastName(rs.getString("lastName"));
        participant.setFirstName(rs.getString("firstName"));
        participant.setState(rs.getString("state"));
        participant.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
        participant.setId(rs.getLong("id"));
        return participant;
    }
}
