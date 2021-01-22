package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import javax.sql.DataSource;

import java.util.*;

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

    public void deleteByIdAndMemberId(Long id, Long memberId) {
        String sql = "delete from favorite where id = ? and member_id = ?";
        jdbcTemplate.update(sql, id, memberId);
    }

    public List<Favorite> findAll(Long memberId) {
        String sql = "select * from favorite where member_Id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }
}
