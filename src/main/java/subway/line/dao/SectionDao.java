package subway.line.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SectionDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SectionDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("SECTION")
                .usingGeneratedKeyColumns("id");
    }

    public Section insert(Line line, Section section) {
        Map<String, Object> params = new HashMap();
        params.put("line_id", line.getId());
        params.put("up_station_id", section.getUpStation().getId());
        params.put("down_station_id", section.getDownStation().getId());
        params.put("distance", section.getDistance());
        Long sectionId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Section(sectionId, section.getUpStation(), section.getDownStation(), section.getDistance());
    }

    public void deleteByLineId(Long lineId) {
        jdbcTemplate.update("delete from SECTION where line_id = ?", lineId);
    }

    public void insertSections(Line line) {
        List<Section> sections = line.getSections().getSections();
        List<Map<String, Object>> batchValues = sections.stream()
                .map(section -> {
                    Map<String, Object> params = new HashMap<>();
                    params.put("line_id", line.getId());
                    params.put("up_station_id", section.getUpStation().getId());
                    params.put("down_station_id", section.getDownStation().getId());
                    params.put("distance", section.getDistance());
                    return params;
                })
                .collect(Collectors.toList());

        simpleJdbcInsert.executeBatch(batchValues.toArray(new Map[sections.size()]));
    }

    public Long findLineIdByUpStationIdAndDownStationId(Long upStationId, Long downStationId) {
        String sql = "select line_id from SECTION " +
                "where (up_station_id = ? and down_station_id = ?) " +
                "or (down_station_id = ? and up_station_id = ?)";
        return jdbcTemplate.queryForObject(sql, Long.class, upStationId, downStationId, upStationId, downStationId);
    }

    public List<Section> findAll() {
        String sql = "select id, line_id, up_station_id, down_station_id, distance " +
                "from SECTION";
        return jdbcTemplate.query(sql, rowMapper);
    }

    private final RowMapper<Section> rowMapper = (resultSet, rowNum) -> new Section(
            resultSet.getLong("id"),
            new Station(resultSet.getLong("up_station_id")),
            new Station(resultSet.getLong("down_station_id")),
            resultSet.getInt("distance")
    );
}
