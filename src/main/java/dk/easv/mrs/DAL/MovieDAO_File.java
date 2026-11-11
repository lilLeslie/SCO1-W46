package dk.easv.mrs.DAL;

// Java imports

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// Project imports
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dk.easv.mrs.BE.Movie;

public class MovieDAO_File implements IMovieDataAccess {
    private DbConnection dbConnection = new DbConnection();

    private static final String MOVIES_FILE = "data/movie_titles.txt";

    public MovieDAO_File() throws IOException {
    }

    //The @Override annotation is not required, but is recommended for readability
    // and to force the compiler to check and generate error msg. if needed etc.
    //@Override
    public List<Movie> getAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = dbConnection.getConnection();
             Statement stmt = connection.createStatement()) {

            String sql = "SELECT * FROM dbo.Movie";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int year = rs.getInt(3);
                movies.add(new Movie(id, year, title));
            }
            return movies;

        } catch (SQLException e){
            e.printStackTrace();
            throw new Exception("Could not get movies: ", e);
        }
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        Movie movie = new Movie(getAvailableId(), year, title);
        Files.write(Path.of(MOVIES_FILE), toCsv(movie).getBytes(), StandardOpenOption.APPEND);
        return movie;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

        Path sourcePath = Path.of(String.valueOf(MOVIES_FILE));
        Path tempPath = Files.createTempFile(sourcePath.getParent(), "_temp", ".txt");

        try (BufferedReader br = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(tempPath, StandardCharsets.UTF_8)) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (!firstLine) {
                    bw.newLine();
                }

                int id = Integer.parseInt(line.split(",")[0]);
                if (id == movie.getId()) {
                    bw.write(toCsv(movie));
                } else {
                    bw.write(line);
                }
                firstLine = false;
            }

            Files.move(tempPath, sourcePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        Path sourcePath = Path.of(String.valueOf(MOVIES_FILE));
        Path tempPath = Files.createTempFile(sourcePath.getParent(), "_temp", ".txt");

        try (BufferedReader br = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8);
             BufferedWriter bw = Files.newBufferedWriter(tempPath, StandardCharsets.UTF_8)) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                int id = Integer.parseInt(line.split(",")[0]);
                if (id == movie.getId()) continue;
                if (!firstLine) bw.newLine();
                bw.write(line);
                firstLine = false;
            }

            Files.move(tempPath, sourcePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private int getAvailableId() throws Exception {
        return getAllMovies().size();
    }

    private String toCsv(Movie movie) {
        return movie.getId() + "," + movie.getYear() + "," + movie.getTitle();
    }
}