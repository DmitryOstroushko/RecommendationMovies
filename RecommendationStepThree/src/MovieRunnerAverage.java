import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {

    public void printAverageRatings() {
        String movieFileName = "RecommendationStepThree/data/ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 12;

        SecondRatings secondRatings = new SecondRatings(movieFileName, raterFileName);

        System.out.println("Total numbers of movies: " + secondRatings.getMovieSize());
        System.out.println("Total numbers of raters: " + secondRatings.getRaterSize());

        ArrayList<Rating> averageRatingList = secondRatings.getAverageRatings(minCountRaters);
        Collections.sort(averageRatingList);
        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + secondRatings.getTitle(rating.getItem()));
        }
    }

    public void getAverageRatingOneMovie() {
        String movieFileName = "RecommendationStepThree/data/ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        String movieTitle = "Vacation";

        SecondRatings secondRatings = new SecondRatings(movieFileName, raterFileName);
        String movieID = secondRatings.getID(movieTitle);
        System.out.println("Average rating for movie " + movieTitle + ": " + secondRatings.getAverageByID(movieID, 0));

    }
}
