import java.util.Arrays;

public class DirectorsFilter implements Filter {
    private String[] myDirectors;

    public DirectorsFilter(String directors) {
        myDirectors = directors.split(",");
    }

    @Override
    public boolean satisfies(String id) {
        for (String director : myDirectors) {
            if (MovieDatabase.getDirector(id).contains(director.trim())) return true;
        }
        return false;
    }

}
