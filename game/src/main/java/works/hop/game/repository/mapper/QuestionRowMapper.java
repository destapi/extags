package works.hop.game.repository.mapper;

import org.springframework.jdbc.core.RowMapper;
import works.hop.game.model.Question;
import works.hop.game.model.QuestionType;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionRowMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setQuestion(rs.getString("question"));
        question.setQuestionType(QuestionType.valueOf(rs.getString("questionType")));
        question.setAnswer(rs.getString("answer"));
        question.setCreatedByRef(rs.getLong("createdByRef"));
        question.setAnswerReason(rs.getString("answerReason"));
        question.setMaxPoints(rs.getInt("maxPoints"));
        question.setDateCreated(rs.getTimestamp("dateCreated").toLocalDateTime());
        question.setId(rs.getLong("id"));
        return question;
    }
}
