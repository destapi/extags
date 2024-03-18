package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class Choice {

    int ordinal;
    Question question;
    @NotNull
    long questionRef;
    @Length(max = 64)
    String choiceValue;
    @Length(max = 256)
    String explanation;
}
