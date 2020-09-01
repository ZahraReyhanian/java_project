package projectt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainMenuController implements Initializable {

    private int id;
    @FXML
    private Button userInfo;
    @FXML
    private ImageView imgView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void ProgramList(ActionEvent event) throws IOException, SQLException, Exception {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController programList = Loader.getController();
        programList.setText(id, "Programs");

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void ProgramTodayList(ActionEvent event) throws IOException, SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController programList = Loader.getController();
        programList.setText(id, "Today");

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void weeklySchedule(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Tables.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        TablesController tables = Loader.getController();
        tables.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void average(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Average.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        AverageController ave = Loader.getController();
        ave.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void timeStudy(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ReportTimes.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReportTimesController times = Loader.getController();
        times.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void message(ActionEvent event) throws IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Message.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MessageController message = Loader.getController();
        message.setText(id, userInfo.getText());

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void register_go(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("UpdateUser.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        UpdateUserController update = Loader.getController();
        update.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id) throws SQLException, FileNotFoundException, IOException {
        this.id = id;

        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM user WHERE id =?";

        ps = MyConnection.getConnection().prepareStatement(query);

        ps.setInt(1, id);

        rs = ps.executeQuery();

        if (rs.next()) {
            userInfo.setText(rs.getString("name"));
            InputStream is = rs.getBinaryStream("image");
            OutputStream os = new FileOutputStream(new File("img.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);

            }
            os.close();
            is.close();
            javafx.scene.image.Image image1 = new Image("file:img.jpg", imgView.getFitWidth(), imgView.getFitHeight(), true, true);

            this.imgView.setImage(image1);

        }

    }

    @FXML
    private void lessons(ActionEvent event) throws SQLException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("LessonList.fxml"));
        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        LessonListController lesson = Loader.getController();
        lesson.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void exitProgram(ActionEvent event) {
        System.exit(0);
    }

}
