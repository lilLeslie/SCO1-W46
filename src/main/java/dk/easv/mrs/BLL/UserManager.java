package dk.easv.mrs.BLL;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.DAL.UserDAO;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;

public class UserManager {
    private UserDAO userDAO;

    public UserManager() throws IOException {
        userDAO = new UserDAO();
    }

    public User createUser(String username, String rawPassword){
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt(5));
        return userDAO.createUser(new User(username, hashedPassword));
    }
}
