package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.Member;

import javax.sql.DataSource;

@Repository
public class FavoriteDao {

    private final JdbcTemplate jdbcTemplate;

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Favorite favorite) {
        String sql = "insert into favorite (member_id, source_station_id, target_station_id) values ( ?, ?, ? )";
        jdbcTemplate.update(sql, favorite.getMemberId(), favorite.getSourceId(), favorite.getTargetId());
    }
}
