package dk.easv.mrs.BLL.util;

// Java imports
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieSearcher {

    /**
     * Searches the provided list of movies for movies matching the specified query.
     * @param searchBase the list of movies to search through
     * @param query the search query to match against movie titles and release years
     * @return a list of movies whose titles or release years contain {@code query};
     * an empty list if no matches are found.
     */
    public List<Movie> search(List<Movie> searchBase, String query) {
        List<Movie> searchResult = new ArrayList<>();

        for (Movie movie : searchBase) {
            if(compareToMovieTitle(query, movie) || compareToMovieYear(query, movie))
            {
                searchResult.add(movie);
            }
        }

        return searchResult;
    }

    /**
     * Checks whether the movie's release year contains the search query.
     * @param query the search query to match against the movie's release year
     * @param movie the movie whose release year is being compared
     * @return {@code true} if the movie's release year contains the query;
     * {@code false} otherwise
     */
    private boolean compareToMovieYear(String query, Movie movie) {
        return Integer.toString(movie.getYear()).contains(query);
    }

    /**
     * Checks whether the movie's title contains the search query.
     * @param query the search query to match against the movie's title
     * @param movie the movie whose title is beign compared
     * @return {@code true} if the movie's title contains the query;
     * {@code false} otherwise
     */
    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
