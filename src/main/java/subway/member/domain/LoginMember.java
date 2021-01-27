package subway.member.domain;

public class LoginMember {
    public static final double CHILD_DISCOUNT_RATE = 0.5;
    public static final double TEENAGER_DISCOUNT_RATE = 0.2;
    public static final double NONE_DISCOUNT_RATE = 0.0;
    public static final int MINIMUM_CHILD_AGE = 6;
    public static final int MAXIMUM_CHILD_AGE = 12;
    public static final int MINIMUM_TEENAGER_AGE = 13;
    public static final int MAXIMUM_TEENAGER_AGE = 18;

    private Long id;
    private String email;
    private Integer age;

    public LoginMember() {
    }

    public LoginMember(Long id, String email, Integer age) {
        this.id = id;
        this.email = email;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public double getDiscountRate() {
        if (age >= MINIMUM_CHILD_AGE && age <= MAXIMUM_CHILD_AGE) {
            return CHILD_DISCOUNT_RATE;
        }
        if (age >= MINIMUM_TEENAGER_AGE && age <= MAXIMUM_TEENAGER_AGE) {
            return TEENAGER_DISCOUNT_RATE;
        }
        return NONE_DISCOUNT_RATE;
    }
}
