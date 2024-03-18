package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.GameStep;
import works.hop.game.model.StepStatus;
import works.hop.game.repository.mapper.GameStepRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class GameStepRepo {

    final JdbcTemplate jdbcTemplate;

    public GameStepRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GameStep getGameStep(long gameRef, long questionRef) {
        String SELECT_BY_ID = "select * from GameStep where gameRef = ? and questionRef = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new GameStepRowMapper(), gameRef, questionRef);
    }

    public GameStep createGameStep(GameStep gameQ) {
        String INSERT_ENTITY_SQL = "insert into GameStep (gameRef, questionRef, groupNum, questionNum, autoProgression, delayBeforeCountdown, " +
                "delayAfterCountdown, countdownDuration, countdownIntervals, stepStatus) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setLong(1, gameQ.getGameRef());
            ps.setLong(2, gameQ.getQuestionRef());
            ps.setInt(3, gameQ.getGroupNum());
            ps.setInt(4, gameQ.getQuestionNum());
            ps.setBoolean(5, gameQ.isAutoProgression());
            ps.setLong(6, gameQ.getDelayBeforeCountdown());
            ps.setLong(7, gameQ.getDelayAfterCountdown());
            ps.setLong(8, gameQ.getCountdownDuration());
            ps.setLong(9, gameQ.getCountdownIntervals());
            ps.setString(10, Optional.ofNullable(gameQ.getStepStatus()).map(Enum::name).orElse(StepStatus.AWAITING_NEXT.name()));
            return ps;
        });
        return gameQ;
    }

    public GameStep updateGameStep(GameStep progressing) {
        String UPDATE_ENTITY_SQL = "update GameStep set groupNum = ?, questionNum = ?, autoProgression=?, delayBeforeCountdown=?, delayAfterCountdown=?, " +
                "countdownDuration=?, countdownIntervals=? where gameRef = ? and questionRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setInt(1, progressing.getGroupNum());
            ps.setInt(2, progressing.getQuestionNum());
            ps.setBoolean(3, progressing.isAutoProgression());
            ps.setLong(4, progressing.getDelayBeforeCountdown());
            ps.setLong(5, progressing.getDelayAfterCountdown());
            ps.setLong(6, progressing.getCountdownDuration());
            ps.setLong(7, progressing.getCountdownIntervals());
            ps.setLong(8, progressing.getGameRef());
            ps.setLong(9, progressing.getQuestionRef());
            return ps;
        });

        return progressing;
    }
}
