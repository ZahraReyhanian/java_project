package projectt;

import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class CounterController implements Initializable {

    @FXML
    private Pane times;
    @FXML
    private Label timeLabel;

    private boolean finishTime;
    private int id;
    private String lesson;
    private int hour = 0, minute = 0, second = 0;
    @FXML
    private Label lbl_lesson;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, FileNotFoundException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ReportTimes.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CounterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReportTimesController report = Loader.getController();

        report.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

    }

    @FXML
    private void startbtn(ActionEvent event) {
        timeLabel.setText("00 : 00 : 00");
        finishTime = false;
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    private void finish(ActionEvent event) throws SQLException {
        finishTime = true;
        task.cancel();
        int learnTime = 0;
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM lesson WHERE userID =? AND title =? ";

        ps = MyConnection.getConnection().prepareStatement(query);

        ps.setInt(1, id);
        ps.setString(2, lesson);

        rs = ps.executeQuery();
        if (rs.next()) {
            learnTime = rs.getInt("times");
        }
        updateLesson(learnTime);
    }

    void setText(int id, String lesson) {
        this.id = id;
        this.lesson = lesson;
        lbl_lesson.setText(lesson);

    }

    @FXML
    private void timerGo(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Timer.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(CounterController.class.getName()).log(Level.SEVERE, null, ex);
        }

        TimerController timer = Loader.getController();

        timer.setText(id, lesson);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    Task<Void> task = new Task<Void>() {
        @Override
        public Void call() {
            for (; hour <= 24; hour++) {
                if (isCancelled()) {
                    break;
                }
                for (minute = 0; minute <= 59; minute++) {
                    if (isCancelled()) {
                        break;
                    }
                    for (second = 0; second <= 59; second++) {
                        if (isCancelled()) {
                            break;
                        }
                        System.out.println(hour + " : " + minute + " : " + second);
                        String t = "" + hour + " : " + minute + " : " + second;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                timeLabel.setText(t);

                            }
                        });

                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }

            }

            JOptionPane.showMessageDialog(null, "Finished !");
            currentThread().interrupt();
            return null;
        }
    };

    private void updateLesson(int learnTime) throws SQLException {
        PreparedStatement ps = null;
        String query = "UPDATE lesson SET times=? WHERE userID = ? AND title=?";

        ps = MyConnection.getConnection().prepareStatement(query);

        learnTime += ((hour - 1) * 3600) + ((minute - 1) * 60) + second - 1;

        ps.setInt(1, learnTime);
        ps.setInt(2, id);
        ps.setString(3, lesson);
        if (ps.executeUpdate() > 0) {
            System.out.println("Yessssss");
        } else {
            System.out.println("Nooooooo");
        }

    }

}
