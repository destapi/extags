package works.hop.game.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Player implements Serializable {

    long id;
    @Length(max = 64)
    String firstName;
    @Length(max = 64)
    String lastName;
    @NotNull
    @Length(max = 64)
    String screenName;
    @NotNull
    @Length(max = 64)
    String emailAddress;
    @NotNull
    @Length(max = 64)
    String city;
    @NotNull
    @Length(max = 64)
    String state;
    PlayerStatus playerStatus;
    LocalDateTime dateCreated;
}
