package cedream.client;

import cedream.client.controller.Router;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class. Create default route.
 * @author Cedric Thonus
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Router router = new Router(primaryStage);
        router.run();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
