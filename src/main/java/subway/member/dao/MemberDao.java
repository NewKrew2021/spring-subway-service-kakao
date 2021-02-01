package subway.member.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.common.domain.Age;
import subway.member.domain.Member;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MemberDao {
    private static final String SELECT_BY_ID_QUERY = "select * from MEMBER where id = ?";
    private static final String SELECT_BY_EMAIL_QUERY = "select * from MEMBER where email = ?";
    private static final String SELECT_BY_EMAIL_AND_PASSWORD_QUERY = "select count(*) from MEMBER where email = ? and password = ?";
    private static final String UPDATE_QUERY = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    private static final String DELETE_QUERY = "delete from MEMBER where id = ?";
    private static final RowMapper<Member> rowMapper = (rs, rowNum) ->
            Member.of(
                    rs.getLong("id"),
                    rs.getString("email"),
                    rs.getString("password"),
                    Age.from(rs.getInt("age"))
            );

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public MemberDao(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("member")
                .usingGeneratedKeyColumns("id");
    }

    public Member insert(Member member) {
        Map<String, Object> params = getParamsMap(member);
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.of(id, member.getEmail(), member.getPassword(), member.getAge());
    }

    private Map<String, Object> getParamsMap(Member member) {
        Map<String, Object> params = new HashMap<>();
        params.put("email", member.getEmail());
        params.put("password", member.getPassword());
        params.put("age", member.getAge().getAge());
        return params;
    }

    public void update(Member member) {
        jdbcTemplate.update(UPDATE_QUERY,
                member.getEmail(),
                member.getPassword(),
                member.getAge().getAge(),
                member.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(SELECT_BY_ID_QUERY, rowMapper, id);
    }

    public Member findByEmail(String email) {
        return jdbcTemplate.queryForObject(SELECT_BY_EMAIL_QUERY, rowMapper, email);
    }

    public boolean isNotExistsMemberByEmailAndPassword(String email, String password) {
        Integer count = jdbcTemplate.queryForObject(SELECT_BY_EMAIL_AND_PASSWORD_QUERY, Integer.class, email, password);
        return count == null || count == 0;
    }
}
