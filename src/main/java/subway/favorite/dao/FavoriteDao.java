package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertAction;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", favorite.getMemberId());
        params.put("source_station_id", favorite.getSourceStation().getId());
        params.put("target_station_id", favorite.getTargetStation().getId());

        Long lineId = insertAction.executeAndReturnKey(params).longValue();
        return new Favorite(lineId, favorite.getMemberId(), favorite.getSourceStation(), favorite.getTargetStation());
    }

    public List<Favorite> findById(Long memberId) {
        String sql = "select F.id as favorite_id, member_id, " +
                "SRC.id as source_station_id, SRC.name as source_station_name, " +
                "TRG.id as target_station_id, TRG.name as target_station_name " +
                "from FAVORITE F " +
                "left outer join STATION SRC on F.source_station_id = SRC.id " +
                "left outer join STATION TRG on F.target_station_id = TRG.id " +
                "where member_id = ?";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, memberId);
        return result.stream()
                .map(this::mapFavorite)
                .collect(Collectors.toList());
    }

    private Favorite mapFavorite(Map<String, Object> result) {
        return new Favorite(
                (Long) result.get("FAVORITE_ID"),
                (Long) result.get("MEMBER_ID"),
                new Station((Long) result.get("SOURCE_STATION_ID"), (String) result.get("SOURCE_STATION_NAME")),
                new Station((Long) result.get("TARGET_STATION_ID"), (String) result.get("TARGET_STATION_NAME"))
        );
    }

    public void deleteFavoriteById(Long memberId, Long favoriteId) {
        jdbcTemplate.update("delete from FAVORITE where id = ? and member_id = ?", favoriteId, memberId);
    }
}
