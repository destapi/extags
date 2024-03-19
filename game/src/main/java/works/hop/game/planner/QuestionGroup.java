package works.hop.game.planner;

import lombok.Data;
import works.hop.game.model.Question;

import java.util.LinkedList;
import java.util.List;

@Data
public class QuestionGroup {

    int ordinal;
    String gameRef;
    List<Question> session = new LinkedList<>();
    ProgressTiming timing;
    ScoringPlan scoringPlan;
}
