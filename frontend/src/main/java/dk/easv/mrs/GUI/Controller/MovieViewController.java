package dk.easv.mrs.GUI.Controller;

// Java imports

import dk.easv.mrs.GUI.View.MovieListCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public ListView<Movie> lstMovies;
    private MovieModel movieModel;
    private Movie selected;

    public MovieViewController() {
        try {
            movieModel = new MovieModel();
        } catch (Exception e) {
            displayError(e);
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lstMovies.setItems(movieModel.getObservableMovies());
        lstMovies.setCellFactory(lv -> new MovieListCell());
        txtMovieSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                movieModel.searchMovie(newValue);
            } catch (Exception e) {
                displayError(e);
                e.printStackTrace();
            }
        });
        lstMovies.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2){
                    selected = lstMovies.getSelectionModel().getSelectedItem();
                    try {
                        openMovieDialog();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

    }

    private void displayError(Throwable t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Something went wrong");
        alert.setHeaderText(t.getMessage());
        alert.showAndWait();
    }

    public void onAddMovieClicked(ActionEvent actionEvent) throws IOException {
        openMovieDialog();
    }

    private void openMovieDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MovieDialogView.fxml"));
        Parent root = loader.load();
        MovieDialogController controller = loader.getController();
        controller.setMovieModel(movieModel);
        controller.setMovie(selected != null ? selected : null);
        Stage stage = new Stage();
        stage.setTitle("Movie Dialog");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(event -> selected = null);
        stage.show();
    }

    public void onDeleteClicked(ActionEvent actionEvent) throws Exception {
        if (lstMovies.getSelectionModel().getSelectedItem() != null){
            Movie movie = lstMovies.getSelectionModel().getSelectedItem();
            movieModel.deleteMovie(movie);
        }
    }
}
