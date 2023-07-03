public class Main {
    public static void main(String[] args) {
        MovieRunnerAverage movieRunnerAverage = new MovieRunnerAverage();
        //movieRunnerAverage.printAverageRatings();
        //movieRunnerAverage.getAverageRatingOneMovie();

        MovieRunnerWithFilters movieRunnerWithFilters = new MovieRunnerWithFilters();
        //movieRunnerWithFilters.printAverageRatings();
        //movieRunnerWithFilters.printAverageRatingsByYear();
        //movieRunnerWithFilters.printAverageRatingsByGenre();
        //movieRunnerWithFilters.printAverageRatingsByMinutes();
        //movieRunnerWithFilters.printAverageRatingsByDirectors();
        //movieRunnerWithFilters.printAverageRatingsByYearAfterAndGenre();
        movieRunnerWithFilters.printAverageRatingsByDirectorsAndMinutes();

    }
}