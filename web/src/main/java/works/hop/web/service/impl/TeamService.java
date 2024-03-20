package works.hop.web.service.impl;

import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import works.hop.game.model.Player;
import works.hop.game.model.Team;
import works.hop.game.repository.TeamRepo;
import works.hop.web.service.IResult;
import works.hop.web.service.ITeamService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TeamService implements ITeamService {

    final TeamRepo teamRepo;
    final Validator validator;

    @Override
    public Validator validator() {
        return this.validator;
    }

    @Override
    public IResult<Team> getById(long teamId) {
        IResult<Team> result = new Result<>();
        try {
            result.data(teamRepo.getById(teamId));
        } catch (Exception e) {
            result.errors(Map.of("getById", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<List<Player>> teamMembers(long teamId) {
        IResult<List<Player>> result = new Result<>();
        try {
            result.data(teamRepo.teamMembers(teamId));
        } catch (Exception e) {
            result.errors(Map.of("teamMembers", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Team> createNewTeam(Team team) {
        IResult<Team> result = new Result<>();
        try {
            Map<String, String> violations = validate(team);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(teamRepo.createTeam(team));
        } catch (Exception e) {
            result.errors(Map.of("createNewTeam", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Team> updateTeam(Team team) {
        IResult<Team> result = new Result<>();
        try {
            Map<String, String> violations = validate(team);
            if (!violations.isEmpty()) {
                result.errors(violations);
                return result;
            }
            result.data(teamRepo.updateTeam(team));
        } catch (Exception e) {
            result.errors(Map.of("registerTeam", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> deleteTeam(long teamId) {
        IResult<Void> result = new Result<>();
        try {
            teamRepo.deleteTeam(teamId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("unregisterTeam", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> joinTeam(long teamId, long playerId) {
        IResult<Void> result = new Result<>();
        try {
            teamRepo.joinTeam(teamId, playerId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("joinTeam", e.getMessage()));
        }
        return result;
    }

    @Override
    public IResult<Void> leaveTeam(long teamId, long playerId) {
        IResult<Void> result = new Result<>();
        try {
            teamRepo.leaveTeam(teamId, playerId);
            return new Result<>(null);
        } catch (Exception e) {
            result.errors(Map.of("leaveTeam", e.getMessage()));
        }
        return result;
    }
}
