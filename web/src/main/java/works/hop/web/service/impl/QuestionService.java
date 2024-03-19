package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Question;
import works.hop.game.repository.QuestionRepo;
import works.hop.web.service.IQuestionService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuestionService implements IQuestionService {

    final QuestionRepo questionRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }


    @Override
    public IResult<Question> getById(long questionId) {
        IResult<Question> result = new Result<>();
        try {
            result.data(questionRepo.getById(questionId));
        } catch (Exception e) {
            result.errors(Map.of("getById", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Question> getByValue(long authorId, String question) {
        IResult<Question> result = new Result<>();
        try {
            result.data(questionRepo.getByValue(authorId, question));
        } catch (Exception e) {
            result.errors(Map.of("getByValue", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<Question>> getByAuthor(long authorId) {
        IResult<List<Question>> result = new Result<>();
        try {
            result.data(questionRepo.getByAuthor(authorId));
        } catch (Exception e) {
            result.errors(Map.of("getByAuthor", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Question> createQuestion(Question question) {
        IResult<Question> result = new Result<>();
        try {
            Map<String, String> violations = validate(question);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(questionRepo.createQuestion(question));
        } catch (Exception e) {
            result.errors(Map.of("createNewGameScore", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Question> updateQuestion(Question question) {
        IResult<Question> result = new Result<>();
        try {
            Map<String, String> violations = validate(question);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(questionRepo.updateQuestion(question));
        } catch (Exception e) {
            result.errors(Map.of("updateGameScore", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removeQuestion(long questionId) {
        IResult<Void> result = new Result<>();
        try {
            questionRepo.removeQuestion(questionId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("clearGameScores", e.getMessage()));
        }
        return result;
    }
}
