package subway.member.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import subway.exception.AlreadyExistedDataException;
import subway.exception.NotExistMemberException;
import subway.member.domain.Member;

import javax.sql.DataSource;

@Repository
public class MemberDao {
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
        try {
            Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
            return new Member(id, member.getEmail(), member.getPassword(), member.getAge());
        } catch (RuntimeException e) {
            throw new AlreadyExistedDataException();
        }
    }

    public void update(Member member) {
        String sql = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
        jdbcTemplate.update(sql, new Object[]{member.getEmail(), member.getPassword(), member.getAge(), member.getId()});
    }

    public void deleteById(Long id) {
        String sql = "delete from MEMBER where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Member findById(Long id) {
        String sql = "select * from MEMBER where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, id);
        } catch (RuntimeException e) {
            throw new NotExistMemberException();
        }
    }

    public Member findByEmail(String email) {
        System.out.println(email);
        String sql = "select * from MEMBER where email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, rowMapper, email);
        } catch (RuntimeException e) {
            throw new NotExistMemberException();
        }
    }
}
