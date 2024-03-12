package works.hop.game.service;

import org.junit.jupiter.api.Test;
import works.hop.game.model.ProgressTiming;
import works.hop.game.model.Question;
import works.hop.game.model.QuestionGroup;
import works.hop.game.model.ScoringPlan;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class RunQuestionsTest {

    public static void main(String[] args) throws InterruptedException {
        //plan questions
        Question que1 = new Question();
        que1.setId("1");
        que1.setQuestion("1 + 1");
        Question que2 = new Question();
        que1.setId("2");
        que2.setQuestion("2 + 2");
        Question que3 = new Question();
        que1.setId("3");
        que3.setQuestion("3 + 3");

        // plan timing
        ProgressTiming timing = new ProgressTiming();
        timing.setMode(ProgressTiming.ProgressMode.AUTO);
        timing.setDelayBeforeCountdown(3000L);
        timing.setCountdownDuration(5000L);
        timing.setCountdownIntervals(500L);
        timing.setDelayAfterCountdown(2000L);

        // scoring plan
        ScoringPlan scoring = new ScoringPlan();

        // plan session
        QuestionGroup group = new QuestionGroup();
        group.getSession().add(que1);
        group.getSession().add(que2);
        group.getSession().add(que3);
        group.setTiming(timing);
        group.setScoringPlan(scoring);

        //execute runner
        RunQuestions runner = new RunQuestions(group);
        runner.start();
    }

    public static void mainB(String[] args) {
        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n" +
                        "Thread's name: " + Thread.currentThread().getName());
                timer.cancel();
            }
        };

        long delay = 1000L;
        timer.schedule(task, delay);
    }

    public static void mainC(String[] args) {

        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {

            long max = 5000L;
            public void run() {
                System.out.println("Task performed on: " + new Date() + "n" +
                        "Thread's name: " + Thread.currentThread().getName());
                if(max < 0 ){
                    timer.cancel();
                }
                max -= 1000;
            }
        };

        timer.schedule(task, 2000L, 5000L);
    }
}