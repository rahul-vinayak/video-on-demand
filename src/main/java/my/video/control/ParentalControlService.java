package my.video.control;

public interface ParentalControlService {

    PCSResponse isParentalControlSatisfied(ParentalControlLevel parentalControlLevel, String movieId);

}
