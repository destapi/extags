package works.hop.web.service;

import works.hop.game.model.Game;
import works.hop.game.model.Player;
import works.hop.game.model.PlayerStatus;

import java.util.List;

public interface IGameService extends Validator<Player> {

    IResult<Game> getById(long gameId);

    IResult<Game> getByOrganizer(long playerId);

    IResult<List<Player>> getParticipants(long gameId);

    IResult<Game> createGame(Game game);

    IResult<Game> updateGame(Game game);

    IResult<Void> startGame(long gameId);

    IResult<Void> endGame(long gameId);

    IResult<Void> resetGame(long gameId);

    IResult<Void> joinGame(long gameId, long playerId);

    IResult<Void> leaveGame(long gameId, long playerId);

    IResult<Void> clearParticipants(long gameId);
}
