package dk.easv.mrs.GUI.Model;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.BLL.UserManager;

import java.io.IOException;

public class UserModel {
    UserManager userManager;

    public UserModel() throws IOException {
        userManager = new UserManager();
    }

    public User createUser(String username, String password){
        return userManager.createUser(username, password);
    }

    public User login(String username, String password){
        return userManager.getUser(username, password);
    }
}
