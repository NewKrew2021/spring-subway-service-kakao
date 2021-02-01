package subway.favorite.dao;

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
    private final RowMapper<Favorite> favoriteRowMapper = (resultSet, rowNum) -> new Favorite(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            new Station(resultSet.getLong("source_station_id"), resultSet.getString("source_station_name")),
            new Station(resultSet.getLong("target_station_id"), resultSet.getString("target_station_name"))
        );

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FAVORITE")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite saveFavorite(Favorite favorite) {
        Map<String, Object> params = new HashMap();
        params.put("member_id", favorite.getMemberId());
        params.put("source_station_id", favorite.getSourceStation().getId());
        params.put("target_station_id", favorite.getTargetStation().getId());

        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Favorite(id, favorite.getMemberId(), favorite.getSourceStation(), favorite.getTargetStation());
    }

    public List<Favorite> findAllByMemberId(Long memberId) {
        String sql = "select F.id as id, F.member_id as member_id, F.source_station_id as source_station_id, " +
                "F.target_station_id as target_station_id, " +
                "UST.name as source_station_name, DST.name as target_station_name " +
                "from FAVORITE F \n" +
                "left outer join STATION UST on F.source_station_id = UST.id " +
                "left outer join STATION DST on F.target_station_id = DST.id " +
                "WHERE F.member_id = ?";
        return jdbcTemplate.query(sql, favoriteRowMapper, memberId);
    }

    public void deleteFavoriteById(Long memberId, Long favoriteId) {
        String deleteSqlById = "delete from FAVORITE where id = ? and member_id = ?";
        jdbcTemplate.update(deleteSqlById, favoriteId, memberId);
    }
}
