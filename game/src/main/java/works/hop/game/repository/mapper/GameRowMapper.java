package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Game;
import works.hop.game.model.GameStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;


public class GameRowMapper implements RowMapper<Game> {

    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        Game game = new Game();
        game.setId(rs.getLong("id"));
        game.setTitle(rs.getString("title"));
        game.setOrganizerRef(rs.getLong("organizerRef"));
        game.setDescription(rs.getString("description"));
        game.setGameStatus(GameStatus.valueOf(rs.getString("gameStatus")));
        game.setTimeStarted(Optional.ofNullable(rs.getTimestamp("timeStarted")).map(Timestamp::toLocalDateTime).orElse(null));
        game.setTimeEnded(Optional.ofNullable(rs.getTimestamp("timeEnded")).map(Timestamp::toLocalDateTime).orElse(null));
        game.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
        return game;
    }
}
