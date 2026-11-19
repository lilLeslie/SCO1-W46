package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.Model.Page;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserDashboardController implements Initializable {
    @FXML
    private Label lblUsername;
    @FXML
    private BorderPane borderPane;
    private User user;
    private Page currentPage;

    public UserDashboardController() {
        currentPage = Page.DISCOVER;
    }

    public void setUser(User user) {
        this.user = user;
        lblUsername.setText(user.getUsername());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCenter(currentPage.fxml);
    }

    private void setCenter(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/"+fxmlName));
            Parent root = loader.load();
            borderPane.setCenter(root);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
