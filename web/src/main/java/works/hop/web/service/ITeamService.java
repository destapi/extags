package works.hop.web.service;

import works.hop.game.model.Player;
import works.hop.game.model.Team;

import java.util.List;

public interface ITeamService extends IValidator<Team> {

    IResult<Team> getById(long teamId);

    IResult<List<Player>> teamMembers(long teamId);

    IResult<Team> createNewTeam(Team team);

    IResult<Team> registerTeam(Team team);

    IResult<Void> unregisterTeam(int teamId);

    IResult<Void> joinTeam(long teamId, long playerId);

    IResult<Void> leaveTeam(long teamId, long playerId);
}
