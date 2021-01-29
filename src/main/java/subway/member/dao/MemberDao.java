package subway.member.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exception.InvalidMemberException;
import subway.member.domain.Member;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class MemberDao {
    public static final String FIND_MEMBER_BY_MEMBER_ID = "select * from MEMBER where id = ?";
    public static final String UPDATE_MEMBER = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    public static final String DELETE_MEMBER = "delete from MEMBER where id = ?";
    public static final String FIND_MEMBER_BY_EMAIL = "select * from MEMBER where email = ?";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private RowMapper<Member> rowMapper = (rs, rowNum) ->
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
        jdbcTemplate.update(UPDATE_MEMBER, new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_MEMBER, id);
    }

    public Member findById(Long id) {
        return jdbcTemplate.queryForObject(FIND_MEMBER_BY_MEMBER_ID, rowMapper, id);
    }

    public Optional<Member> findByEmail(String email) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_MEMBER_BY_EMAIL, rowMapper, email));
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
}
