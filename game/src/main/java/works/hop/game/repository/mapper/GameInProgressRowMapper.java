package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.GameInProgress;
import works.hop.game.model.ProgressStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GameInProgressRowMapper implements RowMapper<GameInProgress> {

    @Override
    public GameInProgress mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameInProgress inProgress = new GameInProgress();
        inProgress.setGroupNum(rs.getInt("groupNum"));
        inProgress.setQuestionNum(rs.getInt("questionNum"));
        inProgress.setQuestionRef(rs.getLong("questionRef"));
        inProgress.setAutoProgression(rs.getBoolean("autoProgression"));
        inProgress.setProgressStatus(ProgressStatus.valueOf(rs.getString("progressStatus")));
        inProgress.setGameRef(rs.getLong("gameRef"));
        inProgress.setCountdownIntervals(rs.getLong("countdownIntervals"));
        inProgress.setCountdownDuration(rs.getLong("countdownDuration"));
        inProgress.setDelayAfterCountdown(rs.getLong("delayAfterCountdown"));
        inProgress.setDelayBeforeCountdown(rs.getLong("delayBeforeCountdown"));
        inProgress.setTimeStarted(rs.getTimestamp("timeStarted").toLocalDateTime());
        inProgress.setTimeEnded(rs.getTimestamp("timeEnded").toLocalDateTime());
        return inProgress;
    }
}
