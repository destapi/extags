package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameStep {

    int groupNum;
    int questionNum;
    @NotNull
    long gameRef;
    Question question;
    @NotNull
    long questionRef;
    boolean autoProgression;
    Long delayBeforeCountdown;
    Long delayAfterCountdown;
    Long countdownDuration;
    Long countdownIntervals;
    @NotNull
    StepStatus stepStatus;
}
