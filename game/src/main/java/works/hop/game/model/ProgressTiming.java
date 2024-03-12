package works.hop.game.model;

import lombok.Data;

@Data
public class ProgressTiming {

    public enum ProgressMode { AUTO, MANUAL}

    ProgressMode mode = ProgressMode.MANUAL;
    Long delayBeforeCountdown;
    Long delayAfterCountdown;
    Long countdownDuration;
    Long countdownIntervals;
}
