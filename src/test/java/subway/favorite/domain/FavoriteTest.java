package subway.favorite.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.station.domain.Station;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FavoriteTest {

    @DisplayName("생성자 테스트")
    @Test
    public void FavoriteTest() {

        Favorite favorite = new Favorite(1L,new Station(1L, "강남역" ), new Station(2L,"판교역") );
        assertThat(favorite.getId()).isEqualTo(1L);
        assertThat(favorite.getSource().getName()).isEqualTo("강남역");
        assertThat(favorite.getTarget().getName()).isEqualTo("판교역");
    }

}
