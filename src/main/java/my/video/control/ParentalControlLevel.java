package my.video.control;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by rahul on 30/09/2017.
 */
public enum ParentalControlLevel {

    U("U"),
    PG("PG"),
    TWELVE("12"),
    FIFTEEN("15"),
    EIGHTEEN("18");

    private final String levelCode;

    ParentalControlLevel(String levelCode) {
        this.levelCode = levelCode;
    }

    public String getLevelCode() {
        return this.levelCode;
    }

    public static ParentalControlLevel ofCode(String level) throws PCLException {
        return Stream
                .of(ParentalControlLevel.values())
                .filter(pcl -> pcl.levelCode.equals(level))
                .findFirst()
                .orElseThrow(() -> new PCLException("Failed to get the correct control level"));
    }
}
