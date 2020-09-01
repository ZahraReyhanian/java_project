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

public class AddMenuController implements Initializable {

    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void newHW(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("InsertHW.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        InsertHWController insertHW = Loader.getController();
        insertHW.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void returnb(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("mainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mainMenu = Loader.getController();
        mainMenu.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void newExam(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("InsertExamm.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        InsertExammController insertE = Loader.getController();
        insertE.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void newClass(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("InsertClass.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        InsertClassController creatClass = Loader.getController();
        creatClass.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id) {
        this.id = id;
    }

}
