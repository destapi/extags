package works.hop.web.service;

import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;

public interface IPlayerService extends IValidator<Player> {

    IResult<Player> getById(long playerId);

    IResult<Player> getByEmailAddress(String emailAddress);

    IResult<Player> createNewPlayer(Player player);

    IResult<Player> updatePlayer(Player player);

    IResult<Void> updateStatus(long playerId, PlayerStatus newStatus);

    IResult<Void> removePlayer(long playerId);
}
