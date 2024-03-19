package works.hop.web.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Game;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;
import works.hop.game.repository.GameRepo;
import works.hop.web.service.IGameService;
import works.hop.web.service.IPlayerService;
import works.hop.web.service.IResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService implements IGameService {

    final GameRepo gameRepo;
    final Validator validator;

    @Override
    public Map<String, String> validate(Player model) {
        Set<ConstraintViolation<Player>> violations = validator.validate(model);
        if (!violations.isEmpty()) {
            return violations.stream().map(v -> new String[]{v.getLeafBean().toString(), v.getMessage()})
                    .collect(Collectors.toMap(v -> v[0], v -> v[1]));
        }
        return Collections.emptyMap();
    }

    @Override
    public IResult<Game> getById(long gameId) {
        return null;
    }

    @Override
    public IResult<Game> getByOrganizer(long playerId) {
        return null;
    }

    @Override
    public IResult<List<Player>> getParticipants(long gameId) {
        return null;
    }

    @Override
    public IResult<Game> createGame(Game game) {
        return null;
    }

    @Override
    public IResult<Game> updateGame(Game game) {
        return null;
    }

    @Override
    public IResult<Void> startGame(long gameId) {
        return null;
    }

    @Override
    public IResult<Void> endGame(long gameId) {
        return null;
    }

    @Override
    public IResult<Void> resetGame(long gameId) {
        return null;
    }

    @Override
    public IResult<Void> joinGame(long gameId, long playerId) {
        return null;
    }

    @Override
    public IResult<Void> leaveGame(long gameId, long playerId) {
        return null;
    }

    @Override
    public IResult<Void> clearParticipants(long gameId) {
        return null;
    }
}
