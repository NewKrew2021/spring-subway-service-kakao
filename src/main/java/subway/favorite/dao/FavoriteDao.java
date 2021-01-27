package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FavoriteDao {
    private static final String SELECT_BY_MEMBER_ID_QUERY = "select * from FAVORITE where member_id = ?";
    private static final String DELETE_BY_ID_AND_MEMBER_ID = "delete from FAVORITE where id = ? and member_id = ?";
    private static final RowMapper<Favorite> favoriteRowMapper = (rs, rowNum) -> new Favorite(
            rs.getLong("id"),
            rs.getLong("member_id"),
            rs.getLong("source_station_id"),
            rs.getLong("target_station_id")
    );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Favorite(id, favorite.getMemberId(), favorite.getSourceStationId(), favorite.getTargetStationId());
    }

    public void deleteById(Long id, Long memberId) {
        jdbcTemplate.update(DELETE_BY_ID_AND_MEMBER_ID, id, memberId);
    }

    public List<Favorite> findByMemberId(Long memberId) {
        return jdbcTemplate.query(SELECT_BY_MEMBER_ID_QUERY, favoriteRowMapper, memberId);
    }
}
