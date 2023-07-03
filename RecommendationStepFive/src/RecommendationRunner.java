import java.util.ArrayList;
import java.util.Collections;

public class RecommendationRunner implements Recommender {

    public ArrayList<String> getItemsToRate () {

        String genre = "Comedy,Drama";
        int countMoviesToRate = 15;

        ArrayList<String> itemsList = new ArrayList<>();

        for (String movie : MovieDatabase.filterBy(new GenreFilter(genre))) {
            itemsList.add(movie);
            if (itemsList.size() >= countMoviesToRate) break;
        }

        return itemsList;
    }



    public void printRecommendationsFor (String webRaterID) {
        String raterID = webRaterID;
        int minCountRaters = 5;
        int topSimilarRaters = 20;

        FourthRatings fourthRatings = new FourthRatings();
        int countMoviesToShow = 15;
        ArrayList<Rating> movieList = fourthRatings.getSimilarRatings(raterID, topSimilarRaters, minCountRaters);
        if (movieList.size() == 0) System.out.println("NO RECOMMENDATIONS");
        int idx = 0;
        System.out.println("<!DOCTYPE html>");
        System.out.println("<html>");
        System.out.println("<head>");
        System.out.println("<meta charset=\"utf-8\">");
        System.out.println("<title>Тег table</title>");
        System.out.println("</head>");
        System.out.println("<body>");
        System.out.println("<table border=\"1\">");
        for (Rating rating : movieList) {
            if (idx >= countMoviesToShow) break;
            System.out.println("<tr>");
            System.out.println("<th>" + MovieDatabase.getMovie(rating.getItem()).getTitle() + "</th>");
            System.out.println("<th>" + rating.getValue() + "</th>");
            System.out.println("</tr>");
            idx++;
        }
        System.out.println("</table>");
        System.out.println("</body>");
        System.out.println("</html>");
    }
}
