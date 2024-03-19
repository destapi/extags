package works.hop.web.service;

import works.hop.game.model.Clue;

import java.util.List;

public interface IClueService extends IValidator<Clue> {

    IResult<List<Clue>> questionClues(long questionId);

    IResult<Clue> addClue(Clue clue);

    IResult<Clue> updateClue(Clue clue);

    IResult<Void> removeClue(int ordinal, long questionRef);
}
