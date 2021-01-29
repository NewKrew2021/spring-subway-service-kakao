package subway.station.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exception.InvalidMemberException;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class StationDao {
    public static final String FIND_AlL_STATION = "select * from STATION";
    public static final String DELETE_STATION = "delete from STATION where id = ?";
    public static final String FIND_STATION_BY_STATION_ID = "select * from STATION where id = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    private RowMapper<Station> rowMapper = (rs, rowNum) ->
            new Station(
                    rs.getLong("id"),
                    rs.getString("name")
            );


    public StationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("station")
                .usingGeneratedKeyColumns("id");
    }

    public Station insert(Station station) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(station);
        Long id = insertAction.executeAndReturnKey(params).longValue();
        return new Station(id, station.getName());
    }

    public List<Station> findAll() {
        return jdbcTemplate.query(FIND_AlL_STATION, rowMapper);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_STATION, id);
    }

    public Optional<Station> findById(Long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_STATION_BY_STATION_ID, rowMapper, id));
    }
}
