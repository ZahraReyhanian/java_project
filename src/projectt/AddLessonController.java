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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class AddLessonController implements Initializable {

    private int id;
    private String colorB = null;
    @FXML
    private TextField title;
    @FXML
    private TextField profosser;
    @FXML
    private TextField VAHED;
    @FXML
    private Button blue;
    @FXML
    private Button red;
    @FXML
    private Button gray;
    @FXML
    private Button orange;
    @FXML
    private Button purple;
    @FXML
    private Button brown;
    @FXML
    private Button green;
    @FXML
    private Button red2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    void setText(int id) {
        this.id = id;
    }

    @FXML
    private void save(ActionEvent event) throws SQLException {

        if (title.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "نام درس را وارد کنید");
        } else if (isNumeric(title.getText())) {
            JOptionPane.showMessageDialog(null, "نام درس نمی تواند عدد باشد");
        } else if (isNumeric(profosser.getText())) {
            JOptionPane.showMessageDialog(null, "نام استاد نمیتواند عدد باشد");
        } else if (VAHED.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "تعداد واحد درس را انتخاب کنید");
        } else if (!isNumeric(VAHED.getText())) {
            JOptionPane.showMessageDialog(null, "واحد باید عدد باشد");
        } else if (isNumeric(VAHED.getText()) && Integer.parseInt(VAHED.getText()) < 0) {
            JOptionPane.showMessageDialog(null, "واحد نباید منفی باشد");
        } else if (colorB == null) {
            JOptionPane.showMessageDialog(null, "یک رنگ را انتخاب کنید");
        } else if (checkLesson(title.getText())) {
            JOptionPane.showMessageDialog(null, "این درس تکراری است");
        } else {

            PreparedStatement ps = null;
            String query = "INSERT INTO lesson (title,professor,color,vahed,userID,times)" + "VALUES(?,?,?,?,?,?)";

            ps = MyConnection.getConnection().prepareStatement(query);

            ps.setString(1, title.getText());
            ps.setString(2, profosser.getText());
            ps.setString(3, colorB);
            ps.setString(4, VAHED.getText());
            ps.setInt(5, id);
            ps.setInt(6, 0);

            if (ps.executeUpdate() > 0) {

                JOptionPane.showMessageDialog(null, "New lesson add");

                FXMLLoader Loader = new FXMLLoader();
                Loader.setLocation(getClass().getResource("LessonList.fxml"));

                try {
                    Loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(AddLessonController.class.getName()).log(Level.SEVERE, null, ex);
                }

                LessonListController lesson = Loader.getController();
                lesson.setText(id);

                Parent p = Loader.getRoot();
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(p));
                window.show();

            }
        }
    }

    public boolean checkLesson(String lesson) throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs;
        boolean checkLesson = false;

        String query = "SELECT * FROM lesson WHERE title=? AND userID=?";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setString(1, lesson);
            ps.setInt(2, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                checkLesson = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return checkLesson;
    }

    //is numeric
    public boolean isNumeric(String s) {

        return java.util.regex.Pattern.matches("\\d+", s);
    }

    @FXML
    private void cansel(ActionEvent event) throws SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("LessonList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AddLessonController.class.getName()).log(Level.SEVERE, null, ex);
        }

        LessonListController lesson = Loader.getController();
        lesson.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void blue(ActionEvent event) {
        this.colorB = "#3f51b5";
        textButton();
        blue.setText("O");

    }

    @FXML
    private void red(ActionEvent event) {
        this.colorB = "#DD2c00";
        textButton();
        red.setText("O");
    }

    @FXML
    private void gray(ActionEvent event) {
        this.colorB = "#616161";
        textButton();
        gray.setText("O");
    }

    @FXML
    private void orange(ActionEvent event) {
        this.colorB = "#f9a825";
        textButton();
        orange.setText("O");
    }

    @FXML
    private void purple(ActionEvent event) {
        this.colorB = "purple";
        textButton();
        purple.setText("O");
    }

    @FXML
    private void brown(ActionEvent event) {
        this.colorB = "#8c6e63";
        textButton();
        brown.setText("O");
    }

    @FXML
    private void green(ActionEvent event) {
        this.colorB = "#307b05";
        textButton();
        green.setText("O");
    }

    @FXML
    private void red2(ActionEvent event) {
        this.colorB = "#812321";
        textButton();
        red2.setText("O");
    }

    public void textButton() {
        blue.setText(null);
        purple.setText(null);
        brown.setText(null);
        red.setText(null);
        red2.setText(null);
        green.setText(null);
        gray.setText(null);
        orange.setText(null);
    }

}
