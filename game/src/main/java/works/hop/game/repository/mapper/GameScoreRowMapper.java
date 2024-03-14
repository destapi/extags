package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.GameScore;
import works.hop.game.model.ProgressStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GameScoreRowMapper implements RowMapper<GameScore> {

    @Override
    public GameScore mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameScore gameScore = new GameScore();
        gameScore.setGameRef(rs.getLong("gameRef"));
        gameScore.setPlayerRef(rs.getLong("playerRef"));
        gameScore.setQuestionRef(rs.getLong("questionRef"));
        gameScore.setResponse(rs.getString("response"));
        gameScore.setPointsEarned(rs.getInt("pointsEarned"));
        gameScore.setGameRef(rs.getLong("gameRef"));
        gameScore.setTimePosted(rs.getTimestamp("timePosted").toLocalDateTime());
        return gameScore;
    }
}
