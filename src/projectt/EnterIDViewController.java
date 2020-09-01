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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class EnterIDViewController implements Initializable {

    @FXML
    private TextField username;

    private int id;
    private boolean isServer;
    private String name;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void returnMenu(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Message.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageController msg = Loader.getController();

        msg.setText(id,name);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void StartChat(ActionEvent event) throws SQLException, InterruptedException {
        if (this.username.equals(null)) {
            JOptionPane.showMessageDialog(null, "You should enter username");
        } else {
            PreparedStatement ps;
            ResultSet rs;
            String uname = username.getText();

            String query = "SELECT * FROM user WHERE user =? ";

            ps = MyConnection.getConnection().prepareStatement(query);

            ps.setString(1, uname);

            rs = ps.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Welcome");

               
                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("Server.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ServerController server = Loader.getController();
                        server.setText(id, name);

                        Parent p = Loader.getRoot();

                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                        Thread.sleep(500);
                        
                        FXMLLoader Loader2 = new FXMLLoader();
                        Loader2.setLocation(getClass().getResource("Client.fxml"));

                        try {
                            Loader2.load();
                        } catch (IOException ex) {
                            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ClientController client = Loader2.getController();
                        try {
                            client.setText(rs.getInt("id"), rs.getString("name"));
                        } catch (SQLException ex) {
                            System.out.println(ex);
                            JOptionPane.showMessageDialog(null, "Oh Sorry a problem Occur!");
                        }

                        Parent p2 = Loader2.getRoot();

                        Stage stage = new Stage();
                        stage.setScene(new Scene(p2));
                        stage.showAndWait();

                    
                

            } else {
                JOptionPane.showMessageDialog(null, "This username is not exit");
            }

        }
    }

    private boolean exitUserName() {
        boolean b = false;
        return b;
    }

    public void setText(int id, String name) {
        this.id=id;
        this.name=name;

    }
}
