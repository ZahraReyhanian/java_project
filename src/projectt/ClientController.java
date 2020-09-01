
package projectt;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class ClientController implements Initializable {

    private int id;
    private String name;
    private NetworkConnection connection;
    private boolean req = false;
    @FXML
    private TextArea messages;
    @FXML
    private TextArea input;
    @FXML
    private Button exceptButton;
    @FXML
    private Button egnorButton;
    @FXML
    private Button sendButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void sendButton(ActionEvent event) {
        String message = name + " :" + input.getText();
        input.clear();
        messages.appendText(message + "\n");
        try {
            connection.send(message + "\n");
        } catch (Exception e) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e);
            messages.appendText("Failed to send\n");
        }
    }

    public void setText(int id, String name) {
        this.name = name;
        this.id = id;
        try {
            connection = createClient();
            connection.startConnection();
        } catch (Exception ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private NetworkConnection createClient() {
        return new Clients("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
                req = false;
                messages.appendText(data.toString() + "\n");
                if (data.toString().contains(" : request00112233,..!!:))))))")) {
                    req = true;
                }
            });
        });
    }

    /////////stop//////////
    @FXML
    private void exceptButton(ActionEvent event) {
        if (req == true) {
            req=false;
            String message = name + " :" + "except your request";
            input.clear();
            messages.appendText(message + "\n");
            try {
                connection.send(message + "\n");
            } catch (Exception e) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
                System.out.println(e);
                messages.appendText("Failed to send\n");

            }
            
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("RequestView.fxml"));

            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            RequestViewController addRequestProject = Loader.getController();
            addRequestProject.setText(id, name);

            Parent p = Loader.getRoot();
            /*
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(p));
            window.show();*/
            
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.showAndWait();

        } else {
            JOptionPane.showMessageDialog(null, "you don't have request");
        }
    }

    @FXML
    private void egnorButton(ActionEvent event) {
        if (req == true) {
            req = false;
            String message = name + " :" + "dosen't except your request";
            input.clear();
            messages.appendText(message + "\n");
            try {
                connection.send(message + "\n");
            } catch (Exception e) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, e);
                System.out.println(e);
                messages.appendText("Failed to send\n");

            }

        } else {
            JOptionPane.showMessageDialog(null, "you don't have request");
        }
    }

    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, IOException, Exception {
        connection.closeConnection();
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController menu = Loader.getController();

        menu.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

}
