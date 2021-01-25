package subway.favorite.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import subway.favorite.domain.Favorite;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class FavoriteDao {

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Favorite> rowMapper = (rs, rowNum) -> new Favorite(
            rs.getLong("id"),
            rs.getLong("member_id"),
            rs.getLong("source_station_id"),
            rs.getLong("target_station_id")
    );

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Favorite save(Favorite favorite) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertFavorite(favorite), keyHolder);
        return new Favorite((Long) keyHolder.getKey(), favorite.getMemberId(), favorite.getSource(), favorite.getTarget());
    }

    private PreparedStatementCreator insertFavorite(Favorite favorite) {
        return con -> {
            PreparedStatement psmt = con.prepareStatement(
                    "INSERT INTO favorite (member_id, source_station_id, target_station_id) VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            psmt.setLong(1, favorite.getMemberId());
            psmt.setLong(2, favorite.getSource());
            psmt.setLong(3, favorite.getTarget());
            return psmt;
        };
    }

    public List<Favorite> findAll(Long memberId) {
        String SQL = "SELECT id,member_id,source_station_id,target_station_id FROM favorite WHERE member_id = ?";
        return jdbcTemplate.query(SQL, rowMapper, memberId);
    }

    public int deleteById(Long id) {
        String SQL = "DELETE FROM favorite WHERE id = ?";
        return jdbcTemplate.update(SQL,id);
    }
}
