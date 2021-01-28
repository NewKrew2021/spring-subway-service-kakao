package subway.favorite.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Favorite> favoriteMapper = (rs, rowNum) ->
            new Favorite(
                    rs.getLong("id"),
                    rs.getLong("member_id"),
                    rs.getLong("source_station_id"),
                    rs.getLong("target_station_id"));

    public long insert(Favorite favorite) {
        String sql = "insert into FAVORITE(member_id, source_station_id, target_station_id) values(?, ?, ?)";
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
        String sql = "select F.id, F.member_id, " +
                "SST.id as source_station_id, TST.id as target_station_id " +
                "from FAVORITE F " +
                "left outer join STATION SST on F.source_station_id = SST.id " +
                "left outer join STATION TST on F.target_station_id = TST.id " +
                "WHERE F.member_id = ?";

        return jdbcTemplate.query(sql, favoriteMapper, memberId);
    }

    public boolean favoriteExists(Favorite favorite) {
        String sql = "select COUNT(*) from FAVORITE where member_id = ? and source_station_id = ?" +
                " and target_station_id = ?";

        return jdbcTemplate.queryForObject(sql, int.class, favorite.getMemberId(),
                favorite.getSourceStationId(), favorite.getTargetStationId()) != 0;
    }

    public void deleteById(long memberId, long favoriteId) {
        String sql = "delete from FAVORITE where member_id = ? and id = ?";
        int affectedRows = jdbcTemplate.update(sql, memberId, favoriteId);

        if (affectedRows != 1) {
            throw new NoSuchElementException(
                    String.format("Could not find favorite of member with id: %d and favorite id: %d",
                            memberId, favoriteId));
        }
    }
}
