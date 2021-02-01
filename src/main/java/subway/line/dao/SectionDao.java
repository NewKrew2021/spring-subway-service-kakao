package subway.line.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.line.domain.Line;
import subway.line.domain.Section;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class SectionDao {
    private static final String DELETE_BY_ID_QUERY = "delete from SECTION where line_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public SectionDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("section")
                .usingGeneratedKeyColumns("id");
    }

    public Section insert(Line line, Section section) {
        Map<String, Object> params = getParamsMap(line, section);
        Long sectionId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Section.of(sectionId, section);
    }

    private Map<String, Object> getParamsMap(Line line, Section section) {
        Map<String, Object> params = new HashMap<>();
        params.put("line_id", line.getId());
        params.put("up_station_id", section.getUpStation().getId());
        params.put("down_station_id", section.getDownStation().getId());
        params.put("distance", section.getDistance().getDistance());
        return params;
    }

    public void deleteByLineId(Long lineId) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, lineId);
    }

    public void insertSections(Line line) {
        List<Section> sections = line.getSections().getSections();
        List<Map<String, Object>> batchValues = sections.stream()
                .map(section -> getParamsMap(line, section))
                .collect(Collectors.toList());
        simpleJdbcInsert.executeBatch(SqlParameterSourceUtils.createBatch(batchValues));
    }
}
