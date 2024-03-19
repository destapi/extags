package works.hop.web.service;

import works.hop.game.model.Choice;

import java.util.List;

public interface IChoiceService extends IValidator<Choice> {

    IResult<List<Choice>> questionChoices(long questionId);

    IResult<Choice> addChoice(Choice choice);

    IResult<Choice> updateChoice(Choice choice);

    IResult<Void> removeChoice(int ordinal, long questionRef);
}
