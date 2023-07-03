public class GenreFilter implements Filter {
    private String[] myGenres;

    public GenreFilter(String genres) {
        myGenres = genres.split(",");

    }

    @Override
    public boolean satisfies(String id) {
        for (String genre : myGenres) {
            if (MovieDatabase.getGenres(id).contains(genre.trim())) return true;
        }
        return false;
    }

}
