import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class FirstRatings {

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

}
