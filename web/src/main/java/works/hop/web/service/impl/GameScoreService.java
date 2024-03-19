package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.GameScore;
import works.hop.game.repository.GameScoreRepo;
import works.hop.web.service.IGameScoreService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameScoreService implements IGameScoreService {

    final GameScoreRepo gameScoreRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<GameScore> getScoreByQuestion(long playerId, long gameId, long questionId) {
        IResult<GameScore> result = new Result<>();
        try {
            result.data(gameScoreRepo.getByQuestionRef(playerId, gameId, questionId));
        } catch (Exception e) {
            result.errors(Map.of("getScoreByQuestion", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<GameScore>> getScoresByPlayer(long playerId, long gameId) {
        IResult<List<GameScore>> result = new Result<>();
        try {
            result.data(gameScoreRepo.getByPlayerRef(playerId, gameId));
        } catch (Exception e) {
            result.errors(Map.of("getScoresByPlayer", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<GameScore>> getScoresByGame(long gameId) {
        IResult<List<GameScore>> result = new Result<>();
        try {
            result.data(gameScoreRepo.getByGameRef(gameId));
        } catch (Exception e) {
            result.errors(Map.of("getScoresByGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<GameScore> createNewGameScore(GameScore score) {
        IResult<GameScore> result = new Result<>();
        try {
            Map<String, String> violations = validate(score);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameScoreRepo.createGameScore(score));
        } catch (Exception e) {
            result.errors(Map.of("createNewGameScore", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<GameScore> updateGameScore(GameScore score) {
        IResult<GameScore> result = new Result<>();
        try {
            Map<String, String> violations = validate(score);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameScoreRepo.updateGameScore(score));
        } catch (Exception e) {
            result.errors(Map.of("updateGameScore", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Integer> clearGameScores(long gameId) {
        IResult<Integer> result = new Result<>();
        try {
            result.data(gameScoreRepo.clearGameScores(gameId));
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("clearGameScores", e.getMessage()));
        }
        return result;
    }
}
