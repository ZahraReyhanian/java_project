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
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ServerController implements Initializable {

    private int id;
    private String name;
    private int idC;
    private String nameC;
    private NetworkConnection connection;
    @FXML
    private TextArea messages;
    @FXML
    private TextArea input;

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
            System.out.println(e);
            messages.appendText("Failed to send\n");

        }
    }

    public void setText(int id, String name) {
        this.id = id;
        this.name = name;
        System.out.println(name);
        try {
            connection = createServer();
            connection.startConnection();
        } catch (Exception ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private NetworkConnection createServer() {
        return new Server("127.0.0.1", 55555, data -> {
            Platform.runLater(() -> {
                messages.appendText(data.toString() + "\n");
            });
        });
    }

    @FXML
    private void reqButton(ActionEvent event) {

        String message = name + " : request00112233,..!!:))))))";
        input.clear();
        messages.appendText(message + "\n");
        try {
            connection.send(message + "\n");
        } catch (Exception e) {
            System.out.println(e);
            messages.appendText("Failed to send\n");

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
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController menu = Loader.getController();

        menu.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id, String name, int idC, String nameC) {
        this.id = id;
        this.idC = idC;
        this.name = name;
        this.name = name;
        
    }

    

}
