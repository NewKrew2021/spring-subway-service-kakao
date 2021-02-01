package subway.station.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StationDao {
    private static final String SELECT_ALL_QUERY = "select * from STATION";
    private static final String SELECT_BY_ID_QUERY = "select * from STATION where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from STATION where id = ?";
    private static final RowMapper<Station> rowMapper = (rs, rowNum) ->
            Station.of(
                    rs.getLong("id"),
                    rs.getString("name")
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public StationDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("station")
                .usingGeneratedKeyColumns("id");
    }

    public Station insert(Station station) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(station);
        Long id = insertAction.executeAndReturnKey(params).longValue();
        return Station.of(id, station.getName());
    }

    public List<Station> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    public Station findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, rowMapper, id);
    }
}
