package works.hop.game.model;

import lombok.Data;

@Data
public class Clue {

    int ordinal;
    Question question;
    String questionRef;
    String clueValue;
    String explanation;
}
