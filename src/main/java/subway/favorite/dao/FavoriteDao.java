package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.favorite.exceptions.FavoriteNotFoundException;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Favorite> favoriteMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("source_station_id"),
                    rs.getLong("target_station_id")
            );

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long insert(Favorite favorite) {
        String sql = "insert into favorite(member_id, source_station_id, target_station_id) values(?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement pstmt = con.prepareStatement(sql, new String[]{"id"});
            pstmt.setLong(1, favorite.getMemberId());
            pstmt.setLong(2, favorite.getSourceStationId());
            pstmt.setLong(3, favorite.getTargetStationId());
            return pstmt;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Favorite> findFavoritesByMemberId(long memberId) {
        String sql = "select * from favorite WHERE member_id = ?";
        return jdbcTemplate.query(sql, favoriteMapper, memberId);
    }

    public boolean favoriteExists(Favorite favorite) {
        String sql = "select count(*) from favorite where member_id = ? and source_station_id = ? and target_station_id = ?";

        return jdbcTemplate.queryForObject(sql, int.class, favorite.getMemberId(),
                favorite.getSourceStationId(), favorite.getTargetStationId()) != 0;
    }

    public void deleteById(long memberId, long favoriteId) {
        String sql = "delete from favorite where id = ? and member_id = ?";
        int affectedRows = jdbcTemplate.update(sql, favoriteId, memberId);

        if (affectedRows != 1) {
            throw new FavoriteNotFoundException("삭제 요청한 아이디에 맞는 즐겨찾기가 없습니다. id: " + favoriteId);
        }
    }
}
