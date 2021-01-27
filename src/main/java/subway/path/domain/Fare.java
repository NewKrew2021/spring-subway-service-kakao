package subway.path.domain;

import subway.line.domain.Line;
import subway.line.domain.Section;
import subway.line.domain.Sections;
import subway.station.domain.Station;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fare {

    public static final int BASIC_FARE = 1250;

    private int fare;

    public Fare() {
        this.fare = BASIC_FARE;
    }

    public int getFare() {
        return fare;
    }

    public void applyDistanceFarePolicy(int distance) {
        DistanceFare distanceFare = DistanceFare.getDistanceFare(distance);
        fare += distanceFare.calculateExtraFareByDistance(distance);
    }

    public void applyExtraFareOfLine(List<Station> shortestPath, List<Line> lines) {
        Sections allSections = new Sections();
        lines.forEach(line -> allSections.addSections(line.getSections()));

        Set<Integer> extraFares = new HashSet<>();
        for (int i = 0; i < shortestPath.size() - 1; i++) {
            Section section = allSections.findSectionContainStations(shortestPath.get(i), shortestPath.get(i+1));
            extraFares.add(
                    lines.stream()
                            .filter(line -> line.getSections().hasSection(section))
                            .findFirst()
                            .get()
                            .getExtraFare()
            );
        }
        fare += Collections.max(extraFares);
    }

    public void applyAgeFarePolicy(int age) {
        AgeFare ageFare = AgeFare.getAgeFare(age);
        fare -= ageFare.calculateDiscountFareByAge(fare);
    }
}
