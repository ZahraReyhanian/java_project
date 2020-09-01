package projectt;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MessageController implements Initializable {

    private boolean isServer;
    private String name;
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    
    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mainMenu = Loader.getController();
        mainMenu.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @FXML
    private void goStartChat(ActionEvent event) throws Exception{
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("EnterIDView.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        EnterIDViewController EIV = Loader.getController();
        EIV.setText(id,name);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

}
