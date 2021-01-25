package subway.member.domain;

public enum LoginMemberType {
    KID, CHILD, ADOLESCENT, ADULT;
    private static final int LIMIT_KID_AGE = 5;
    private static final int LIMIT_CHILD_AGE = 12;
    private static final int LIMIT_ADOLESCENT_AGE = 18;

    public static LoginMemberType getLoginMemberType(int age) {
        if (age <= LIMIT_KID_AGE) {
            return KID;
        }
        if (age <= LIMIT_CHILD_AGE) {
            return CHILD;
        }
        if (age <= LIMIT_ADOLESCENT_AGE) {
            return ADOLESCENT;
        }
        return ADULT;
    }
}
