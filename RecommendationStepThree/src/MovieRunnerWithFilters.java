import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerWithFilters {

    public void printAverageRatings() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
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

    public void printAverageRatingsByYear() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 20;
        int yearAfter = 2000;

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        YearAfterFilter yearAfterFilter = new YearAfterFilter(yearAfter);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, yearAfterFilter);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByGenre() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 20;
        String genre = "Comedy";

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        GenreFilter genreFilter = new GenreFilter(genre);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, genreFilter);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getGenres(rating.getItem()));
        }

    }

    public void printAverageRatingsByMinutes() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 5;
        int minMinutes = 105;
        int maxMinutes = 135;

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, minutesFilter);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());

        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": time=" + MovieDatabase.getMinutes(rating.getItem()) + ", " + MovieDatabase.getTitle(rating.getItem()));
        }

    }

    public void printAverageRatingsByDirectors() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 4;
        String director = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollak";

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        DirectorsFilter directorsFilter = new DirectorsFilter(director);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, directorsFilter);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("     " + MovieDatabase.getDirector(rating.getItem()));
        }

    }

    public void printAverageRatingsByYearAfterAndGenre() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 8;
        String genre = "Drama";
        int yearAfter = 1990;

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        AllFilters allFilters = new AllFilters();
        GenreFilter genreFilter = new GenreFilter(genre);
        allFilters.addFilter(genreFilter);
        YearAfterFilter yearAfterFilter = new YearAfterFilter(yearAfter);
        allFilters.addFilter(yearAfterFilter);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, allFilters);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
        }

    }

    public void printAverageRatingsByDirectorsAndMinutes() {
        String movieFileName = "ratedmoviesfull.csv";
        String raterFileName = "RecommendationStepThree/data/ratings.csv";
        int minCountRaters = 3;
        String director = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollak";

        int minMinutes = 90;
        int maxMinutes = 180;

        ThirdRatings thirdRatings = new ThirdRatings(raterFileName);
        System.out.println("Total numbers of raters: " + thirdRatings.getRaterSize());

        MovieDatabase.initialize(movieFileName);
        System.out.println("Total numbers of movies: " + MovieDatabase.size());

        AllFilters allFilters = new AllFilters();
        DirectorsFilter directorsFilter = new DirectorsFilter(director);
        allFilters.addFilter(directorsFilter);
        MinutesFilter minutesFilter = new MinutesFilter(minMinutes, maxMinutes);
        allFilters.addFilter(minutesFilter);

        ArrayList<Rating> averageRatingList = thirdRatings.getAverageRatingsByFilter(minCountRaters, allFilters);
        Collections.sort(averageRatingList);
        System.out.println("Numbers of movies with minimum " + minCountRaters + " raters: " + averageRatingList.size());


        for (Rating rating : averageRatingList) {
            System.out.println(rating.getValue() + ": " + MovieDatabase.getTitle(rating.getItem()));
        }

    }

}
