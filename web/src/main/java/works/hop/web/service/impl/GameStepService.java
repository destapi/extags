package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.GameStep;
import works.hop.game.repository.GameStepRepo;
import works.hop.web.service.IGameStepService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameStepService implements IGameStepService {

    final GameStepRepo gameStepRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<GameStep> getGameStep(long gameId, long questionId) {
        IResult<GameStep> result = new Result<>();
        try {
            result.data(gameStepRepo.getGameStep(gameId, questionId));
        } catch (Exception e) {
            result.errors(Map.of("getGameStep", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<GameStep>> getGameSteps(long gameId) {
        IResult<List<GameStep>> result = new Result<>();
        try {
            result.data(gameStepRepo.getGameSteps(gameId));
        } catch (Exception e) {
            result.errors(Map.of("getScoresByGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<GameStep> createNewGameStep(GameStep step) {
        IResult<GameStep> result = new Result<>();
        try {
            Map<String, String> violations = validate(step);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameStepRepo.createGameStep(step));
        } catch (Exception e) {
            result.errors(Map.of("createNewGameStep", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<GameStep> updateGameStep(GameStep step) {
        IResult<GameStep> result = new Result<>();
        try {
            Map<String, String> violations = validate(step);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameStepRepo.updateGameStep(step));
        } catch (Exception e) {
            result.errors(Map.of("updateGameStep", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removeGameStep(int groupNum, int questionNum) {
        IResult<Void> result = new Result<>();
        try {
            gameStepRepo.removeGameStep(groupNum, questionNum);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("removeGameStep", e.getMessage()));
        }
        return result;
    }
}
