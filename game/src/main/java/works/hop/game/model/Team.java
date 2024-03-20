package works.hop.game.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
public class Team {

    long id;
    @NotNull
    @Length(max = 64)
    String name;
    @Length(max = 64)
    String city;
    @Length(max = 32)
    String state;
    Player captain;
    @Min(1)
    long captainRef;
    LocalDateTime dateCreated;
    List<Player> members = new LinkedList<>();
}
