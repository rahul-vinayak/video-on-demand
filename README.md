# video-on-demand

Parental Control service to return the suitability of watching a movie as per the Parent Control

# To run
<code>
mvn clean install 
  
  that will run all the tests.

ParentalControlServiceImpl is the implementation of the interface to ParentalControlService having method isParentalControlSatisfied(ParentalControlLevel parentalControlLevelPreference, String movieId)

params are ParentalControlPreference(enum ParentalControlLevel) and the MovieID(String) to be passed in by the client. 

The tests are in the class ParentalControlServiceTest.java

