package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Choice;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ChoiceRowMapper implements RowMapper<Choice> {

    @Override
    public Choice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Choice game = new Choice();
        game.setOrdinal(rs.getInt("ordinal"));
        game.setQuestionRef(rs.getLong("questionRef"));
        game.setChoiceValue(rs.getString("choiceValue"));
        game.setExplanation(rs.getString("explanation"));
        return game;
    }
}
