package subway.station.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exception.InvalidMemberException;
import subway.exception.StationNotFoundException;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StationDao {
    private static final String SELECT_FROM_STATION = "select * from STATION";
    private static final String DELETE_FROM_STATION_WHERE_ID = "delete from STATION where id = ?";
    private static final String SELECT_FROM_STATION_WHERE_ID = "select * from STATION where id = ?";

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
        return jdbcTemplate.query(SELECT_FROM_STATION, rowMapper);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_FROM_STATION_WHERE_ID, id);
    }

    public Station findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_STATION_WHERE_ID, rowMapper, id);
        } catch (EmptyResultDataAccessException e){
            throw new StationNotFoundException();
        }
    }
}
