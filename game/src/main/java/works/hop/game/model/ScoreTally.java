package works.hop.game.model;

import java.util.HashMap;
import java.util.Map;

public class ScoreTally {

    String id;
    String gameRef;
    String refParticipant;
    Map<String, Score> tally = new HashMap<>();
}
