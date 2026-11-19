package dk.easv.mrs.GUI.Controller;

// Java imports

import dk.easv.mrs.BE.User;
import dk.easv.mrs.GUI.View.MovieListCell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Project imports
import dk.easv.mrs.BE.Movie;
import dk.easv.mrs.GUI.Model.MovieModel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieViewController implements Initializable {


    public TextField txtMovieSearch;
    public TableView<Movie> tblMovies;
    private MovieModel movieModel;
    private Movie selected;
    private User user;

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
        loadTableView();
        tblMovies.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    selected = tblMovies.getSelectionModel().getSelectedItem();
                    try {
                        openMovieDialog();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void setUser(User user){
        this.user = user;
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

    private void loadTableView(){
        TableColumn titleCol = new TableColumn("Title");
        TableColumn yearCol = new TableColumn("year");
        titleCol.setCellValueFactory(
                new PropertyValueFactory<Movie, String>("title")
        );
        yearCol.setCellValueFactory(
                new PropertyValueFactory<Movie, Integer>("year")
        );
        tblMovies.getColumns().addAll(titleCol, yearCol);
        tblMovies.setItems(movieModel.getObservableMovies());
    }

    public void onDeleteClicked(ActionEvent actionEvent) throws Exception {
        if (tblMovies.getSelectionModel().getSelectedItem() != null) {
            Movie movie = tblMovies.getSelectionModel().getSelectedItem();
            movieModel.deleteMovie(movie);
        }
    }
}
