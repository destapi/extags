package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Clue;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ClueRowMapper implements RowMapper<Clue> {

    @Override
    public Clue mapRow(ResultSet rs, int rowNum) throws SQLException {
        Clue game = new Clue();
        game.setOrdinal(rs.getInt("ordinal"));
        game.setQuestionRef(rs.getLong("questionRef"));
        game.setClueValue(rs.getString("clueValue"));
        game.setExplanation(rs.getString("explanation"));
        return game;
    }
}
