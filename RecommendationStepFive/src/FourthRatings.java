import java.util.ArrayList;
import java.util.Collections;

public class FourthRatings {

    private double dotProduct(Rater me, Rater r) {
        ArrayList<String> myRated = me.getItemsRated();

        double dotResult = 0;
        for (String movieID : myRated) {
            if (!r.hasRating(movieID)) continue;
            double meRating = me.getRating(movieID) - 5;
            double rRating = r.getRating(movieID) - 5;
            dotResult += meRating * rRating;
        }
        return dotResult;
    }

    private ArrayList<Rating> getSimilarities(String raterID) {
        ArrayList<Rating> similarRatings = new ArrayList<>();
        ArrayList<Rater> raters = RaterDatabase.getRaters();

        for(Rater r : raters){
            if(!r.getID().equals(raterID)){
                double dotProduct = dotProduct(RaterDatabase.getRater(raterID), r);
                if(dotProduct > 0){
                    similarRatings.add(new Rating(r.getID(), dotProduct));
                }
            }
        }

        Collections.sort(similarRatings);
        Collections.reverse(similarRatings);
        return similarRatings;
    }

    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> ratings = new ArrayList<>();
        ArrayList<String> movieIDByTopSimilar = new ArrayList<>();
        ArrayList<Rating> similarRatings = getSimilarities(raterID);
        for(int i=0; i< numSimilarRaters; i++){
            String raterIDs = similarRatings.get(i).getItem();
            ArrayList<String> movieRated = RaterDatabase.getRater(raterIDs).getItemsRated();
            for(String movieID : movieRated){
                if(!movieIDByTopSimilar.contains(movieID)){
                    movieIDByTopSimilar.add(movieID);
                }
            }
        }
        for(String id : movieIDByTopSimilar){
            ArrayList<Rating> similarRatings2 = getSimilarities(raterID);
            int top = 0;
            int sum = 0;
            double weightSimilar = 0.0;
            double avg = 0.0;
            for(int i=0; i < numSimilarRaters; i++){
                double rating = RaterDatabase.getRater(similarRatings2.get(i).getItem()).getRating(id);
                if(rating != -1){
                    top++;
                    sum += rating * similarRatings.get(i).getValue();
                    weightSimilar += similarRatings.get(i).getValue();
                }
            }
            if(top >= minimalRaters){
                avg = sum / top;
            }
            if(avg > 0 ){
                ratings.add(new Rating(id, avg));
            }
        }

        Collections.sort(ratings);
        Collections.reverse(ratings);
        return ratings;
    }

    public double getAverageByID(String movieID, int minimalRaters) {

        int countRatings = 0;
        double totalRating = 0.0;
        ArrayList<Rater> myRaters = RaterDatabase.getRaters(); //Rater -> EfficientRater
        for (Rater rater : myRaters) {
            if (rater.hasRating(movieID)) {
                double rating = rater.getRating(movieID);
                countRatings++;
                totalRating += rating;
            }
        }

        if (countRatings >= minimalRaters) return totalRating / countRatings;
        return 0.0;
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters) {

        ArrayList<Rating> ratingList = new ArrayList<>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movie : movies) {
            double rating = getAverageByID(movie, minimalRaters);
            if (rating <= 0.0) continue;
            Rating ratingObject = new Rating(movie, rating);
            ratingList.add(ratingObject);
        }
        return ratingList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> ratingList = new ArrayList<Rating>();

        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        for (String movie : movies) {
            if (!filterCriteria.satisfies(movie)) continue;;
            double rating = getAverageByID(movie, minimalRaters);
            if (rating <= 0.0) continue;
            Rating ratingObject = new Rating(movie, rating);
            ratingList.add(ratingObject);
        }
        return ratingList;

    }
}
