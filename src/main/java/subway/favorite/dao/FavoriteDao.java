package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;
import subway.favorite.dto.FavoriteResponse;
import subway.station.dto.StationResponse;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class FavoriteDao {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<FavoriteResponse> favoriteMapper = (rs, rowNum) ->
            new FavoriteResponse(
                    rs.getLong("id"),
                    new StationResponse(
                            rs.getLong("source_station_id"),
                            rs.getString("source_station_name")),
                    new StationResponse(
                            rs.getLong("target_station_id"),
                            rs.getString("target_station_name"))
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

    public List<FavoriteResponse> findFavoriteResponsesByMemberId(long memberId) {
        String sql = "select F.id, F.member_id, " +
                "SST.id as source_station_id, SST.name as source_station_name, " +
                "TST.id as target_station_id, TST.name as target_station_name, " +
                "from favorite F \n" +
                "left outer join station SST on F.source_station_id = SST.id " +
                "left outer join station TST on F.target_station_id = TST.id " +
                "WHERE F.member_id = ?";

        return jdbcTemplate.query(sql, favoriteMapper, memberId);
    }

    public Favorite findById(long favoriteId){
        String sql = "select * from favorite where id = ?";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> new Favorite(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("source_station_id"),
                        rs.getLong("target_station_id")
                ),
                favoriteId);
    }

    public boolean favoriteExists(Favorite favorite) {
        String sql = "select EXISTS" +
                " (select * from favorite where member_id = ? and source_station_id = ? and target_station_id = ?)" +
                " as success";

        return jdbcTemplate.queryForObject(sql, int.class, favorite.getMemberId(),
                favorite.getSourceStationId(), favorite.getTargetStationId()) != 0;
    }

    public void deleteById(long favoriteId) {
        String sql = "delete from favorite where id = ?";
        int affectedRows = jdbcTemplate.update(sql, favoriteId);

        if (affectedRows != 1) {
            throw new NoSuchElementException("Could not find favorite with id: %d" + favoriteId);
        }
    }
}
