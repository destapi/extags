package works.hop.web.service;

import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;

public interface IPlayerService extends Validator<Player> {

    IResult<Player> getById(long id);

    IResult<Player> getByEmailAddress(String emailAddress);

    IResult<Player> createNewPlayer(Player id);

    IResult<Player> updatePlayer(Player player);

    IResult<Void> updateStatus(long id, PlayerStatus newStatus);

    IResult<Void> removePlayer(long id);
}
