package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class GameScore {

    Game game;
    @NotNull
    long gameRef;
    Participant player;
    @NotNull
    long playerRef;
    Question question;
    @NotNull
    long questionRef;
    @NotNull
    @Length(max = 256)
    String response;
    int pointsEarned;
    LocalDateTime timePosted;
}
