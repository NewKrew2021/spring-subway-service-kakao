package subway.path.util;


public class FareUtil {

    public static final int BASE_MONEY = 1250;
    public static final int DISTANCE_10KM = 10;
    public static final int DISTANCE_50KM = 50;
    public static final int AMOUNT_PER_DISTANCE_BETWEEN_10KM_TO_50KM = 100;
    public static final int DIVISOR_BETWEEN_10KM_TO_50KM = 5;
    public static final int DIVISOR_OVER_50KM = 8;
    public static final int EXCESS_50KM_MONEY = ((DISTANCE_50KM - DISTANCE_10KM) / DIVISOR_BETWEEN_10KM_TO_50KM) * 100;
    public static final int AMOUNT_PER_DISTANCE_OVER_50KM = 100;

    public static int calculateDistanceFare(int distance){
        int totalFare = BASE_MONEY;

        if(distance < DISTANCE_10KM){
            return totalFare;
        }
        return getExtraFareByDistance(distance) + totalFare;
    }

    public static int getExtraFareByDistance(int distance) {
        if(distance < DISTANCE_50KM) {
            return (int) Math.ceil((double)(distance - DISTANCE_10KM) / DIVISOR_BETWEEN_10KM_TO_50KM) * AMOUNT_PER_DISTANCE_BETWEEN_10KM_TO_50KM;
        }
        return EXCESS_50KM_MONEY + Math.max(0, (int) Math.ceil((double)(distance - DISTANCE_50KM) / DIVISOR_OVER_50KM) * AMOUNT_PER_DISTANCE_OVER_50KM);
    }
}
