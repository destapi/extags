package works.hop.game.service;

import works.hop.game.model.Participant;
import works.hop.game.model.Question;

import java.time.LocalDateTime;

public interface RunGame {

    String initGame(Participant organizer, String title, String description, LocalDateTime scheduledStart);

    String addParticipant(Participant player);

    String joinGame();

    String ejectParticipant();

    String leaveGame();

    void onNextQuestion(Question question);

    String addScore(String player, String question, String response, int points);
}