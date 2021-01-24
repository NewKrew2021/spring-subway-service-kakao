package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FavoriteDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("FAVORITE")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Favorite> favoriteRowMapper = (rs, rowNum) -> {
        Favorite favorite = new Favorite(rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getLong("source_station_id"),
                rs.getLong("target_station_id"));
        return favorite;
    };

    public void insert(Long memberId, Long sourceId, Long targetId){
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", memberId);
        params.put("source_station_id", sourceId);
        params.put("target_station_id", targetId);

        insertAction.execute(params);
    }

    public Favorite findByUser(Long memberId){
        String sql = "select * from favorite where member_id = ?";
        return jdbcTemplate.queryForObject(sql, favoriteRowMapper, memberId);
    }

    public void deleteFavorite(Long id) {
        String sql = "delete from favorite where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
