public class MinutesFilter implements Filter {

    private int myMinMinutes;
    private int myMaxMinutes;

    public MinutesFilter(int minMinutes) {
        myMinMinutes = minMinutes;
        myMaxMinutes = Integer.MAX_VALUE;
    }

    public MinutesFilter(int minMinutes, int maxMinutes) {
        myMinMinutes = 0;
        myMaxMinutes = maxMinutes;
    }

    @Override
    public boolean satisfies(String id) {
        return MovieDatabase.getMinutes(id) >= myMinMinutes && MovieDatabase.getMinutes(id) <= myMaxMinutes;
    }
}
