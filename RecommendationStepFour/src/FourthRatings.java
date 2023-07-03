import java.util.ArrayList;

public class FourthRatings {

    public double getAverageByID(String movieID, int minimalRaters) {

        int countRatings = 0;
        double totalRating = 0.0;
        ArrayList<Rater> myRaters = RaterDatabase.getRaters(); //Rater -> EfficientRater
        for (Rater rater : myRaters) {
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movie : movies) {
            double rating = getAverageByID(movie, minimalRaters);
            if (rating == 0.0) continue;
            Rating ratingObject = new Rating(movie, rating);
            ratingList.add(ratingObject);
        }
        return ratingList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratingList = new ArrayList<Rating>();

        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String movie : movies) {
            double rating = getAverageByID(movie, minimalRaters);
            if (rating == 0.0) continue;
            Rating ratingObject = new Rating(movie, rating);
            ratingList.add(ratingObject);
        }
        return ratingList;

    }
}
