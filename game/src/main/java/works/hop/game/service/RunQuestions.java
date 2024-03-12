package works.hop.game.service;

import works.hop.game.model.Question;
import works.hop.game.model.QuestionGroup;

import java.util.Timer;
import java.util.TimerTask;

public class RunQuestions extends TimerTask {

    final Timer timer = new Timer();
    QuestionGroup group;
    int currentQue = 0;

    public RunQuestions(QuestionGroup group) {
        this.group = group;
    }

    public void start() {
        long duration = this.group.getTiming().getDelayBeforeCountdown() + this.group.getTiming().getCountdownDuration() + group.getTiming().getDelayBeforeCountdown();
        timer.scheduleAtFixedRate(this, 0L, duration);
    }

    @Override
    public void run() {
        this.group.getScoringPlan().current(currentQue + 1, group.getSession().size());
        Question question = group.getSession().get(currentQue);
        QuestionTask task = new QuestionTask(question,
                this.group.getTiming().getDelayBeforeCountdown(),
                this.group.getTiming().getCountdownDuration(),
                this.group.getTiming().getCountdownIntervals());
        task.start(this.group.getScoringPlan());
        this.group.getScoringPlan().question(question, this.group.getTiming().getCountdownDuration());
        currentQue++;

        if (currentQue >= group.getSession().size()) {
            timer.cancel();
        }
    }
}
