import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerSimilarRatings {

    public void printAverageRatings() {
        String movieFileName = "RecommendationStepFour/data/ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepFour/data/ratings.csv";
        int minCountRaters = 35;

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatings(minCountRaters);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre() {
        String movieFileName = "RecommendationStepFour/data/ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepFour/data/ratings.csv";
        int minCountRaters = 8;
        String genre = "Drama";
        int yearAfter = 1990;

        FourthRatings fourthRatings = new FourthRatings();
        System.out.println("Total numbers of raters: " + RaterDatabase.size());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        AllFilters allFilters = new AllFilters();
        GenreFilter genreFilter = new GenreFilter(genre);
        allFilters.addFilter(genreFilter);
        YearAfterFilter yearAfterFilter = new YearAfterFilter(yearAfter);
        allFilters.addFilter(yearAfterFilter);

        ArrayList<Rating> averageRatingList = fourthRatings.getAverageRatingsByFilter(minCountRaters, allFilters);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
        }

    }

    private double dotProduct(Rater me, Rater r) {
        double dotResult = 0;
        for (String movieID : MovieDatabase.filterBy(new TrueFilter())) {
            double meRating = me.getRating(movieID);
            double rRating = r.getRating(movieID);
            if ((meRating <= 0) || (rRating <= 0)) continue;
            dotResult += (meRating - 5) * (rRating - 5);
        }
        return dotResult;
    }

    private ArrayList<Rating> getSimilarities(String raterID) {
        ArrayList<Rating> list = new ArrayList<>();
        Rater me = RaterDatabase.getRater(raterID);
        for (Rater r : RaterDatabase.getRaters()) {
            if (r.getID() == me.getID()) continue;
            double similarity = dotProduct(r, me);
            if (similarity <= 0) continue;
            list.add(new Rating(r.getID(), similarity));
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public ArrayList<Rating> getSimilarRatings(String raterID, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> similarities = getSimilarities(raterID);

        ArrayList<Rating> movieList = new ArrayList<>();

        for (String movieID : MovieDatabase.filterBy(new TrueFilter())) {

            double totalWeightedAverage = 0.0;
            int countWeightedAverage = 0;
            for (int idx = 0; idx < numSimilarRaters; idx++) {
                double similarityRating = similarities.get(idx).getValue();
                double movieRating = RaterDatabase.getRater(similarities.get(idx).getItem()).getRating(movieID);
                if ((similarityRating <= 0) || (movieRating <= 0)) continue;
                totalWeightedAverage += similarityRating * movieRating;
                countWeightedAverage++;
            }
            if (countWeightedAverage >= minimalRaters) movieList.add(new Rating(movieID, totalWeightedAverage/countWeightedAverage));
        }
        Collections.sort(movieList, Collections.reverseOrder());
        return movieList;
    }


    public ArrayList<Rating> getSimilarRatingsByFilter(String raterID, int numSimilarRaters, int minimalRaters,
                                                       Filter filterCriteria) {
        ArrayList<Rating> similarities = getSimilarities(raterID);

        ArrayList<Rating> movieList = new ArrayList<>();

        for (String movieID : MovieDatabase.filterBy(filterCriteria)) {
            double totalWeightedAverage = 0.0;
            int countWeightedAverage = 0;
            for (int idx = 0; idx < numSimilarRaters; idx++) {
                double similarityRating = similarities.get(idx).getValue();
                double movieRating = RaterDatabase.getRater(similarities.get(idx).getItem()).getRating(movieID);
                if ((similarityRating <= 0) || (movieRating <= 0)) continue;
                totalWeightedAverage += similarityRating * movieRating;
                countWeightedAverage++;
            }
            if (countWeightedAverage >= minimalRaters) movieList.add(new Rating(movieID, totalWeightedAverage/countWeightedAverage));
        }
        Collections.sort(movieList, Collections.reverseOrder());
        return movieList;
    }

    public void printSimilarRatings() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "ratings.csv";
        String raterID = "71";
        int minCountRaters = 5;
        int topSimilarRaters = 20;

        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());
        RaterDatabase.initialize(raterFileName);
        System.out.println("Total numbers of raters: " + RaterDatabase.size());

        ArrayList<Rating> movieList = getSimilarRatings(raterID, topSimilarRaters, minCountRaters);
        System.out.println("Total similar movies: " + movieList.size());
        System.out.println("TOP similar movie: " + movieList.get(0).getItem() + " " + MovieDatabase.getMovie(movieList.get(0).getItem()).getTitle() + " [" + movieList.get(0).getValue() + "]");
    }

    public void printSimilarRatingsByGenre() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "ratings.csv";
        String raterID = "964";
        int minCountRaters = 5;
        int topSimilarRaters = 20;
        String genre = "Mystery";

        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());
        RaterDatabase.initialize(raterFileName);
        System.out.println("Total numbers of raters: " + RaterDatabase.size());

        GenreFilter genreFilter = new GenreFilter(genre);
        ArrayList<Rating> movieList = getSimilarRatingsByFilter(raterID, topSimilarRaters, minCountRaters, genreFilter);
        System.out.println("Total similar movies: " + movieList.size());
        System.out.println("TOP similar movie: " + movieList.get(0).getItem() + " " + MovieDatabase.getMovie(movieList.get(0).getItem()).getTitle() + " [" + movieList.get(0).getValue() + "]");
    }


    public void printSimilarRatingsByDirector() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "ratings.csv";
        String raterID = "120";
        int minCountRaters = 2;
        int topSimilarRaters = 10;
        String director = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";

        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());
        RaterDatabase.initialize(raterFileName);
        System.out.println("Total numbers of raters: " + RaterDatabase.size());

        DirectorsFilter directorsFilter = new DirectorsFilter(director);
        ArrayList<Rating> movieList = getSimilarRatingsByFilter(raterID, topSimilarRaters, minCountRaters, directorsFilter);
        System.out.println("Total similar movies: " + movieList.size());
        System.out.println("TOP similar movie: " + movieList.get(0).getItem() + " " + MovieDatabase.getMovie(movieList.get(0).getItem()).getTitle() + " [" + movieList.get(0).getValue() + "]");
    }



    public void printSimilarRatingsByGenreAndMinutes() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "ratings.csv";
        String raterID = "168";
        int minCountRaters = 3;
        int topSimilarRaters = 10;
        String genre = "Drama";
        int minMinutes = 80;
        int maxMinutes = 160;

        FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());
        RaterDatabase.initialize(raterFileName);
        System.out.println("Total numbers of raters: " + RaterDatabase.size());


        AllFilters allFilters = new AllFilters();
        GenreFilter genreFilter = new GenreFilter(genre);
        allFilters.addFilter(genreFilter);
        MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> movieList = getSimilarRatingsByFilter(raterID, topSimilarRaters, minCountRaters, allFilters);

        System.out.println("Total similar movies: " + movieList.size());
        System.out.println("TOP similar movie: " + movieList.get(0).getItem() + " " + MovieDatabase.getMovie(movieList.get(0).getItem()).getTitle() + " [" + movieList.get(0).getValue() + "]");
    }


    public void printSimilarRatingsByYearAfterAndMinutes() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "ratings.csv";
        String raterID = "314";
        int minCountRaters = 5;
        int topSimilarRaters = 10;
        int yearAfter = 1975;
        int minMinutes = 70;
        int maxMinutes = 200;

        //FourthRatings fourthRatings = new FourthRatings();
        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());
        RaterDatabase.initialize(raterFileName);
        System.out.println("Total numbers of raters: " + RaterDatabase.size());


        AllFilters allFilters = new AllFilters();
        YearAfterFilter yearAfterFilter = new YearAfterFilter(yearAfter);
        allFilters.addFilter(yearAfterFilter);
        MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        allFilters.addFilter(minutesFilter);
        ArrayList<Rating> movieList = getSimilarRatingsByFilter(raterID, topSimilarRaters, minCountRaters, allFilters);

        System.out.println("Total similar movies: " + movieList.size());
        System.out.println("TOP similar movie: " + movieList.get(0).getItem() + " " + MovieDatabase.getMovie(movieList.get(0).getItem()).getTitle() + " [" + movieList.get(0).getValue() + "]");
    }

}
