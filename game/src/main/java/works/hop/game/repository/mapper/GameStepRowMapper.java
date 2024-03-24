package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.GameStep;
import works.hop.game.model.StepStatus;

import java.sql.ResultSet;
import java.sql.SQLException;


public class GameStepRowMapper implements RowMapper<GameStep> {

    @Override
    public GameStep mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameStep inProgress = new GameStep();
        inProgress.setGroupNum(rs.getInt("groupNum"));
        inProgress.setQuestionNum(rs.getInt("questionNum"));
        inProgress.setGameRef(rs.getLong("gameRef"));
        inProgress.setQuestionRef(rs.getLong("questionRef"));
        inProgress.setAutoProgression(rs.getBoolean("autoProgression"));
        inProgress.setDelayAfterCountdown(rs.getLong("delayAfterCountdown"));
        inProgress.setDelayBeforeCountdown(rs.getLong("delayBeforeCountdown"));
        inProgress.setCountdownIntervals(rs.getLong("countdownIntervals"));
        inProgress.setCountdownDuration(rs.getLong("countdownDuration"));
        inProgress.setMaxPoints(rs.getInt("maxPoints"));
        inProgress.setCorrectChoice(rs.getInt("correctChoice"));
        inProgress.setStepStatus(StepStatus.valueOf(rs.getString("stepStatus")));
        return inProgress;
    }
}
