package subway.member.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.member.domain.Member;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MemberDao {
    public static final String UPDATE_QUERY = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    public static final String DELETE_BY_ID_QUERY = "delete from MEMBER where id = ?";
    public static final String FIND_BY_ID_QUERY = "select * from MEMBER where id = ?";
    public static final String FIND_BY_EMAIL_QUERY = "select * from MEMBER where email = ?";
    public static final String EXISTS_QUERY = "select * from MEMBER where email = ? and password = ? limit 1";
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Member> rowMapper = (rs, rowNum) ->
            new Member(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getInt("age")
            );


    public MemberDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member insert(Member member) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new Member(id, member.getEmail(), member.getPassword(), member.getAge());
    }

    public void update(Member member) {
        jdbcTemplate.update(UPDATE_QUERY, member.getEmail(), member.getPassword(), member.getAge(), member.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_BY_ID_QUERY, id);
    }

    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_QUERY, rowMapper, id);
    }

    public Member findByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, rowMapper, email);
    }

    public boolean isExist(String email, String password) {
        List<Member> result = jdbcTemplate.query(EXISTS_QUERY, rowMapper, email, password);
        return !result.isEmpty();
    }
}
