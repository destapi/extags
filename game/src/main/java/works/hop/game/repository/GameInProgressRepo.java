package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.GameInProgress;
import works.hop.game.model.ProgressStatus;
import works.hop.game.repository.mapper.GameInProgressRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class GameInProgressRepo {

    final JdbcTemplate jdbcTemplate;

    public GameInProgressRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GameInProgress getById(Long id) {
        String SELECT_BY_ID = "select * from GameInProgress where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new GameInProgressRowMapper(), id);
    }

    public GameInProgress getByEmail(String emailAddress) {
        String SELECT_BY_EMAIL = "select * from GameInProgress where emailAddress = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL, new GameInProgressRowMapper(), emailAddress);
    }

    public GameInProgress createPlayer(GameInProgress progressing) {
        String INSERT_ENTITY_SQL = "insert into GameInProgress (gameRef, questionRef, groupNum, questionNum, autoProgression, progressStatus, delayBeforeCountdown, " +
                "delayAfterCountdown, countdownDuration, countdownIntervals) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setLong(1, progressing.getGameRef());
            ps.setLong(2, progressing.getQuestionRef());
            ps.setInt(3, progressing.getGroupNum());
            ps.setInt(4, progressing.getQuestionNum());
            ps.setBoolean(5, progressing.isAutoProgression());
            ps.setString(6, Optional.ofNullable(progressing.getProgressStatus()).map(Enum::name).orElse(ProgressStatus.READY.name()));
            ps.setLong(7, progressing.getDelayBeforeCountdown());
            ps.setLong(8, progressing.getDelayAfterCountdown());
            ps.setLong(9, progressing.getCountdownDuration());
            ps.setLong(10, progressing.getCountdownIntervals());
            return ps;
        });
        return progressing;
    }

    public GameInProgress updatePlayer(GameInProgress progressing) {
        String UPDATE_ENTITY_SQL = "update GameInProgress set groupNum = ?, questionNum = ?, autoProgression=?, progressStatus=?, delayBeforeCountdown=?, delayAfterCountdown=?, " +
                "countdownDuration=?, countdownIntervals=? where gameRef, questionRef = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setInt(1, progressing.getGroupNum());
            ps.setInt(2, progressing.getQuestionNum());
            ps.setBoolean(3, progressing.isAutoProgression());
            ps.setString(4, Optional.ofNullable(progressing.getProgressStatus()).map(Enum::name).orElse(ProgressStatus.READY.name()));
            ps.setLong(5, progressing.getDelayBeforeCountdown());
            ps.setLong(6, progressing.getDelayAfterCountdown());
            ps.setLong(7, progressing.getCountdownDuration());
            ps.setLong(8, progressing.getCountdownIntervals());
            ps.setLong(9, progressing.getGameRef());
            ps.setLong(10, progressing.getQuestionRef());
            return ps;
        });

        return progressing;
    }
}
