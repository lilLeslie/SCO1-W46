package dk.easv.mrs.DAL;

// Java imports
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public interface IMovieDataAccess {

    List<Movie> getAllMovies() throws Exception;

    Movie createMovie(Movie movie) throws Exception;

    void updateMovie(Movie movie) throws Exception;

    void deleteMovie(Movie movie) throws Exception;

}
