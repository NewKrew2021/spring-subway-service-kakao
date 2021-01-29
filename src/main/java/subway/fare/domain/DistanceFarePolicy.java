package subway.fare.domain;

public class DistanceFarePolicy {

    public static final int FIRST_INTERVAL_THRESHOLD = 10;
    public static final int SECOND_INTERVAL_THRESHOLD = 50;

    public static final int FIRST_INTERVAL_UNIT_DISTANCE = 5;
    public static final int SECOND_INTERVAL_UNIT_DISTANCE = 8;

    public static final int UNIT_DISTANCE_FARE = 100;

    public static final int FIRST_INTERVAL_MAXIMUM_DISTANCE_FARE =
            ((SECOND_INTERVAL_THRESHOLD - FIRST_INTERVAL_THRESHOLD - 1)
                    / FIRST_INTERVAL_UNIT_DISTANCE + 1)
                    * UNIT_DISTANCE_FARE;


    public static int findDistanceFare(int distance) {
        if (distance <= FIRST_INTERVAL_THRESHOLD) {
            return 0;
        }
        if (distance <= SECOND_INTERVAL_THRESHOLD) {
            return ((distance - FIRST_INTERVAL_THRESHOLD - 1)
                    / FIRST_INTERVAL_UNIT_DISTANCE + 1)
                    * UNIT_DISTANCE_FARE;
        }

        return FIRST_INTERVAL_MAXIMUM_DISTANCE_FARE
                + ((distance - SECOND_INTERVAL_THRESHOLD - 1)
                / SECOND_INTERVAL_UNIT_DISTANCE + 1)
                * UNIT_DISTANCE_FARE;
    }
}
