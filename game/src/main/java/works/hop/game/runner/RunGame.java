package works.hop.game.runner;

import works.hop.game.model.Player;
import works.hop.game.model.Question;

import java.time.LocalDateTime;

public interface RunGame {

    String initGame(Player organizer, String title, String description, LocalDateTime scheduledStart);

    String addParticipant(Player player);

    String joinGame();

    String ejectParticipant();

    String leaveGame();

    void onNextQuestion(Question question);

    String addScore(String player, String question, String response, int points);
}