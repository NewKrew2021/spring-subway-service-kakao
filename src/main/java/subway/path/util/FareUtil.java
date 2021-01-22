package subway.path.util;


public class FareUtil {

    public static int calculateDistanceFare(int distance){
        int totalFare = 1250;

        if(distance < 10){
            return totalFare;
        }
        return getExtraFareByDistance(distance) + totalFare;
    }

    public static int getExtraFareByDistance(int distance) {
        if(distance < 50) {
            return (int) Math.ceil((double)(distance - 10) / 5) * 100;
        }
        return 800 + Math.max(0, (int) Math.ceil((double)(distance - 50) / 8) * 100);
    }
}
