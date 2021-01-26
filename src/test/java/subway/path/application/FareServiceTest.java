package subway.path.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import subway.line.dao.SectionDao;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FareServiceTest {

    @InjectMocks
    private FareService fareService;

    @Mock
    private SectionDao sectionDao;

    @DisplayName("거리에 따른 운임을 계산한다.")
    @ParameterizedTest
    @CsvSource({"10,1250", "11,1350", "15,1350", "50,2050", "51,2150", "58,2150"})
    void getFareByDistance(int distance, int fare) {
        assertThat(fareService.getDistanceFare(distance)).isEqualTo(fare);
    }

    @DisplayName("나이에 따른 할인 운임을 계산한다.")
    @ParameterizedTest
    @CsvSource({"5,0", "6,2850", "12,2850", "13,4350", "18,4350", "19,5350"})
    void discount(int age, int discountedFare) {
        int fare = 5350;
        assertThat(fareService.discount(fare, age)).isEqualTo(discountedFare);
    }

    @DisplayName("path 정보를 통해 어떤 노선을 지나갔는지 구한다.")
    @Test
    void getLineIds() {
        List<Long> path = Arrays.asList(1L, 5L, 3L, 4L, 6L);

        // stub
        when(sectionDao.findLineIdByUpStationIdAndDownStationId(1L, 5L)).thenReturn(4L);
        when(sectionDao.findLineIdByUpStationIdAndDownStationId(5L, 3L)).thenReturn(3L);
        when(sectionDao.findLineIdByUpStationIdAndDownStationId(3L, 4L)).thenReturn(1L);
        when(sectionDao.findLineIdByUpStationIdAndDownStationId(4L, 6L)).thenReturn(1L);

        assertThat(fareService.getLineIds(path)).containsExactlyInAnyOrder(4L, 3L, 1L);
    }
}
