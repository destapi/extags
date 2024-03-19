package works.hop.web.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;
import works.hop.game.repository.PlayerRepo;
import works.hop.web.service.IPlayerService;
import works.hop.web.service.IResult;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService implements IPlayerService {

    final PlayerRepo playerRepo;
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
    public IResult<Player> getById(long id) {
        return null;
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
    public IResult<Void> updateStatus(long id, PlayerStatus newStatus) {
        IResult<Void> result = new Result<>();
        try {
            playerRepo.updateStatus(id, newStatus);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("updateStatus", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> removePlayer(long id) {
        IResult<Void> result = new Result<>();
        try {
            playerRepo.removePlayer(id);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("removePlayer", e.getMessage()));
        }
        return result;
    }
}
