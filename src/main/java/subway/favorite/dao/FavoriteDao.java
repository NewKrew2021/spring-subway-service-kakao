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

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertAction;

    private RowMapper<Favorite> rowMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("user_id"),
                    rs.getLong("source_id"),
                    rs.getLong("target_id")
            );


    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("favorite")
                .usingGeneratedKeyColumns("id");
    }

    public Favorite insert(Favorite favorite) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(favorite);
        Long id = insertAction.executeAndReturnKey(params).longValue();
        return new Favorite(id, favorite.getUserId(), favorite.getSourceId(), favorite.getTargetId());
    }

    public void deleteByIdAndUserId(Long id, Long userId) {
        String sql = "delete from FAVORITE where id = ? and user_id = ?";
        jdbcTemplate.update(sql, id, userId);
    }

    public List<Favorite> findByUserId(Long id) {
        String sql = "select * from FAVORITE where user_id = ?";
        return jdbcTemplate.query(sql, rowMapper, id);
    }
}
