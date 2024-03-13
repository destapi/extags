package works.hop.game.model;

import lombok.Data;

@Data
public class ProgressTiming {

    ProgressMode mode = ProgressMode.MANUAL;
    Long delayBeforeCountdown;
    Long delayAfterCountdown;
    Long countdownDuration;
    Long countdownIntervals;

    public enum ProgressMode {AUTO, MANUAL}
}
