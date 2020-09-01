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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ReportTimesController implements Initializable {

    private int id;
    private Button[] lesson;
    private Button[] times;
    private VBox vbox;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void setText(int id) throws SQLException {
        this.id = id;
        findLesson();

    }

    public void findLesson() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM lesson WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            lesson = new Button[100];
            times = new Button[100];
            int i = -1;
            while (rs.next()) {
                i++;
                String subject;
                subject = rs.getString("title");
                lesson[i] = new Button(subject);
                lesson[i].setMaxHeight(46);
                lesson[i].setMaxWidth(202);
                Font font = new Font(16);
                lesson[i].setFont(font);
                lesson[i].setStyle("-fx-background-color :" + rs.getString(4));
                lesson[i].setAlignment(Pos.CENTER);
                lesson[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        try {
                            FXMLLoader Loader = new FXMLLoader();
                            Loader.setLocation(getClass().getResource("Timer.fxml"));

                            Loader.load();

                            TimerController timer = Loader.getController();

                            timer.setText(id, subject);

                            Parent p = Loader.getRoot();
                            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            window.setScene(new Scene(p));
                            window.show();
                        } catch (IOException ex) {
                            Logger.getLogger(ReportTimesController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });

                int time = rs.getInt(7);
                int h = time / 3600;
                int m = (time % 3600) / 60;
                int s = ((time % 3600) % 60);
                times[i] = new Button(h + ":" + m + ":" + s);
                times[i].setStyle("-fx-background-color :" + rs.getString(4));
                times[i].setMaxHeight(46);
                times[i].setMaxWidth(202);
                times[i].setFont(font);
                times[i].setTextFill(Color.WHITE);
                times[i].setAlignment(Pos.CENTER);

                gridPane.add(lesson[i], 1, i + 1);
                gridPane.add(times[i], 0, i + 1);

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ScrollPane sp = new ScrollPane(this.gridPane);
        sp.setPrefSize(402, 390);
        sp.setLayoutX(45);
        sp.setLayoutY(92);
        this.mainPane.getChildren().add(sp);

    }

    @FXML
    private void menuButton(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController menu = Loader.getController();

        menu.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }
}
