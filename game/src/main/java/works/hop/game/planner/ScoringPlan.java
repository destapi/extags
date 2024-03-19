package works.hop.game.planner;

import works.hop.game.model.Question;

public class ScoringPlan {

    public void countdown(Long remaining) {
        System.out.println(remaining);
    }

    public void question(Question question, Long duration) {
        System.out.printf("Que %s: duration: %d\n", question.getQuestion(), duration);
    }

    public void current(int currentQue, int size) {
        System.out.printf("Que %d of %d\n", currentQue, size);
    }
}
