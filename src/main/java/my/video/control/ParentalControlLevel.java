package my.video.control;

import java.util.stream.Stream;

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

    public static ParentalControlLevel ofLevelCode(String levelCode) throws PCLException {
        return Stream
                .of(ParentalControlLevel.values())
                .filter(pcl -> pcl.levelCode.equals(levelCode))
                .findFirst()
                .orElseThrow(() -> new PCLException("Failed to map the control level to "+levelCode));
    }
}
