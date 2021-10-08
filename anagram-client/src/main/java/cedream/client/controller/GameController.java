package cedream.client.controller;

import cedream.data.data.DataAnagram;
import cedream.data.data.DataWordFound;
import cedream.data.data.Type;
import cedream.data.words.Word;
import cedream.client.model.AnagramClient;
import cedream.client.view.AlertView;
import cedream.data.users.User;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Controller of the game.
 *
 * @author Cedric Thonus
 */
public class GameController extends BorderPane implements Observer, Initializable {

    private final Router router;
    private final AnagramClient client;

    @FXML
    private ListView members, wordsFound;

    @FXML
    private Label word, nbPropositions, indice;

    @FXML
    private TextField proposition;

    @FXML
    private GridPane form;

    @FXML
    private ProgressIndicator progress;

    @FXML
    private Button disconnectBtn, indiceBtn;

    /**
     * Initialize route, client and load fxml file and set up his controller and
     * root.
     *
     * @param router object that manages the routes.
     * @param client AnagramClient object.
     */
    public GameController(Router router, AnagramClient client) {
        this.router = router;
        this.client = client;
        client.addObserver(this);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/Game.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
        }
    }

    /**
     * Check if the proposition respect a regex and send this to the server.
     */
    @FXML
    public void doAProposition() {
        String str = proposition.getText();
        if (!str.matches("^[a-zA-Z-]+$")) {
            AlertView.showAlert("Votre proposition peut contenir des majuscules "
                    + "et minuscules ainsi que tirets");
            return;
        }
        lockForm();
        try {
            client.sendToServer(new DataAnagram(proposition.getText(), Type.PROPOSITION, client.getMySelf()));
        } catch (IOException ex) {
            AlertView.showAlert("Erreur d'envoi au serveur"
                    + " (" + ex.getMessage() + ")");
            resetForm();
        }
    }

    /**
     * The client ask a indice to server.
     */
    @FXML
    public void indice() {
        lockForm();
        indiceBtn.setDisable(true);
        try {
            client.sendToServer(new DataAnagram(null, Type.ASKINDICE, client.getMySelf()));
        } catch (IOException ex) {
            AlertView.showAlert("Erreur d'envoi au serveur"
                    + " (" + ex.getMessage() + ")");
            resetForm();
            indiceBtn.setDisable(false);
        }
    }

    /**
     * Send to the server the request to pass
     */
    @FXML
    public void pass() {
        try {
            lockForm();
            client.sendToServer(new DataAnagram(null, Type.PASS, client.getMySelf()));
        } catch (IOException ex) {
            AlertView.showAlert("Erreur d'envoi au serveur"
                    + " (" + ex.getMessage() + ")");
            resetForm();
        }
    }

    /**
     * Disconnect client and quit application.
     */
    @FXML
    public void disconnect() {
        try {
            client.quit();
            router.close();
        } catch (IOException ex) {
            AlertView.showAlert(ex.getMessage());
        }

    }

    /**
     * Disable form and show loading element.
     */
    private void lockForm() {
        form.setDisable(true);
        progress.setVisible(true);
    }

    /**
     * Enable form and hide loading element and set proposition field to empty.
     */
    private void resetForm() {
        form.setDisable(false);
        progress.setVisible(false);
        proposition.setText("");
    }

    /**
     * 
     * @param arg DataAnagram object
     */
    private void dataAnagram(Object arg) {
        DataAnagram dataAnagram = (DataAnagram) arg;

        switch (dataAnagram.getType()) {
            case WORD:
                word.setText(dataAnagram.getContent().toString());
                indiceBtn.setDisable(false);
                indice.setText("Pas d'indice");
                break;
            case BADWORD:
                AlertView.showAlert("La proposition que vous avez faites n'est pas bonne");
                break;
            case REVEALWORD:
                AlertView.showAlert("Le mot a trouvé était "
                        + dataAnagram.getContent().toString());
                break;
            case INDICE:
                AlertView.showAlert("L'indice est : "
                        + dataAnagram.getContent().toString());
                indice.setText(dataAnagram.getContent().toString());
                break;
            case ENDGAME:
                try {
                    AlertView.showAlert("Partie terminée ! Tu as trouvé "
                            + client.getMySelf().getNbWordsFound() + " mots");
                    client.quit();
                    router.switchView(Route.END);
                } catch (IOException ex) {
                    AlertView.showAlert(ex.getMessage());
                }
                break;
            default:
        }

        nbPropositions.setText(((Word) dataAnagram.getContent()).getNbPropositons() + "");
        resetForm();
    }

    /**
     * 
     * @param arg DataWordFound object
     */
    private void dataWordFound(Object arg) {
        DataWordFound wordFound = (DataWordFound) arg;
        if (wordFound.getType() == Type.WORDFOUND) {
            String author = wordFound.getAuthor().getName();
            String word = wordFound.getContent().toString();
            wordsFound.getItems().add(author + " a trouvé " + word);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            members.getItems().clear();
            for (User u : client.getMembers()) {
                members.getItems().add(u.getName() + " (" + u.getNbWordsFound()
                        + " mots trouvés)");
            }

            if (arg != null && arg instanceof DataAnagram) {
                dataAnagram(arg);
            }

            if (arg != null && arg instanceof DataWordFound) {
                dataWordFound(arg);
            }

        });
    }

    /**
     * Initialize disconnect button with the name of client.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disconnectBtn.setText("Se deconnecter (" + client.getMySelf().getName() + ")");
    }

}
