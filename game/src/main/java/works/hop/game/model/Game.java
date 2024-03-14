package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Game {

    long id;
    @NotNull
    @Length(max = 64)
    String title;
    @Length(max = 256)
    String description;
    Participant organizer;
    @NotNull
    long organizerRef;
    @NotNull
    GameStatus gameStatus;
    LocalDateTime startTime;
    LocalDateTime startEnded;
    LocalDateTime dateCreated;
    Map<Long, Participant> participants = new HashMap<>();
    Map<Long, List<GameScore>> scoreBoard = new HashMap<>();
}
