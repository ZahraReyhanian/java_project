package projectt;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;

    private User user;
    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void exit_login(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void mainMenu(ActionEvent event) throws IOException, SQLException {
        PreparedStatement ps;
        ResultSet rs;
        String uname = userName.getText();
        String pass = password.getText();

        String query = "SELECT * FROM user WHERE user =? AND pass =? ";

        ps = MyConnection.getConnection().prepareStatement(query);

        ps.setString(1, uname);
        ps.setString(2, pass);

        rs = ps.executeQuery();
        if (rs.next()) {
            JOptionPane.showMessageDialog(null, "Welcome");
            id = rs.getInt("id");

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("MainMenu.fxml"));

            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }

            MainMenuController mainMenu = Loader.getController();
            mainMenu.setText(id);

            Parent p = Loader.getRoot();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(p));
            window.show();

        } else {
            JOptionPane.showMessageDialog(null, "this user name and password is not exit");
        }

    }

    @FXML
    private void return_login(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("menu1.fxml"));

        Scene tableViewScene = new Scene(tableViewParent);

        //this line gets the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    
}
