package dk.easv.mrs.BLL.util;

// Java imports
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieSearcher {


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

    private boolean compareToMovieYear(String query, Movie movie) {
        return Integer.toString(movie.getYear()).contains(query);
    }

    private boolean compareToMovieTitle(String query, Movie movie) {
        return movie.getTitle().toLowerCase().contains(query.toLowerCase());
    }

}
