package subway.member.dao;

public class MemberDaoQuery {
    public static final String updateQuery = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    public static final String deleteByIdQuery = "delete from MEMBER where id = ?";
    public static final String findByIdQuery = "select * from MEMBER where id = ?";
    public static final String findByEmailQuery = "select * from MEMBER where email = ?";
}
