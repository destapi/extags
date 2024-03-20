package works.hop.web.service.impl;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import works.hop.game.model.Team;
import works.hop.game.repository.TeamRepo;
import works.hop.web.config.AppConfig;
import works.hop.web.service.ITeamService;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
class TeamServiceTest {

    ITeamService teamService;
    @Mock
    TeamRepo teamRepo;
    @Autowired
    Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teamService = new TeamService(teamRepo, validator);
    }
    
    @Test
    void validator_when_entity_is_missing_required_fields() {
        Team team = new Team();
        Map<String, String> violations = teamService.validate(team);
        assertThat(violations).isNotEmpty();
    }
    
    @Test
    void validator_when_entity_is_NOT_missing_required_fields() {
        Team team = new Team();
        team.setCaptainRef(1L);
        team.setName("Dark knights");
        team.setCity("Canton");
        team.setState("OH");
        Map<String, String> violations = teamService.validate(team);
        assertThat(violations).isEmpty();
    }
}