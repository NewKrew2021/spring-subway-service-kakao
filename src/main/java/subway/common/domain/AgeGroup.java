package subway.common.domain;

import java.util.Arrays;

public enum AgeGroup {
    INFANTS(Range.of(0, 6)),
    CHILD(Range.of(6,13)),
    TEENAGER(Range.of(13,19)),
    ADULT(Range.of(19,200))
    ;

    private final Range ageRange;

    AgeGroup(Range range) {
        this.ageRange = range;
    }

    public static AgeGroup from(Age age) {
        return Arrays.stream(AgeGroup.values())
                .filter(ageGroup -> ageGroup.ageRange.isBelong(age.getAge()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("정의되지 않은 연령입니다."));
    }
}
