package subway.common.domain;

import subway.common.exception.NegativeNumberException;

public class Age {
    private final int age;

    private Age(int age) {
        validate(age);
        this.age = age;
    }

    private void validate(int age) {
        if(age < 0) {
            throw new NegativeNumberException("나이는 음수가 될 수 없습니다.");
        }
    }

    public static Age from(int age) {
        return new Age(age);
    }

    public int getAge() {
        return age;
    }
}
