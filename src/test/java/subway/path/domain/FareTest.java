package subway.path.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.member.domain.LoginMember;
import subway.station.domain.Station;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FareTest {
    /**
     * line1 : 300
     * line2 : 500
     * line3 : 1000
     *
     * station1    --- line1[20] ---   station2
     * |                        |
     * line2[30]                  line1[70]
     * |                        |
     * station4  --- line3[10] ---   station3
     */

    private static LoginMember child = new LoginMember(1L,"a",6);
    private static LoginMember baby = new LoginMember(1L,"a",3);
    private static LoginMember teenager = new LoginMember(1L,"a",13);
    private static LoginMember adult = new LoginMember(1L,"a",20);

    private static Station station1 = new Station(1L,"station1");
    private static Station station2 = new Station(2L,"station2");
    private static Station station3 = new Station(3L,"station3");
    private static Station station4 = new Station(4L,"station4");

    private static Section section1to2 = new Section(station1,station2,20);
    private static Section section2to3 = new Section(station2,station3,70);
    private static Section section3to4 = new Section(station3,station4,10);
    private static Section section4to1 = new Section(station4,station1,30);

    private static Line line1;
    private static Line line2;
    private static Line line3;
    private static List<Line> lines;

    @BeforeEach
    public void setUp() {
        line1 = new Line(1L,"line1","a"
                ,300, new Sections(new ArrayList<>(Arrays.asList(section1to2,section2to3))));
        line2 = new Line(2L,"line2","b"
                ,500, new Sections(new ArrayList<>(Arrays.asList(section4to1))));
        line3 = new Line(1L,"line3","c"
                ,1000, new Sections(new ArrayList<>(Arrays.asList(section3to4))));

        lines = new ArrayList<>(Arrays.asList(line1,line2,line3));
    }

    @DisplayName("주어진 노선과, 출발 목적역, 도착 목적역, 회원정보가 주어졌을 때 그에 맞는 최종 금액을 반환한다.")
    @Test
    public void Test1() {
        //given : 경로 station1 -> station4 -> station3  기본금액 1250원 라인 추가액 : 1000  거리 40 -> 추가액 : 600
        ShortestGraph shortestGraph1 = new ShortestGraph(lines,station1,station3);

        //when 어린이 -> 최종금액 2850원에서 350원 뺀 2500원에서 50%를 적용한 1250원을 2850원에서 제외 -> 1600원
        Fare fare = new Fare(shortestGraph1, child);

        //then
        assertThat(fare.getFare()).isEqualTo(1600);
    }

    @DisplayName("주어진 노선과, 출발 목적역, 도착 목적역, 회원정보가 주어졌을 때 그에 맞는 최종 금액을 반환한다.")
    @Test
    public void Test2() {
        //given : 경로 station2 -> station1 -> station4 -> station3  기본금액 1250원 라인 추가액 : 1000  거리 60 -> 추가액 : 1000
        ShortestGraph shortestGraph2 = new ShortestGraph(lines,station2,station3);

        //when 어른,로그인안한분 -> 최종금액 3250원람 / 영유아 -> 공짜
        Fare fareAdult = new Fare(shortestGraph2, adult);
        Fare fareNotLogin = new Fare(shortestGraph2,LoginMember.NOT_LOGIN_MEMBER);
        Fare fareBaby = new Fare(shortestGraph2,baby);

        //then
        assertThat(fareAdult.getFare()).isEqualTo(3250);
        assertThat(fareNotLogin.getFare()).isEqualTo(3250);
        assertThat(fareBaby.getFare()).isEqualTo(0);
    }

    @DisplayName("주어진 노선과, 출발 목적역, 도착 목적역, 회원정보가 주어졌을 때 그에 맞는 최종 금액을 반환한다.")
    @Test
    public void Test3() {
        //given : 경로 station2 -> station1 -> station4 -> station3  기본금액 1250원 라인 추가액 : 500  거리 50 -> 추가액 : 800
        ShortestGraph shortestGraph3 = new ShortestGraph(lines,station2,station4);

        //when 청소 -> 최종금액 2550원에서 350원을 뺀 2200원의 20% 금액인 440원을 할인해서 2110
        Fare fare = new Fare(shortestGraph3,teenager);

        //then
        assertThat(fare.getFare()).isEqualTo(2110);

    }

}
