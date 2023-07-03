
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.ArrayList;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;

    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }

    public SecondRatings(String movieFile, String ratingsFile) {
        FirstRatings firstRatings = new FirstRatings();
        myMovies = firstRatings.loadMovies(movieFile);
        myRaters = firstRatings.loadRaters(ratingsFile);
    }

    public int getMovieSize() {
        return myMovies.size();
    }

    public int getRaterSize() {
        return myRaters.size();
    }

    public double getAverageByID(String movieID, int minimalRaters) {

        int countRatings = 0;
        double totalRating = 0.0;

        for (EfficientRater rater : myRaters) {
            double rating = rater.getRating(movieID);
            if (rating == -1) continue;
            countRatings++;
            totalRating += rating;
        }

        if (countRatings >= minimalRaters) return totalRating / countRatings;
        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {

        ArrayList<Rating> ratingList = new ArrayList<>();
        for (Movie movie : myMovies) {
            double rating = getAverageByID(movie.getID(), minimalRaters);
            if (rating == 0.0) continue;
            Rating ratingObject = new Rating(movie.getID(), rating);
            ratingList.add(ratingObject);
        }
        return ratingList;
    }

    public String getTitle(String movieID) {
        for (Movie movie : myMovies) {
            if (movie.getID().equals(movieID)) return movie.getTitle();
        }
        return "NO SUCH ID " + movieID;
    }

    public String getID(String movieTitle) {
        for (Movie movie : myMovies) {
            if (movie.getTitle().equals(movieTitle)) return movie.getID();
        }
        return "NO SUCH TITLE " + movieTitle;
    }
}
