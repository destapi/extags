package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class Question {

    long id;
    @NotNull
    @Length(max = 256)
    String question;
    @NotNull
    QuestionType questionType;
    @NotNull
    @Length(max = 256)
    String answer;
    @Length(max = 1024)
    String answerReason;
    int maxPoints;
    Participant createdBy;
    @NotNull
    long createdByRef;
    LocalDateTime dateCreated;
    List<Choice> multipleChoices = new LinkedList<>();
    List<Clue> eliminationClues = new LinkedList<>();
}
