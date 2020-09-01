package projectt;

import java.io.FileNotFoundException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class SearchPageController implements Initializable {

    private int id;
    private String title;
    private VBox vbox;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label titleLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void menu_botton(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mMenu = Loader.getController();

        mMenu.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id, String subject) throws SQLException, Exception {
        this.id = id;
        vbox = new VBox();
        titleLabel.setText(subject);
        this.title = subject;
        printProgram();
    }

    public void printProgram() throws SQLException, Exception {
        PrograamSelect psb = new PrograamSelect(id);
        psb.search(title);

        if (!psb.getButton().equals(null)) {
            Button[] buttons = new Button[100];
            buttons = psb.getButton();

            for (int i = 0; i <= psb.getI(); i++) {
                vbox.getChildren().add(buttons[i]);
            }
            ScrollPane sp = new ScrollPane(vbox);
            sp.setPrefSize(460, 360);
            sp.setLayoutX(17);
            sp.setLayoutY(116);
            this.mainPane.getChildren().add(sp);
        } else {
            JOptionPane.showMessageDialog(null, "چنین برنامه ای موجود نیست.");
        }

    }

    @FXML
    private void returnTo(ActionEvent event) throws SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController pl = Loader.getController();
        pl.setText(id, "Programs");

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }
}
