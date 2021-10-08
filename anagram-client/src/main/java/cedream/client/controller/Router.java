package cedream.client.controller;

import static cedream.client.controller.Route.START;
import cedream.client.model.AnagramClient;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Manages routes of application.
 *
 * @author Cedric Thonus
 */
public class Router {

    private final Stage stage;
    private final Route DEFAULT = START;

    /**
     * Initialize stage.
     *
     * @param stage Main stage of application
     */
    public Router(Stage stage) {
        this.stage = stage;
    }

    /**
     * Run router.
     */
    public void run() {
        if (stage.isShowing()) {
            return;
        }
        switchView(DEFAULT);
        stage.show();
    }

    /**
     * Call <code> switchView(Route route, Object obj) </code>.
     *
     * @param route
     */
    public void switchView(Route route) {
        switchView(route, null);
    }

    /**
     * Switch to another route.
     *
     * @param route new route.
     * @param obj a object if we want communicate something.
     */
    public void switchView(Route route, Object obj) {
        stage.setTitle(route.getTitle());
        Parent root = null;
        switch (route) {
            case START:
                root = startView();
                break;
            case GAME:
                root = gameView((AnagramClient) obj);
                break;
            case END:
                root = endView();
                break;
            default:
                root = startView();
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    /**
     * Call start controller and returns.
     *
     * @return controller of start view.
     */
    private Parent startView() {
        StartController controller = new StartController(this);
        return controller;
    }

    /**
     * Call game controller and returns.
     *
     * @return controller of game view.
     */
    private Parent gameView(AnagramClient client) {
        GameController controller = new GameController(this, client);
        return controller;
    }

    /**
     * Call end controller and returns.
     *
     * @return controller of end game view.
     */
    private Parent endView() {
        EndGameController controller = new EndGameController(this);
        return controller;
    }

    /**
     * Close application.
     */
    public void close() {
        stage.close();
    }

}
