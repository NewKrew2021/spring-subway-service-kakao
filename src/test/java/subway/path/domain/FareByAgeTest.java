package subway.path.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FareByAgeTest {

    @Test
    void ageTest(){
        FareByAge baby = new FareByAge(3000, 5);
        FareByAge young = new FareByAge(3000, 7);
        FareByAge teen = new FareByAge(3000, 15);
        FareByAge adult = new FareByAge(3000, 30);

        assertThat(baby.getFare()).isEqualTo(0);
        assertThat(young.getFare()).isEqualTo(1675);
        assertThat(teen.getFare()).isEqualTo(2470);
        assertThat(adult.getFare()).isEqualTo(3000);
    }
}