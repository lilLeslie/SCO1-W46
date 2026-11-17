package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    private UserModel userModel;

    public SignUpController(){
        try {
            userModel = new UserModel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCreateUserClicked(ActionEvent actionEvent) {
        if (isValid()){
            User savedUser = userModel.createUser(txtUsername.getText(), txtPassword.getText());
            if (savedUser != null){
                Node node = (Node) actionEvent.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        }
    }

    private boolean isValid(){
        return !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }
}
