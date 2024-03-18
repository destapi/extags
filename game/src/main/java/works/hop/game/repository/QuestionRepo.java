package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Question;
import works.hop.game.model.QuestionType;
import works.hop.game.repository.mapper.QuestionRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class QuestionRepo {

    final JdbcTemplate jdbcTemplate;

    public QuestionRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Question getById(Long id) {
        String SELECT_BY_ID = "select * from Question where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new QuestionRowMapper(), id);
    }

    public Question getByValue(Long createdBy, String question) {
        String SELECT_BY_ID = "select * from Question where createdByRef = ? and question = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new QuestionRowMapper(), createdBy, question);
    }

    public List<Question> getByAuthor(Long createdBy) {
        String SELECT_BY_ID = "select * from Question where createdByRef = ?";
        return jdbcTemplate.query(SELECT_BY_ID, new QuestionRowMapper(), createdBy);
    }

    public Question createQuestion(Question question) {
        String INSERT_ENTITY_SQL = "insert into Question (question, questionType, answer, answerReason, maxPoints, createdByRef) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Objects.requireNonNull(question.getQuestion()));
            ps.setString(2, Optional.ofNullable(question.getQuestionType()).map(Enum::name).orElse(QuestionType.GENERAL.name()));
            ps.setString(3, Objects.requireNonNullElse(question.getAnswer(), ""));
            ps.setString(4, Objects.requireNonNullElse(question.getAnswerReason(), ""));
            ps.setInt(5, question.getMaxPoints());
            ps.setLong(6, question.getCreatedByRef());
            return ps;
        }, keyHolder);

        question.setId((long) keyHolder.getKeyList().get(0).get("id"));
        return question;
    }

    public Question updateQuestion(Question question) {
        String UPDATE_ENTITY_SQL = "update Question set question = ?, questionType=?, answer=?, answerReason=?, maxPoints=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNull(question.getQuestion()));
            ps.setString(2, Optional.ofNullable(question.getQuestionType()).map(Enum::name).orElse(QuestionType.GENERAL.name()));
            ps.setString(3, Objects.requireNonNullElse(question.getAnswer(), ""));
            ps.setString(4, Objects.requireNonNullElse(question.getAnswerReason(), ""));
            ps.setInt(5, question.getMaxPoints());
            ps.setLong(6, question.getId());
            return ps;
        });

        return question;
    }
}
