package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Team;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


public class TeamRowMapper implements RowMapper<Team> {

    @Override
    public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
        Team game = new Team();
        game.setName(rs.getString("name"));
        game.setCity(rs.getString("city"));
        game.setState(rs.getString("state"));
        game.setCaptainRef(rs.getLong("captainRef"));
        game.setDateCreated(Optional.ofNullable(rs.getTimestamp("dateCreated")).map(Timestamp::toLocalDateTime).orElse(null));
        game.setId(rs.getLong("id"));
        return game;
    }
}
