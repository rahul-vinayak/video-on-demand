package my.video.control;

/**
 * Created by rahul on 30/09/2017.
 */
public interface MovieService {
    String getParentalControlLevel(String movieId) throws TitleNotFoundException, TechnicalFailureException;
}
