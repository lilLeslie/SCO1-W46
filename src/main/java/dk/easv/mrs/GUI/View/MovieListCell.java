package dk.easv.mrs.GUI.View;

import dk.easv.mrs.BE.Movie;
import javafx.scene.control.ListCell;

public class MovieListCell extends ListCell<Movie> {
    @Override
    protected void updateItem(Movie movie, boolean empty){
        super.updateItem(movie, empty);
        setText(movie == null ? "" : movie.toString());
    }
}
