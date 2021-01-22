package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import java.util.List;

@Repository
public class FavoriteDao {

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Favorite> rowMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("source_station_id"),
                    rs.getLong("target_station_id")
            );

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Favorite(id, favorite.getMemberId(), favorite.getSourceStationId(), favorite.getTargetStationId());
    }

    public List<Favorite> findAll(Long memberId) {
        String sql = "select id, member_id, source_station_id, target_station_id from FAVORITE where member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public void deleteById(Long id) {
        String sql = "delete from FAVORITE where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
