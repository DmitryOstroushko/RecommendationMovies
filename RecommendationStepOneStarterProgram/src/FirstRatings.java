import edu.duke.*;

import java.lang.reflect.Array;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {


    private void countGenres(HashMap<String, Integer> genreMap, String genres) {
        String[] genreList = genres.split(",");
        for (String genre : genreList) {
            if (!genreMap.containsKey(genre.trim())) {
                genreMap.put(genre.trim(), 1);
            }
            else {
                genreMap.put(genre.trim(), genreMap.get(genre.trim()) + 1);
            }
        }

        /**
         for (Map.Entry<String, Integer> director: genreMap.entrySet()) {
         System.out.println(director.getKey() + ": " + director.getValue());
         }
         */
    }

    private void countDirectors(HashMap<String, Integer> directorMap, String directors) {
        String[] directorList = directors.split(",");

        for (String director : directorList) {
            if (!directorMap.containsKey(director.trim())){
                directorMap.put(director.trim(), 1);
            }
            else {
                directorMap.put(director.trim(), directorMap.get(director.trim()) + 1);
            }
        }
    }

    public ArrayList<Movie> loadMovies(String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();

        ArrayList<Movie> movieList = new ArrayList<>();

        for (CSVRecord record : parser) {

            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String country = record.get("country");
            String genre = record.get("genre");
            String director = record.get("director");
            String minutes = record.get("minutes");
            String poster = record.get("poster");
            Movie movie = new Movie(id, title, year, genre, director, country, poster, Integer.valueOf(minutes));

            movieList.add(movie);
        }
        return movieList;
    }

    public void testLoadMovies() {
        // The parameters of testing
        String filename = "StepOneStarterProgram/data/ratedmoviesfull.csv";
        String genreName = "Comedy";
        int movieLength = 150;
        int movieLengthCount = 0;

        ArrayList<Movie> movieList = loadMovies(filename);
        System.out.println("Total " + movieList.size() + " movies");

        HashMap<String, Integer> genreMap = new HashMap<>();
        HashMap<String, Integer> directorMap = new HashMap<>();

        for (Movie movie : movieList) {
            countGenres(genreMap, movie.getGenres());
            countDirectors(directorMap, movie.getDirector());
            if (movie.getMinutes() > movieLength) movieLengthCount++;
            movie.toString();
        }

        // Genre
        int genreCount = genreMap.get(genreName);
        System.out.println("Movies of " + genreName + ": " + genreCount);
        // Length
        System.out.println("Movies greater then " + movieLength + ": " + movieLengthCount);

        // Directors
        int maxMovies = -1;
        System.out.println("\nAll directors:");

        for (Map.Entry<String, Integer> director: directorMap.entrySet())
        {
            if (maxMovies < director.getValue()) maxMovies = director.getValue();
            System.out.println(director.getKey() + " has " + director.getValue() + " movies");
        }

        int countMaxMovies = 0;
        System.out.println("\nDirectors with max num of movies:");
        for (Map.Entry<String, Integer> director: directorMap.entrySet()) {
            if (director.getValue() == maxMovies) {
                System.out.println(director.getKey() + " has " + director.getValue() + " movies");
                countMaxMovies++;
            }
        }
        System.out.println(countMaxMovies + " with max num of movies");

    }

    public ArrayList<Rater> loadRaters(String filename) {
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();

        ArrayList<Rater> raterList = new ArrayList<>();

        for (CSVRecord record : parser) {
            String raterID = record.get("rater_id");
            String movieID = record.get("movie_id");
            double ratingValue = Double.valueOf(record.get("rating"));
            String time = record.get("time");

            boolean isRaterInList = false;
            for (Rater rater : raterList) {
                if (rater.getID().equals(raterID)) {
                    rater.addRating(movieID, ratingValue);
                    isRaterInList = true;
                    break;
                }
            }

            if (!isRaterInList) {
                Rater rater = new Rater(raterID);
                rater.addRating(movieID, ratingValue);
                raterList.add(rater);
            }

        }
        return raterList;
    }

    public void testLoadRaters() {
        // The parameters of testing
        String filename = "StepOneStarterProgram/data/ratings.csv";
        String specificRaterID = "193";

        ArrayList<Rater> raterList = loadRaters(filename);
        System.out.println("Total " + raterList.size() + " raters");

        // Total statistic
        for (Rater rater : raterList) {
            if (specificRaterID.isEmpty() || specificRaterID.equals(rater.getID())) {
                System.out.println(rater.getID() + ": " + rater.numRatings() + " ratings");
            }
        }

        // Max ratings
        int maxRatings = -1;
        System.out.println("\nAll raters:");
        for (Rater rater : raterList) {
            if (rater.numRatings() > maxRatings) maxRatings = rater.numRatings();
            System.out.println(rater.getID() + " has " + rater.numRatings() + " ratings");
        }

        int countMaxRatings = 0;
        System.out.println("\nRaters with max num of ratings:");
        for (Rater rater : raterList) {
            if (rater.numRatings() == maxRatings) {
                System.out.println(rater.getID() + " has " + rater.numRatings() + " ratings");
                countMaxRatings++;
            }
        }
        System.out.println(countMaxRatings + " with max num of ratings");

        // Statistics about movies ratings
        /**
         * Add code to determine how many different movies have been rated by all these raters.
         * If you run this code on the file ratings_short.csv, you will see there were four movies rated.
         */
        HashMap <String, Integer> movieByRaters = new HashMap<>();
        for (Rater rater : raterList) {
            for (String movieID : rater.getItemsRated()) {
                if (!movieByRaters.containsKey(movieID)) {
                    movieByRaters.put(movieID, 1);
                } else {
                    movieByRaters.put(movieID, movieByRaters.get(movieID) + 1);
                }

            }
        }
        System.out.println("Total rated movies: " + movieByRaters.size());

        /**
         * Add code to find the number of ratings a particular movie has.
         * If you run this code on the file ratings_short.csv for the movie “1798709”,
         * you will see it was rated by four raters.
         */
        String movieID = "1798709";
        System.out.println("Movie " + movieID + " rated by " + movieByRaters.get(movieID) + " raters");
    }

}
