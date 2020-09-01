package projectt;

import java.io.FileNotFoundException;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShowUserInfoController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label userName;
    @FXML
    private Label degree;
    @FXML
    private Label cityy;
    @FXML
    private Label unii;
    @FXML
    private Label Email;
    @FXML
    private Label GENDERR;

    private int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    void setText(int id) {
        this.id = id;

        PreparedStatement ps = null;
        ResultSet rs;
        String query = "SELECT * FROM user WHERE id=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                name.setText(rs.getString("name"));
                userName.setText(rs.getString("user"));
                GENDERR.setText(rs.getString("gendaer"));
                degree.setText(rs.getString("study"));
                cityy.setText(rs.getString("city"));
                unii.setText(rs.getString("uni"));
                Email.setText(rs.getString("email"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editUser(ActionEvent event) throws IOException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("UpdateUser.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ShowUserInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        UpdateUserController editUser = Loader.getController();
        editUser.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void mainMenu(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ShowUserInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mainMenu = Loader.getController();
        mainMenu.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

}
