package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GameInProgress {

    int groupNum;
    int questionNum;
    Game game;
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
    ProgressStatus progressStatus;
    LocalDateTime timeStarted;
    LocalDateTime timeEnded;
}
