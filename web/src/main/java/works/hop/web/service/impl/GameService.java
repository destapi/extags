package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Game;
import works.hop.game.model.Player;
import works.hop.game.repository.GameRepo;
import works.hop.web.service.IGameService;
import works.hop.web.service.IResult;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    final GameRepo gameRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<Game> getById(long gameId) {
        IResult<Game> result = new Result<>();
        try {
            result.data(gameRepo.getById(gameId));
        } catch (Exception e) {
            result.errors(Map.of("getById", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<Game>> getByOrganizer(long playerId) {
        IResult<List<Game>> result = new Result<>();
        try {
            result.data(gameRepo.getByOrganizer(playerId));
        } catch (Exception e) {
            result.errors(Map.of("getByOrganizer", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<Player>> getParticipants(long gameId) {
        IResult<List<Player>> result = new Result<>();
        try {
            result.data(gameRepo.getParticipants(gameId));
        } catch (Exception e) {
            result.errors(Map.of("getParticipants", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Game> createNewGame(Game game) {
        IResult<Game> result = new Result<>();
        try {
            Map<String, String> violations = validate(game);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameRepo.createGame(game));
        } catch (Exception e) {
            result.errors(Map.of("createNewGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Game> updateGame(Game game) {
        IResult<Game> result = new Result<>();
        try {
            Map<String, String> violations = validate(game);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(gameRepo.updateGame(game));
        } catch (Exception e) {
            result.errors(Map.of("updateGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> startGame(long gameId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.startGame(gameId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("startGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> endGame(long gameId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.endGame(gameId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("endGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> resetGame(long gameId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.resetGame(gameId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("resetGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> joinGame(long gameId, long playerId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.joinGame(gameId, playerId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("joinGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> leaveGame(long gameId, long playerId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.leaveGame(gameId, playerId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("leaveGame", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> clearParticipants(long gameId) {
        IResult<Void> result = new Result<>();
        try {
            gameRepo.clearParticipants(gameId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("clearParticipants", e.getMessage()));
        }
        return result;
    }
}
