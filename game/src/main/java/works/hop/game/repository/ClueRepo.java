package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Clue;
import works.hop.game.repository.mapper.ClueRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ClueRepo {

    final JdbcTemplate jdbcTemplate;

    public ClueRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Clue> getByRef(Long questionRef) {
        String SELECT_BY_REF_ID = "select * from Clue where questionRef = ?";
        return jdbcTemplate.query(SELECT_BY_REF_ID, new ClueRowMapper(), questionRef);
    }

    public Clue createClue(Clue clue) {
        String INSERT_ENTITY_SQL = "insert into Clue (ordinal, questionRef, clueValue, explanation) values (?, ?, ?, ?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setInt(1, clue.getOrdinal());
            ps.setLong(2, clue.getQuestionRef());
            ps.setString(3, Objects.requireNonNull(clue.getClueValue(), ""));
            ps.setString(4, Objects.requireNonNullElse(clue.getExplanation(), ""));
            return ps;
        });

        return clue;
    }

    public Clue updateClue(Clue clue) {
        String UPDATE_ENTITY_SQL = "update Clue set clueValue=?, explanation=? where ordinal = ? and questionRef=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNull(clue.getClueValue()));
            ps.setString(2, Objects.requireNonNullElse(clue.getExplanation(), ""));
            ps.setInt(3, clue.getOrdinal());
            ps.setLong(4, clue.getQuestionRef());
            return ps;
        });

        return clue;
    }

    public void removeClue(int ordinal, long questionRef) {
        String UPDATE_ENTITY_SQL = "delete from Clue where ordinal = ? && questionRef=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setInt(1, ordinal);
            ps.setLong(2, questionRef);
            return ps;
        });
    }
}
