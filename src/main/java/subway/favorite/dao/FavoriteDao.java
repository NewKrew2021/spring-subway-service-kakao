package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

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

}
