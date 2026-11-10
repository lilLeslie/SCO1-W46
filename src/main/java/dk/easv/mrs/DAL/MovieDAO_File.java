package dk.easv.mrs.DAL;

// Java imports

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Project imports
import dk.easv.mrs.BE.Movie;

public class MovieDAO_File implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override
    public List<Movie> getAllMovies() throws IOException {
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(MOVIES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    movies.add(new Movie(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return movies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        Movie movie = new Movie(getAvailableId(), year, title);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MOVIES_FILE, true))) {
            bw.newLine();
            bw.write(toCsv(movie));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return movie;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
    }

    private int getAvailableId() throws IOException {
        return getAllMovies().size();
    }

    private String toCsv(Movie movie){
        return movie.getId()+","+movie.getYear()+","+movie.getTitle();
    }
}