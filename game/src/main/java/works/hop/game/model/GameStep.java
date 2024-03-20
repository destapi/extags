package works.hop.game.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameStep {

    int groupNum;
    int questionNum;
    @Min(1)
    long gameRef;
    Question question;
    @Min(1)
    long questionRef;
    boolean autoProgression;
    Long delayBeforeCountdown;
    Long delayAfterCountdown;
    Long countdownDuration;
    Long countdownIntervals;
    @NotNull
    StepStatus stepStatus;
}
