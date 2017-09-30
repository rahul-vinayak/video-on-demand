package my.video.control;

import org.apache.log4j.Logger;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by rahul on 30/09/2017.
 */
public class ParentalControlServiceImpl implements ParentalControlService {


    private static final Logger logger = Logger.getLogger(ParentalControlServiceImpl.class);
    private final MovieService movieService;

    public ParentalControlServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public PCSResponse isParentalControlSatisfied(ParentalControlLevel parentalControlLevelPreference, String movieId) {
        if (parentalControlLevelPreference == null) {
            return new PCSResponse(false, "Parental Control Level preference is missing");
        }
        if (isBlank(movieId)) {
            return new PCSResponse(false, "Movie ID is missing");
        }

        try {
            if (isControlAllowed(parentalControlLevelPreference, movieId)) {
                return new PCSResponse(true, null);
            }
        } catch (TitleNotFoundException tnf) {
            logger.error("exception thrown while checking control for movie: " + movieId, tnf);
            return new PCSResponse(false, "Title for the movie with id:" + movieId + " not found");
        } catch (TechnicalFailureException tf) {
            logger.error("exception thrown while checking control for movie: " + movieId, tf);
            return new PCSResponse(false, "You cannot watch the movie at this time");
        } catch (PCLException pcle) {
            logger.error("error occurred, " + pcle.getMessage(), pcle);
            return new PCSResponse(false, "You cannot watch the movie at this time");
        }
        return new PCSResponse(false, "Movie viewing restricted");
    }

    private boolean isControlAllowed(ParentalControlLevel parentalControlLevelPreference, String movieId) throws TechnicalFailureException, TitleNotFoundException, PCLException {
        String parentalControlLevelString = null;
        parentalControlLevelString = movieService.getParentalControlLevel(movieId);
        ParentalControlLevel parentalControlLevel = ParentalControlLevel.ofCode(parentalControlLevelString);
        return parentalControlLevelPreference.ordinal() >= parentalControlLevel.ordinal();
    }
}
