package works.hop.web.service;

import works.hop.game.model.Question;

import java.util.List;

public interface IQuestionService extends IValidator<Question> {

    IResult<Question> getById(long questionId);

    IResult<Question> getByValue(long authorId, String question);

    IResult<List<Question>> getByAuthor(long authorId);

    IResult<Question> createQuestion(Question question);

    IResult<Question> updateQuestion(Question question);

    IResult<Void> removeQuestion(long questionId);
}
