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

    public EndGameController(Router router) {
        this.router = router;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/End.fxml"));
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
