package works.hop.web.service;

import works.hop.game.model.Player;

public interface IPlayerService extends Validator<Player> {

    IResult<Player> getById(long id);

    IResult<Player> getByEmailAddress(String emailAddress);

    IResult<Player> createNewPlayer(Player id);

    IResult<Player> updatePlayer(long id);

    IResult<Player> updateStatus(long id);

    IResult<Player> removePlayer(long id);
}
