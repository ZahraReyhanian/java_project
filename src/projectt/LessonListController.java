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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LessonListController implements Initializable {

    private int id;
    private VBox vbox;
    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void searchButton(ActionEvent event) {
    }

    @FXML
    private void menuButton(ActionEvent event) throws SQLException, FileNotFoundException, IOException, Exception {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LessonListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mainMenu = Loader.getController();
        mainMenu.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void addLessonButton(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AddLesson.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LessonListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddLessonController addLesson = Loader.getController();
        addLesson.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    void setText(int id) throws SQLException {
        this.id = id;
        findLesson();

    }

    public void findLesson() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs;
        Lesson[] lsn = new Lesson[100];
        vbox = new VBox();

        // indexOfLesson=-1;
        String query = "SELECT * FROM lesson WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Button[] button = new Button[100];
            int i = -1;
            while (rs.next()) {
                String n = rs.getString(2);
                i++;
                button[i] = new Button(rs.getString(2));
                button[i].setStyle("-fx-background-color :" + rs.getString(4));
                button[i].setPrefHeight(40);
                button[i].setPrefWidth(458);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                vbox.getChildren().add(button[i]);
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

            }
            ScrollPane sp = new ScrollPane(vbox);
            sp.setPrefSize(460, 420);
            sp.setLayoutX(17);
            sp.setLayoutY(66);
            this.mainPane.getChildren().add(sp);

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
