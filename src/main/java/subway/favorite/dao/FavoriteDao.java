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
import java.util.Optional;

@Repository
public class FavoriteDao {

    public static final String SELECT_FAVORITE = "select id, member_id, source_station_id, target_station_id from FAVORITE where id = ?";
    public static final String INSERT_FAVORITE = "INSERT INTO favorite (member_id, source_station_id, target_station_id) VALUES (?,?,?)";
    public static final String DELETE_FAVORITE = "DELETE FROM favorite WHERE id = ?";
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Favorite> rowMapper = (rs, rowNum) -> new Favorite(
            rs.getLong("id"),
            rs.getLong("member_id"),
            rs.getLong("source_station_id"),
            rs.getLong("target_station_id")
    );

    public FavoriteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Favorite> save(Favorite favorite) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(insertFavorite(favorite), keyHolder);
        return findById((Long)keyHolder.getKey());
    }

    public Optional<Favorite> findById(Long id) {
        List<Favorite> result = jdbcTemplate.query(SELECT_FAVORITE, rowMapper, id);
        return result.stream().findAny();
    }

    private PreparedStatementCreator insertFavorite(Favorite favorite) {
        return con -> {
            PreparedStatement psmt = con.prepareStatement(
                    INSERT_FAVORITE,
                    Statement.RETURN_GENERATED_KEYS
            );
            psmt.setLong(1, favorite.getMemberId());
            psmt.setLong(2, favorite.getSource());
            psmt.setLong(3, favorite.getTarget());
            return psmt;
        };
    }

    public List<Favorite> findAll(Long memberId) {
        return jdbcTemplate.query(SELECT_FAVORITE, rowMapper, memberId);
    }

    public int deleteById(Long id){
        return jdbcTemplate.update(DELETE_FAVORITE,id);
    }
}
