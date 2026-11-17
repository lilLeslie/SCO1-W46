package dk.easv.mrs.DAL;

import dk.easv.mrs.BE.User;

import java.io.IOException;
import java.sql.*;

public class UserDAO {
    private DbConnection dbConnection = new DbConnection();

    public UserDAO() throws IOException {
    }

    public User createUser(User user) {
        String sql = "INSERT INTO dbo.Users(UserName, Password) VALUES(?,?)";
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    return new User(
                            generatedId,
                            user.getUsername(),
                            user.getPassword()
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
