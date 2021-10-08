package cedream.client.controller;

import cedream.client.model.AnagramClient;
import cedream.client.view.AlertView;
import java.io.IOException;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 * Controller of start view.
 *
 * @author Cedric Thonus
 */
public class StartController extends BorderPane {

    private final Router router;
    Service<Void> threadFx;

    @FXML
    private TextField usernameLb, ipLb, portLb;

    @FXML
    private Button confirmBtn;

    /**
     * Initialize route and load fxml file and set up his controller and root.
     *
     * @param router object that manages the routes.
     */
    public StartController(Router router) {
        this.router = router;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/fxml/Start.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
        }
    }

    /**
     * Check if username, port are correct and try to connect to server.
     */
    @FXML
    public void handleConfirm() {
        confirmBtn.setDisable(true);
        String usernameText = usernameLb.getText();
        try {
            if (usernameText.length() < 3 || usernameText.length() > 12) {
                throw new Exception("Le longueur du nom d'utilisateur doit être "
                        + "compris entre 3 et 12 compris");
            }
            if (!usernameText.matches("^[A-Za-z0-9._-]+$")) {
                throw new Exception("Le nom d'utilisateur ne peut comporter que des "
                        + "caractères alphanumériques ainsi que . - et _");
            }
            if (!portLb.getText().matches("^[0-9]+$")) {
                throw new Exception("Le port doit être numérique");
            }
            connectToServer(usernameText, ipLb.getText(), Integer.parseInt(portLb.getText()));
        } catch (Exception ex) {
            AlertView.showAlert(ex.getMessage());
            confirmBtn.setDisable(false);
        }
    }

    /**
     * Disable confirm button.
     */
    private void modeConnection() {
        confirmBtn.setDisable(true);
    }

    /**
     * Enable confirm button.
     */
    private void modeConfiguration() {
        confirmBtn.setDisable(false);
    }

    /**
     * Try to connect to the server. Try to connect to the server. The
     * connection test is done on another fx thread for the GUI to always
     * answer.
     *
     * @param username username of client.
     * @param ip ip of server.
     * @param port port of server.
     */
    private void connectToServer(String username, String ip, int port) {
        modeConnection();
        /*Run script who try to connect on a server in a worker fx for 
             that the main thread javafx can always response.*/
        threadFx = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        try {
                            AnagramClient client = new AnagramClient(username, ip, port);
                            Platform.runLater(() -> router.switchView(Route.GAME, client));
                        } catch (IllegalArgumentException ex) {
                            System.out.println("salut");
                        } catch (UnknownHostException ex) {
                            Platform.runLater(() -> {
                                AlertView.showAlert("Aucun serveur trouvé portant cette ip");
                            });
                        } catch (IOException ex) {
                            Platform.runLater(() -> {
                                AlertView.showAlert("Aucun serveur à l'écoute sur ce port");
                            });
                        } finally {
                            modeConfiguration();
                        }
                        return null;
                    }
                };
            }
        };
        threadFx.start();

    }

}
