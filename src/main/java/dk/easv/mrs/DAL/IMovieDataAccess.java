package dk.easv.mrs.DAL;

// Java imports
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public interface IMovieDataAccess {

    public List<Movie> getAllMovies() throws Exception;

    public Movie createMovie(String title, int year) throws Exception;

    public void updateMovie(Movie movie) throws Exception;

    public void deleteMovie(Movie movie) throws Exception;

}
