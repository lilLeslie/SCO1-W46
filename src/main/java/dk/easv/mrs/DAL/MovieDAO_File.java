package dk.easv.mrs.DAL;

// Java imports
import java.io.*;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override
    public List<Movie> getAllMovies() throws IOException {
        return null;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
    }
}