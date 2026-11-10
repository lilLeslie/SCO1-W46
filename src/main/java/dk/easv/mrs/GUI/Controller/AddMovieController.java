package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddMovieController {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtYear;

    private MovieModel movieModel;

    public void setMovieModel(MovieModel movieModel){
        this.movieModel = movieModel;
    }

    public void onAddMovieClicked(ActionEvent actionEvent) throws Exception {
        if (isInputValid()){
            boolean movieSaved = movieModel.createMovie(txtTitle.getText(), Integer.parseInt(txtYear.getText()));
            if (movieSaved){
                closeWindow(actionEvent);
            }
        }
    }

    private boolean isInputValid(){
        return !txtTitle.getText().isBlank() && !txtYear.getText().isBlank() && txtYear.getText().matches("^\\d+(\\d+)?");
    }

    private void closeWindow(ActionEvent actionEvent){
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
