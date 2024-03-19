package works.hop.web.service;

import works.hop.game.model.GameScore;

import java.util.List;

public interface IGameScoreService extends IValidator<GameScore> {

    IResult<GameScore> getScoreByQuestion(long playerId, long gameId, long questionId);

    IResult<List<GameScore>> getScoresByPlayer(long playerId, long gameId);

    IResult<List<GameScore>> getScoresByGame(long gameId);

    IResult<GameScore> createNewGameScore(GameScore game);

    IResult<GameScore> updateGameScore(GameScore game);

    IResult<Integer> clearGameScores(long gameId);
}
