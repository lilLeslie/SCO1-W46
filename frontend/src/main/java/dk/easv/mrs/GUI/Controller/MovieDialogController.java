package dk.easv.mrs.GUI.Controller;

import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MovieDialogController implements Initializable {

    public Button btnAction;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtYear;

    private MovieModel movieModel;
    private Movie movie;

    public void setMovieModel(MovieModel movieModel) {
        this.movieModel = movieModel;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;

        if (this.movie != null) {
            txtTitle.setText(movie.getTitle());
            txtYear.setText(String.valueOf(movie.getYear()));

            btnAction.setText("Update");
            btnAction.setOnAction(event -> {
                try {
                    onUpdateMovieClicked(event);
                } catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAction.setText("Add Movie");
        btnAction.setOnAction(event -> {
            try {
                onAddMovieClicked(event);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void onAddMovieClicked(ActionEvent actionEvent) throws Exception {
        if (isInputValid()) {
            boolean movieSaved = movieModel.createMovie(txtTitle.getText(), Integer.parseInt(txtYear.getText()));
            if (movieSaved) {
                closeWindow(actionEvent);
            }
        }
    }

    public void onUpdateMovieClicked(ActionEvent actionEvent) throws Exception {
        if (isInputValid() && movie != null) {
            movie.setTitle(txtTitle.getText());
            movie.setYear(Integer.parseInt(txtYear.getText()));
            movieModel.updateMovie(movie);
            closeWindow(actionEvent);
        }
    }

    private boolean isInputValid() {
        return !txtTitle.getText().isBlank()
                && !txtYear.getText().isBlank()
                && !txtYear.getText().matches("^[0-9]*$\n");
    }

    private void closeWindow(ActionEvent actionEvent) {
        Node n = (Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
}
