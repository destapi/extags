package works.hop.game.service;

import works.hop.game.model.Question;
import works.hop.game.running.ScoringPlan;

import java.util.Timer;
import java.util.TimerTask;

public class QuestionTask extends TimerTask {

    final Timer timer = new Timer();
    Question question;
    Long delayBefore;
    Long duration;
    Long period;
    ScoringPlan scoringPlan;

    public QuestionTask(Question question, Long delayBefore, Long duration, Long period) {
        this.question = question;
        this.delayBefore = delayBefore;
        this.period = period;
        this.duration = duration;
    }

    public void start(ScoringPlan scoringPlan) {
        this.scoringPlan = scoringPlan;
        timer.scheduleAtFixedRate(this, this.delayBefore, this.period);
    }

    public void run() {
        scoringPlan.countdown(this.duration);
        if (this.duration <= 0) {
            timer.cancel();
        }
        this.duration -= this.period;
    }
}
