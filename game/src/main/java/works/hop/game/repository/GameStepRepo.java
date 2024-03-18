package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.GameStep;
import works.hop.game.model.StepStatus;
import works.hop.game.repository.mapper.GameStepRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
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

    public List<GameStep> getGameSteps(long gameRef) {
        String SELECT_BY_ID = "select * from GameStep where gameRef = ?";
        return jdbcTemplate.query(SELECT_BY_ID, new GameStepRowMapper(), gameRef);
    }

    public GameStep createGameStep(GameStep step) {
        String INSERT_ENTITY_SQL = "insert into GameStep (gameRef, questionRef, groupNum, questionNum, autoProgression, delayBeforeCountdown, " +
                "delayAfterCountdown, countdownDuration, countdownIntervals, stepStatus) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setLong(1, step.getGameRef());
            ps.setLong(2, step.getQuestionRef());
            ps.setInt(3, step.getGroupNum());
            ps.setInt(4, step.getQuestionNum());
            ps.setBoolean(5, step.isAutoProgression());
            ps.setLong(6, step.getDelayBeforeCountdown());
            ps.setLong(7, step.getDelayAfterCountdown());
            ps.setLong(8, step.getCountdownDuration());
            ps.setLong(9, step.getCountdownIntervals());
            ps.setString(10, Optional.ofNullable(step.getStepStatus()).map(Enum::name).orElse(StepStatus.AWAITING_NEXT.name()));
            return ps;
        });
        return step;
    }

    public GameStep updateGameStep(GameStep step) {
        String UPDATE_ENTITY_SQL = "update GameStep set groupNum = ?, questionNum = ?, autoProgression=?, delayBeforeCountdown=?, delayAfterCountdown=?, " +
                "countdownDuration=?, countdownIntervals=? where gameRef = ? and questionRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setInt(1, step.getGroupNum());
            ps.setInt(2, step.getQuestionNum());
            ps.setBoolean(3, step.isAutoProgression());
            ps.setLong(4, step.getDelayBeforeCountdown());
            ps.setLong(5, step.getDelayAfterCountdown());
            ps.setLong(6, step.getCountdownDuration());
            ps.setLong(7, step.getCountdownIntervals());
            ps.setLong(8, step.getGameRef());
            ps.setLong(9, step.getQuestionRef());
            return ps;
        });

        return step;
    }

    public void removeGameStep(GameStep step) {
        String REMOVE_ENTITY_SQL = "delete from GameStep where gameRef = ? and questionRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(REMOVE_ENTITY_SQL);
            ps.setInt(1, step.getGroupNum());
            ps.setInt(2, step.getQuestionNum());
            return ps;
        });
    }
}
