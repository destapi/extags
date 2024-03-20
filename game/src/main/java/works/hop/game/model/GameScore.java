package works.hop.game.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class GameScore {

    Game game;
    @Min(1)
    long gameRef;
    Player player;
    @Min(1)
    long playerRef;
    Question question;
    @Min(1)
    long questionRef;
    @NotNull
    @Length(max = 256)
    String response;
    int pointsEarned;
    LocalDateTime timePosted;
}
