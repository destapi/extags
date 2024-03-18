package works.hop.game.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import works.hop.game.model.Team;
import works.hop.game.repository.mapper.TeamRowMapper;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

@Repository
public class TeamRepo {

    final JdbcTemplate jdbcTemplate;

    public TeamRepo(@Autowired DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Team getById(Long id) {
        String SELECT_BY_ID = "select * from Team where id = ?";
        return jdbcTemplate.queryForObject(SELECT_BY_ID, new TeamRowMapper(), id);
    }

    public Team createTeam(Team team) {
        String INSERT_ENTITY_SQL = "insert into Team (name, city, state, captainRef) values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(INSERT_ENTITY_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Objects.requireNonNull(team.getName()));
            ps.setString(2, Objects.requireNonNullElse(team.getCity(), ""));
            ps.setString(3, Objects.requireNonNullElse(team.getState(), ""));
            ps.setLong(4, team.getCaptainRef());
            return ps;
        }, keyHolder);

        team.setId((long) keyHolder.getKeyList().get(0).get("id"));
        return team;
    }

    public Team updateTeam(Team team) {
        String UPDATE_ENTITY_SQL = "update Team set name = ?, city=?, state=? where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setString(1, Objects.requireNonNull(team.getName()));
            ps.setString(2, Objects.requireNonNullElse(team.getCity(), ""));
            ps.setString(3, Objects.requireNonNullElse(team.getState(), ""));
            ps.setLong(4, team.getId());
            return ps;
        });

        return team;
    }

    public void removeTeam(long teamId) {
        String UPDATE_ENTITY_SQL = "delete from Team where id = ?";
        this.jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(UPDATE_ENTITY_SQL);
            ps.setLong(1, teamId);
            return ps;
        });
    }
}
