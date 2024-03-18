package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Choice;
import works.hop.game.repository.mapper.ChoiceRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class ChoiceRepo {

    final JdbcTemplate jdbcTemplate;

    public ChoiceRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Choice> getByRef(Long questionRef) {
        String SELECT_BY_REF_ID = "select * from Choice where questionRef = ?";
        return jdbcTemplate.query(SELECT_BY_REF_ID, new ChoiceRowMapper(), questionRef);
    }

    public Choice createChoice(Choice choice) {
        String INSERT_ENTITY_SQL = "insert into Choice (ordinal, questionRef, choiceValue, explanation) values (?, ?, ?, ?)";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL);
            ps.setInt(1, choice.getOrdinal());
            ps.setLong(2, choice.getQuestionRef());
            ps.setString(3, Objects.requireNonNull(choice.getChoiceValue(), ""));
            ps.setString(4, Objects.requireNonNullElse(choice.getExplanation(), ""));
            return ps;
        });

        return choice;
    }

    public Choice updateChoice(Choice choice) {
        String UPDATE_ENTITY_SQL = "update Choice set choiceValue=?, explanation=? where ordinal = ? and questionRef=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNull(choice.getChoiceValue()));
            ps.setString(2, Objects.requireNonNullElse(choice.getExplanation(), ""));
            ps.setInt(3, choice.getOrdinal());
            ps.setLong(4, choice.getQuestionRef());
            return ps;
        });

        return choice;
    }

    public void removeChoice(int ordinal, long questionRef) {
        String UPDATE_ENTITY_SQL = "delete from Choice where ordinal = ? && questionRef=?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setInt(1, ordinal);
            ps.setLong(2, questionRef);
            return ps;
        });
    }
}
