package subway.favorite.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Favorite> rowMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    new Station(rs.getLong("source_station_id"), rs.getString("source_station_name")),
                    new Station(rs.getLong("target_station_id"), rs.getString("target_station_name"))
            );

    @Autowired
    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Long memberId, Long sourceStationId, Long targetStationId) {

        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("source_station_id", sourceStationId);
        params.put("target_station_id", targetStationId);

        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Favorite(id, memberId, new Station(sourceStationId), new Station(targetStationId));
    }

    public List<Favorite> findByMemberId(Long memberId) {
        String sql = "select F.id as favorite_id, F.member_id as favorite_member_id, F.source_station_id as favorite_source_station_id, F.target_station_id as favorite_target_station_id, " +
                "SST.id as source_station_id, SST.name as source_station_name, " +
                "TST.id as target_station_id, TST.name as target_station_name " +
                "from FAVORITE F \n" +
                "join STATION SST on F.source_station_id = SST.id " +
                "join STATION TST on F.target_station_id = TST.id " +
                "WHERE F.member_id = ?";

        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public void deleteById(Long id) {
        String sql = "delete from FAVORITE where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
