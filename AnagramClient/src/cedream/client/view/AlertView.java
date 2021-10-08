package cedream.client.view;

import javafx.scene.control.Alert;

/**
 *
 * @author Cedric Thonus
 */
public class AlertView {

    public static void showAlert(String str) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informations");
        alert.setHeaderText(null);
        alert.setContentText(str);
        alert.showAndWait();
    }
    
}
