package dk.easv.mrs.DAL;

// Java imports

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.*;
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

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                int year = rs.getInt(3);
                movies.add(new Movie(id, year, title));
            }
            return movies;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Could not get movies: ", e);
        }
    }

    @Override
    public Movie createMovie(Movie movie) throws Exception {
        String sql = "INSERT INTO dbo.Movie(Title, Year) VALUES (?, ?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getYear());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    return new Movie(
                            generatedId,
                            movie.getYear(),
                            movie.getTitle()
                    );
                }
            }
        } catch (SQLException e) {
            throw new Exception("Could not add movie to db: ", e);
        }
        return movie;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
        String sql = "UPDATE dbo.Movie SET Title = ?, Year = ? WHERE Id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getYear());
            ps.setInt(3, movie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Could not update movie: ", e);
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {
        String sql = "DELETE FROM dbo.Movie WHERE Id = ?";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, movie.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Could not delete movie: ", e);
        }
    }
}