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
        game.setGameStatus(GameStatus.valueOf(rs.getString("gameStatus")));
        game.setTitle(rs.getString("title"));
        game.setOrganizerRef(rs.getLong("organizerRef"));
        game.setDescription(rs.getString("description"));
        game.setStartTime(Optional.ofNullable(rs.getTimestamp("startTime")).map(Timestamp::toLocalDateTime).orElse(null));
        game.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
        game.setId(rs.getLong("id"));
        return game;
    }
}
