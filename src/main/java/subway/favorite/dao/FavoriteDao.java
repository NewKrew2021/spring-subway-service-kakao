package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.Member;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Favorite> rowMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("source_station_id"),
                    rs.getLong("target_station_id")
            );

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite save(Favorite favorite) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", favorite.getMemberId());
        parameters.put("source_station_id", favorite.getSourceId());
        parameters.put("target_station_id", favorite.getTargetId());

        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Favorite(id, favorite.getMemberId(), favorite.getSourceId(), favorite.getTargetId());
    }

    public List<Favorite> findAll(Long memberId) {
        String sql = FavoriteDaoQuery.FIND_ALL_QUERY;
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public void deleteById(Long favoriteId) {
        String sql = FavoriteDaoQuery.DELETE_BY_ID_QUERY;
        jdbcTemplate.update(sql, favoriteId);
    }
}
