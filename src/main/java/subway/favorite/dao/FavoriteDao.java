package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.member.domain.Member;
import subway.station.domain.Station;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FavoriteDao {

    private final JdbcTemplate jdbcTemplate;

    private RowMapper<Favorite> rowMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("source_station_id"),
                    rs.getLong("target_station_id")
            );

    public FavoriteDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Favorite favorite) {
        String sql = "insert into favorite (member_id, source_station_id, target_station_id) values ( ?, ?, ? )";
        jdbcTemplate.update(sql, favorite.getMemberId(), favorite.getSourceId(), favorite.getTargetId());
    }

    public List<Favorite> findAll(Long memberId) {
        String sql = "select * from favorite where member_id = ?";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }
}
