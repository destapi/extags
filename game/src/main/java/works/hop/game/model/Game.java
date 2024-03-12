package works.hop.game.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class Game {

    String id;
    String title;
    String description;
    Participant organizer;
    LocalDateTime startTime;
    Map<String, Participant> participants = new HashMap<>();
    Map<String, ScoreTally> scoreBoard = new HashMap<>();
}
