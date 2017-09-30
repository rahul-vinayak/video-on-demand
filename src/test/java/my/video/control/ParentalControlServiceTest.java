package my.video.control;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static my.video.control.ParentalControlLevel.TWELVE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by rahul on 30/09/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {

    private static final String MOVIE_ID = "movie-123";

    @Mock
    private MovieService mockMovieService;
    private ParentalControlService parentalControlService;

    @Before
    public void before() {
        parentalControlService = new ParentalControlServiceImpl(mockMovieService);
    }

    @Test
    public void shouldReturnFalseAndErrorIfParentalControlLevelIsNull() {
        PCSResponse response = parentalControlService.isParentalControlSatisfied(null, MOVIE_ID);
        assertFalse(response.isSatisfied());
        assertEquals("Parental Control Level preference is missing", response.getMessage());
    }

    @Test
    public void shouldReturnFalseAndErrorIfMovieIdIsNull() {
        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, null);
        assertFalse(response.isSatisfied());
        assertEquals("Movie ID is missing", response.getMessage());
    }

    @Test
    public void shouldReturnTrueIfControlPreferenceEqualToControlLevel() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenReturn("12");

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertTrue(response.isSatisfied());
        assertNull(response.getMessage());
    }

    @Test
    public void shouldReturnTrueIfControlPreferenceMoreThanControlLevel() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenReturn("PG");

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertTrue(response.isSatisfied());
        assertNull(response.getMessage());
    }

    @Test
    public void shouldReturnFalseWithErrorIfControlPreferenceLessThanControlLevel() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenReturn("18");

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertFalse(response.isSatisfied());
        assertEquals("Movie viewing restricted", response.getMessage());
    }

    @Test
    public void shouldReturnFalseWithErrorWhenTitleNotFound() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenThrow(new TitleNotFoundException("Title not found"));

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertFalse(response.isSatisfied());
        assertEquals("Title for the movie with id:movie-123 not found", response.getMessage());

    }

    @Test
    public void shouldReturnFalseWithErrorWhenTechnicalFailureException() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenThrow(new TechnicalFailureException("Some technical failure occurred"));

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertFalse(response.isSatisfied());
        assertEquals("You cannot watch the movie at this time", response.getMessage());

    }

    @Test
    public void shouldReturnFalseWithErrorWhenControlLevelFromServiceCantBeMapped() throws Exception{
        when(mockMovieService.getParentalControlLevel(MOVIE_ID)).thenReturn("365");

        PCSResponse response = parentalControlService.isParentalControlSatisfied(TWELVE, MOVIE_ID);
        assertFalse(response.isSatisfied());
        assertEquals("You cannot watch the movie at this time", response.getMessage());

    }
}
