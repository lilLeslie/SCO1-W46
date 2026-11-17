package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.Model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    @FXML private Label lblTitle;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private VBox vbConfirmPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Label lblError;
    @FXML private Button btnSubmit;
    @FXML private Label lblSwitchPrompt;
    @FXML private Hyperlink linkSwitchMode;

    private UserModel userModel;
    private boolean isSignUpMode = false;

    public AuthController() {
        try {
            userModel = new UserModel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSwitchMode() {
        isSignUpMode = !isSignUpMode;

        // Toggle Confirm Password field visibility & layout space
        vbConfirmPassword.setVisible(isSignUpMode);
        txtConfirmPassword.setVisible(isSignUpMode);
        txtConfirmPassword.setManaged(isSignUpMode);
        txtConfirmPassword.clear();
        hideError();

        // Update labels and button text
        if (isSignUpMode) {
            lblTitle.setText("Create Account");
            btnSubmit.setText("Sign Up");
            lblSwitchPrompt.setText("Already have an account?");
            linkSwitchMode.setText("Sign In");
        } else {
            lblTitle.setText("Sign In");
            btnSubmit.setText("Sign In");
            lblSwitchPrompt.setText("Don't have an account?");
            linkSwitchMode.setText("Sign Up");
        }
    }

    @FXML
    private void handleSubmit() {
        hideError();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        try {
            if (isSignUpMode) {
                String confirm = txtConfirmPassword.getText();
                if (!password.equals(confirm)) {
                    showError("Passwords do not match.");
                    return;
                }
                User createdUser = userModel.createUser(username, password);
                openMainView(createdUser);
            } else {
                User loggedInUser = userModel.login(username, password);
                if (loggedInUser != null) {
                    openMainView(loggedInUser);
                } else {
                    showError("Invalid username or password.");
                }
            }
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    private void openMainView(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MovieView.fxml"));
        Parent root = loader.load();
        MovieViewController controller = loader.getController();
        controller.setUser(user);
        Stage stage = new Stage();
        stage.setTitle(user.getUsername());
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    private void showError(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
        lblError.setManaged(true);
    }

    private void hideError() {
        lblError.setVisible(false);
        lblError.setManaged(false);
    }
}