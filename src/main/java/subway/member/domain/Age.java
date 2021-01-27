package subway.member.domain;

public class Age {
    private final int age;

    public Age(int age) {
        validate(age);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    private void validate(int age) {
        if(age <= 0) {
            throw new IllegalArgumentException("나이는 0살보다 커야합니다.");
        }
    }
}