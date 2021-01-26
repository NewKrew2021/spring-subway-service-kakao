package subway.util;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.member.domain.LoginMember;

import java.util.Comparator;
import java.util.List;

public class FareUtil {
    private static final int BASE_FARE = 1250;
    private static final int PERCENTAGE_VALUE = 100;
    private static final int DEDUCTION_FARE = 350;
    private static final double TEENAGER_SALE_RATE = 0.2;
    private static final double CHILD_SALE_RATE = 0.5;
    private static final int BASIC_FARE_DISTANCE = 10;
    private static final int FIFTY_KILOMETER = 50;
    private static final int OVER_FIFTY_KILOMETER_FARE = 800;
    private static final double MIDDLE_DISTANCE_DENOMINATOR = 5;
    private static final double HIGH_DISTANCE_DENOMINATOR = 8;

    public static int getTotalFare(LoginMember loginMember, int totalFare) {
        if (loginMember.isTeenager()) {
            totalFare -= (totalFare - DEDUCTION_FARE) * TEENAGER_SALE_RATE;
        }
        if (loginMember.isChild()) {
            totalFare -= (totalFare - DEDUCTION_FARE) * CHILD_SALE_RATE;
        }

        return totalFare;
    }

    public static int calculateDistanceFare(int totalDistance) {
        int distanceFare = BASE_FARE;
        if (BASIC_FARE_DISTANCE < totalDistance && totalDistance <= FIFTY_KILOMETER) {
            distanceFare += Math.ceil((totalDistance - BASIC_FARE_DISTANCE) / MIDDLE_DISTANCE_DENOMINATOR) * PERCENTAGE_VALUE;
        }
        if (totalDistance > FIFTY_KILOMETER) {
            distanceFare += OVER_FIFTY_KILOMETER_FARE + Math.ceil((totalDistance - FIFTY_KILOMETER) / HIGH_DISTANCE_DENOMINATOR) * PERCENTAGE_VALUE;
        }
        return distanceFare;
    }

    public static int getMaxExtraFare(List<Section> sections, List<Line> lines) {
        return lines.stream()
                .filter(line -> sections.stream()
                        .anyMatch(section -> line.getSections().isExist(section)))
                .max(Comparator.comparingInt(Line::getExtraFare))
                .get()
                .getExtraFare();
    }
}
