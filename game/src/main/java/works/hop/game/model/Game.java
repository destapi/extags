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
    Player organizer;
    @NotNull
    long organizerRef;
    @NotNull
    GameStatus gameStatus;
    LocalDateTime timeStarted;
    LocalDateTime timeEnded;
    LocalDateTime dateCreated;
    Map<Long, Player> participants = new HashMap<>();
    Map<Long, List<GameScore>> scoreBoard = new HashMap<>();
}
