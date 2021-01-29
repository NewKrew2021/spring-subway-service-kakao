package subway.path;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.ExtraFare;
import subway.member.domain.LoginMember;
import subway.path.domain.PriceCalculator;
import subway.path.domain.ResultPath;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PriceCalculatorDomainTest {
    @DisplayName("유아 요금 결과")
    @Test
    void calculateResultKidTest() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station(3L,"잠실역"));
        stations.add(new Station(2L,"강남역"));
        stations.add(new Station(4L,"양재역"));
        stations.add(new Station(5L,"남부터미널역"));
        ResultPath path = new ResultPath(stations,61, ExtraFare.of(900));
        LoginMember member = new LoginMember(1L,"gagao@kakao.com",5);
        assertThat(PriceCalculator.calculateResult(member,path)).isEqualTo(0);
    }

    @DisplayName("어린이 요금 결과")
    @Test
    void calculateResultChildTest() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station(3L,"잠실역"));
        stations.add(new Station(2L,"강남역"));
        stations.add(new Station(4L,"양재역"));
        stations.add(new Station(5L,"남부터미널역"));
        ResultPath path = new ResultPath(stations,61, ExtraFare.of(900));
        LoginMember member = new LoginMember(1L,"gagao@kakao.com",12);
        assertThat(PriceCalculator.calculateResult(member,path)).isEqualTo(1750);
    }

    @DisplayName("청소 요금 결과")
    @Test
    void calculateResultAdolescentTest() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station(3L,"잠실역"));
        stations.add(new Station(2L,"강남역"));
        stations.add(new Station(4L,"양재역"));
        stations.add(new Station(5L,"남부터미널역"));
        ResultPath path = new ResultPath(stations,61, ExtraFare.of(900));
        LoginMember member = new LoginMember(1L,"gagao@kakao.com",18);
        assertThat(PriceCalculator.calculateResult(member,path)).isEqualTo(2590);
    }

    @DisplayName("성인 요금 결과")
    @Test
    void calculateResultAdultTest() {
        List<Station> stations = new ArrayList<>();
        stations.add(new Station(3L,"잠실역"));
        stations.add(new Station(2L,"강남역"));
        stations.add(new Station(4L,"양재역"));
        stations.add(new Station(5L,"남부터미널역"));
        ResultPath path = new ResultPath(stations,61, ExtraFare.of(900));
        LoginMember member = new LoginMember(1L,"gagao@kakao.com",40);
        assertThat(PriceCalculator.calculateResult(member,path)).isEqualTo(3150);
    }
}
