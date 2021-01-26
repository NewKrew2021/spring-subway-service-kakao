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
        params.put("id", favorite.getId());
        params.put("member_id", favorite.getMemberId());
        params.put("source_station_id", favorite.getSource().getId());
        params.put("target_station_id", favorite.getTarget().getId());

        Long favoriteId = insertAction.executeAndReturnKey(params).longValue();
        return new Favorite(favoriteId, favorite.getMemberId(), favorite.getSource(), favorite.getTarget());
    }

    public List<Favorite> findByMemberId(Long memberId) {
        String sql = "select F.id as id, F.member_id as member_id, S.id as source_station_id, S.name as source_station_name, " +
                "T.id as target_station_id, T.name as target_station_name " +
                "from FAVORITE F " +
                "left join STATION S on F.source_station_id = S.id " +
                "left join STATION T on F.target_station_id = T.id " +
                "where F.member_id = ?";

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, memberId);
        return result.stream()
                .map(this::mapFavorite)
                .collect(Collectors.toList());
    }

    public List<Long> findMemberIdById(Long id) {
        String sql = "select member_id from FAVORITE where id = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql, id);
        return result.stream()
                .map(memberId -> (Long) memberId.get("member_id"))
                .collect(Collectors.toList());
    }

    private Favorite mapFavorite(Map<String, Object> result) {
        return new Favorite(
                (Long) result.get("id"),
                (Long) result.get("member_id"),
                new Station((Long) result.get("source_station_id"), (String) result.get("source_station_name")),
                new Station((Long) result.get("target_station_id"), (String) result.get("target_station_name")));
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("delete from FAVORITE where id = ?", id);
    }
}
