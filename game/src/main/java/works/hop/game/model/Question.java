package works.hop.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Question {

    String id;
    String question;
    Answer answer;
    int maxPoints;
    List<Choice> multipleChoices = new LinkedList<>();
    List<Clue> eliminationClues = new LinkedList<>();
}
