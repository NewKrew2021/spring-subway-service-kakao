package subway.member.dao;

public class MemberDaoQuery {
    public static final String UPDATE_QUERY = "update MEMBER set email = ?, password = ?, age = ? where id = ?";
    public static final String DELETE_BY_ID_QUERY = "delete from MEMBER where id = ?";
    public static final String FIND_BY_ID_QUERY = "select m.id, m.email, m.password, m.age from MEMBER m where id = ?";
    public static final String FIND_BY_EMAIL_QUERY = "select m.id, m.email, m.password, m.age from MEMBER m where email = ?";
}
