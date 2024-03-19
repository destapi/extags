package works.hop.web.service;

import works.hop.game.model.GameStep;

import java.util.List;

public interface IGameStepService extends IValidator<GameStep> {

    IResult<GameStep> getGameStep(long gameId, long questionId);

    IResult<List<GameStep>> getGameSteps(long gameId);

    IResult<GameStep> createNewGameStep(GameStep step);

    IResult<GameStep> updateGameStep(GameStep step);

    IResult<Void> removeGameStep(int groupNum, int questionNum);
}
