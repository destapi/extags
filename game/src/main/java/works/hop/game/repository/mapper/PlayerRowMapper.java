package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class PlayerRowMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        Player player = new Player();
        player.setCity(rs.getString("city"));
        player.setEmailAddress(rs.getString("emailAddress"));
        player.setLastName(rs.getString("lastName"));
        player.setFirstName(rs.getString("firstName"));
        player.setState(rs.getString("state"));
        player.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
        player.setId(rs.getLong("id"));
        player.setPlayerStatus(PlayerStatus.valueOf(rs.getString("playerStatus")));
        return player;
    }
}
