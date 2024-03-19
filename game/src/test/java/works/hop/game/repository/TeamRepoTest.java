package works.hop.game.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.config.TestRepositoryConfig;
import works.hop.game.model.Player;
import works.hop.game.model.Team;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestRepositoryConfig.class)
class TeamRepoTest {

    @Autowired
    DataSource embeddedDataSource;
    TeamRepo teamRepo;
    PlayerRepo playerRepo;

    @BeforeEach
    void setUp() {
        teamRepo = new TeamRepo(embeddedDataSource);
        playerRepo = new PlayerRepo(embeddedDataSource);
    }

    @Test
    void createAndUpdateTeam() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Team newTeam = new Team();
        newTeam.setName("Molly and I");
        newTeam.setCaptainRef(crazyeyes.getId());
        newTeam.setCity("Monona");
        newTeam = teamRepo.createTeam(newTeam);
        assertThat(newTeam.getId()).isNotZero();
        assertThat(newTeam.getState()).isNull();
        assertThat(newTeam.getMembers()).isEmpty();

        newTeam.setState("WI");
        newTeam = teamRepo.registerTeam(newTeam);
        Team updated = teamRepo.getById(newTeam.getId());
        assertThat(updated.getState()).isEqualTo(newTeam.getState());
    }

    @Test
    void addAndRemoveTeamMember() {
        Player crazyeyes = playerRepo.getByEmail("jimmy.crazyeyes@email.com");
        Team newTeam = new Team();
        newTeam.setName("Jimmy and I");
        newTeam.setCaptainRef(crazyeyes.getId());
        newTeam.setCity("Milwaukee");
        newTeam = teamRepo.createTeam(newTeam);
        assertThat(newTeam.getId()).isNotZero();
        assertThat(newTeam.getMembers()).isEmpty();

        Player bigfoot = playerRepo.getByEmail("casssie.bigfoot@email.com");
        teamRepo.joinTeam(newTeam.getId(), bigfoot.getId());
        Team updated1 = teamRepo.getById(newTeam.getId());
        assertThat(updated1.getMembers()).hasSize(1);

        Player coolcat = playerRepo.getByEmail("debbie.coolcat@email.com");
        teamRepo.joinTeam(newTeam.getId(), coolcat.getId());
        Team updated2 = teamRepo.getById(newTeam.getId());
        assertThat(updated2.getMembers()).hasSize(2);

        //remove member
        teamRepo.leaveTeam(newTeam.getId(), bigfoot.getId());
        Team updated3 = teamRepo.getById(newTeam.getId());
        assertThat(updated3.getMembers()).hasSize(1);
    }
}