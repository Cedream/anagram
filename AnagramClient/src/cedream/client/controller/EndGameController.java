package cedream.client.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 * Controller of end game view.
 *
 * @author Cedric Thonus
 */
public class EndGameController extends BorderPane {

    private final Router router;

    /**
     * Initialize route and load fxml file and set up his controller and root.
     *
     * @param router object that manages the routes.
     */
    public EndGameController(Router router) {
        this.router = router;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/fxml/End.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
        }
    }

    /**
     * Switch to route Start.
     */
    @FXML
    public void reconnect() {
        router.switchView(Route.START);
    }

    /**
     * Quit application.
     */
    @FXML
    public void quit() {
        router.close();
    }

}
