package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;
import works.hop.game.repository.PlayerRepo;
import works.hop.web.service.IPlayerService;
import works.hop.web.service.IResult;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {

    final PlayerRepo playerRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<Player> getById(long playerId) {
        IResult<Player> result = new Result<>();
        try {
            result.data(playerRepo.getById(playerId));
        } catch (Exception e) {
            result.errors(Map.of("getById", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Player> getByEmailAddress(String emailAddress) {
        IResult<Player> result = new Result<>();
        try {
            result.data(playerRepo.getByEmail(emailAddress));
        } catch (Exception e) {
            result.errors(Map.of("getByEmail", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Player> createNewPlayer(Player player) {
        IResult<Player> result = new Result<>();
        try {
            Map<String, String> violations = validate(player);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(playerRepo.createPlayer(player));
        } catch (Exception e) {
            result.errors(Map.of("createNewPlayer", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Player> updatePlayer(Player player) {
        IResult<Player> result = new Result<>();
        try {
            Map<String, String> violations = validate(player);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(playerRepo.updatePlayer(player));
        } catch (Exception e) {
            result.errors(Map.of("updatePlayer", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> updateStatus(long playerId, PlayerStatus newStatus) {
        IResult<Void> result = new Result<>();
        try {
            playerRepo.updateStatus(playerId, newStatus);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("updateStatus", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removePlayer(long playerId) {
        IResult<Void> result = new Result<>();
        try {
            playerRepo.removePlayer(playerId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("removePlayer", e.getMessage()));
        }
        return result;
    }
}
