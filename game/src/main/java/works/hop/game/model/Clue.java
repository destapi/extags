package works.hop.game.model;

import lombok.Data;

@Data
public class Clue {

    int ordinal;
    Question question;
    long questionRef;
    String clueValue;
    String explanation;
}
