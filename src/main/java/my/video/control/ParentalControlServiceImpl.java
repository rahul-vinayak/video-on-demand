package my.video.control;


import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class ParentalControlServiceImpl implements ParentalControlService {

    private final static Logger logger = LoggerFactory.getLogger(ParentalControlServiceImpl.class);
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
        } catch (TitleNotFoundException tnfe) {
            logger.error("exception thrown while checking control for movie: {}, {}", movieId, tnfe.getMessage());
            return new PCSResponse(false, "Movie not found with id: " + movieId);
        } catch (TechnicalFailureException tfe) {
            logger.error("exception thrown while checking control for movie: {}, {}", movieId, tfe.getMessage());
            return new PCSResponse(false, "Movie cannot be watched at this time");
        } catch (PCLException pcle) {
            logger.error("error occurred, {}", pcle.getMessage());
            return new PCSResponse(false, "Movie cannot be watched at this time");
        }
        return new PCSResponse(false, "Movie viewing restricted");
    }

    private boolean isControlAllowed(ParentalControlLevel parentalControlLevelPreference, String movieId) throws TechnicalFailureException, TitleNotFoundException, PCLException {
        String parentalControlLevelString = movieService.getParentalControlLevel(movieId);
        ParentalControlLevel parentalControlLevel = ParentalControlLevel.ofLevelCode(parentalControlLevelString);
        return parentalControlLevelPreference.ordinal() >= parentalControlLevel.ordinal();
    }
}
