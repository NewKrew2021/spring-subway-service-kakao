package subway.path.domain;

import org.junit.jupiter.api.Test;

import  static  org.assertj.core.api.Assertions.assertThat;

class DistanceFareTest {


    @Test
    public void distanceFareTest(){
        int distanceFare=DistanceFare.getDistanceFare(1);
        assertThat(distanceFare).isEqualTo(1250);
        distanceFare=DistanceFare.getDistanceFare(11);
        assertThat(distanceFare).isEqualTo(1350);
        distanceFare=DistanceFare.getDistanceFare(15);
        assertThat(distanceFare).isEqualTo(1350);
        distanceFare=DistanceFare.getDistanceFare(16);
        assertThat(distanceFare).isEqualTo(1450);
        distanceFare=DistanceFare.getDistanceFare(49);
        assertThat(distanceFare).isEqualTo(2050);
        distanceFare=DistanceFare.getDistanceFare(50);
        assertThat(distanceFare).isEqualTo(2050);

        distanceFare=DistanceFare.getDistanceFare(51);
        assertThat(distanceFare).isEqualTo(2150);
        distanceFare=DistanceFare.getDistanceFare(58);
        assertThat(distanceFare).isEqualTo(2150);
        distanceFare=DistanceFare.getDistanceFare(59);
        assertThat(distanceFare).isEqualTo(2250);
    }

}