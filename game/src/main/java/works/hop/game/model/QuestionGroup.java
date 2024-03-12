package works.hop.game.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class QuestionGroup {

    int ordinal;
    String gameRef;
    List<Question> session = new LinkedList<>();
    QuestionType questionType;
    ProgressTiming timing;
    ScoringPlan scoringPlan;
}
