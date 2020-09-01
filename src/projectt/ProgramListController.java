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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ProgramListController implements Initializable {

    private int id;
    Exam[] lsn = new Exam[100];
    Program[] pgm = new Program[100];
    Button[] button = new Button[100];
    @FXML
    private VBox vbox;
    private String title;
    private String whatSearch;

    @FXML
    private Label titleLabel;
    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void All_botton(ActionEvent event) throws IOException, SQLException {

    }

    @FXML
    private void hw_botton(ActionEvent event) throws IOException, SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("HW_show.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        HW_showController hw = Loader.getController();

        hw.setText(id, title);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void exam_class(ActionEvent event) throws IOException, SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ExamShow.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ExamShowController exam = Loader.getController();

        exam.setText(id, title);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void class_botton(ActionEvent event) throws IOException, SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ClassPShow.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ClassPShowController classP = Loader.getController();

        classP.setText(id, title);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void menu_botton(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController Mmain = Loader.getController();

        Mmain.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void Add(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("AddMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AddMenuController addM = Loader.getController();

        addM.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id, String title) throws SQLException, Exception {
        if (title.equals("Today")) {
            titleLabel.setText("لیست برنامه های امروز");
            this.title = "Today";
        } else {
            titleLabel.setText("لیست برنامه ها");
            this.title = "Programs";
        }
        this.id = id;
        printProgram();
    }

    public void printProgram() throws Exception {

        PrograamSelect psb = new PrograamSelect(id);
        if (this.title.equals("Today")) {
            psb.selectTodayExam();
            psb.selectTodayHmomeWork();
            psb.selectTodayClass();
        } else {
            psb.selectExam();
            psb.selectClass();
            psb.selectHmomeWork();
        }

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
        }
    }

    @FXML
    private void search(MouseEvent event) {

    }

    @FXML
    private void search(ActionEvent event) throws SQLException, Exception {
        this.whatSearch = JOptionPane.showInputDialog("What do you want?");

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("SearchPage.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
        }

        SearchPageController search = Loader.getController();

        search.setText(id, this.whatSearch);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

}
