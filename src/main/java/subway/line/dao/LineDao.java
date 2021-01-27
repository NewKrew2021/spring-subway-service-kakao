package subway.line.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.common.domain.Distance;
import subway.common.domain.Fare;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class LineDao {
    private static final String SELECT_ALL_QUERY =
            "select L.id as line_id, L.name as line_name, L.color as line_color, L.extra_fare as extra_fare, " +
                    "S.id as section_id, S.distance as section_distance, " +
                    "UST.id as up_station_id, UST.name as up_station_name, " +
                    "DST.id as down_station_id, DST.name as down_station_name " +
                    "from LINE L \n" +
                    "left outer join SECTION S on L.id = S.line_id " +
                    "left outer join STATION UST on S.up_station_id = UST.id " +
                    "left outer join STATION DST on S.down_station_id = DST.id ";
    private static final String SELECT_BY_ID_QUERY =
            "select L.id as line_id, L.name as line_name, L.color as line_color, L.extra_fare as extra_fare, " +
                    "S.id as section_id, S.distance as section_distance, " +
                    "UST.id as up_station_id, UST.name as up_station_name, " +
                    "DST.id as down_station_id, DST.name as down_station_name " +
                    "from LINE L \n" +
                    "left outer join SECTION S on L.id = S.line_id " +
                    "left outer join STATION UST on S.up_station_id = UST.id " +
                    "left outer join STATION DST on S.down_station_id = DST.id " +
                    "WHERE L.id = ?";
    private static final String UPDATE_QUERY = "update LINE set name = ?, color = ?, extra_fare = ? where id = ?";
    private static final String DELETE_BY_ID_QUERY = "delete from Line where id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public LineDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("line")
                .usingGeneratedKeyColumns("id");
    }

    public Line insert(Line line) {
        Map<String, Object> params = getParamsMap(line);
        Long lineId = insertAction.executeAndReturnKey(params).longValue();
        return Line.of(lineId, line.getName(), line.getColor(), line.getExtraFare());
    }

    private Map<String, Object> getParamsMap(Line line) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", line.getId());
        params.put("name", line.getName());
        params.put("color", line.getColor());
        params.put("extra_fare", line.getExtraFare().getFare());
        return params;
    }

    public Line findById(Long id) {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_BY_ID_QUERY, id);
        return mapLine(result);
    }

    public void update(Line newLine) {
        jdbcTemplate.update(UPDATE_QUERY, newLine.getName(), newLine.getColor(), newLine.getExtraFare().getFare(), newLine.getId());
    }

    public List<Line> findAll() {
        List<Map<String, Object>> result = jdbcTemplate.queryForList(SELECT_ALL_QUERY);
        Map<Long, List<Map<String, Object>>> resultByLine = result.stream()
                .collect(Collectors.groupingBy(it -> (Long) it.get("line_id")));
        return resultByLine.values()
                .stream()
                .map(this::mapLine)
                .collect(Collectors.toList());
    }

    private Line mapLine(List<Map<String, Object>> result) {
        if (result.size() == 0) {
            throw new RuntimeException();
        }

        List<Section> sections = extractSections(result);

        return Line.of(
                (Long) result.get(0).get("LINE_ID"),
                (String) result.get(0).get("LINE_NAME"),
                (String) result.get(0).get("LINE_COLOR"),
                Fare.from((Integer) result.get(0).get("EXTRA_FARE")),
                new Sections(sections));
    }

    private List<Section> extractSections(List<Map<String, Object>> result) {
        if (result.isEmpty() || result.get(0).get("SECTION_ID") == null) {
            return new ArrayList<>();
        }
        return result.stream()
                .collect(Collectors.groupingBy(it -> it.get("SECTION_ID")))
                .entrySet()
                .stream()
                .map(it -> Section.of(
                        (Long) it.getKey(),
                        Station.of((Long) it.getValue().get(0).get("UP_STATION_ID"), (String) it.getValue().get(0).get("UP_STATION_Name")),
                        Station.of((Long) it.getValue().get(0).get("DOWN_STATION_ID"), (String) it.getValue().get(0).get("DOWN_STATION_Name")),
                        Distance.from((int) it.getValue().get(0).get("SECTION_DISTANCE"))))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }
}
