/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AVAJANG
 */
public class TimerController implements Initializable {

    private int id;
    private int hour = 0, minute = 0, second = 0;
    boolean st = false;
    @FXML
    private Pane times;

    private Thread th;
    private String lesson;

    @FXML
    private Label timeLabel;
    @FXML
    private Label lbl_lesson;

    private Task<Void> task = new Task<Void>() {
        @Override
        public Void call() {
            boolean finish = false;
            st = true;
            while (!finish) {
                for (int i = 0; i <= 60; i++) {
                    for (int j = second; j >= 0; j--) {
                        int s = j;
                        System.out.println(hour + " : " + minute + " : " + j);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (hour >= 10 && minute >= 10 && s >= 10) {
                                    timeLabel.setText("" + hour + " : " + minute + " : " + s);
                                } else if (hour <= 9 && minute > 9 && s > 9) {
                                    timeLabel.setText("0" + hour + " : " + minute + " : " + s);
                                } else if (hour > 9 && minute <= 9 && s > 9) {
                                    timeLabel.setText("" + hour + " : 0" + minute + " : " + s);
                                } else if (hour > 9 && minute > 9 && s <= 9) {
                                    timeLabel.setText("" + hour + " : " + minute + " : 0" + s);
                                } else if (hour <= 9 && minute <= 9 && s > 9) {
                                    timeLabel.setText("0" + hour + " : 0" + minute + " : " + s);
                                } else if (hour <= 9 && minute > 9 && s <= 9) {
                                    timeLabel.setText("0" + hour + " : " + minute + " : 0" + s);
                                } else if (hour > 9 && minute <= 9 && s <= 9) {
                                    timeLabel.setText("" + hour + " : 0" + minute + " : 0" + s);
                                } else if (hour <= 9 && minute <= 9 && s <= 9) {
                                    timeLabel.setText("0" + hour + " : 0" + minute + " : 0" + s);
                                }
                            }
                        });

                        try {
                            sleep(1000);
                        } catch (InterruptedException ex) {
                            System.out.println(ex.getMessage());
                        }

                    }
                    if (hour == 0 && minute == 0) {
                        finish = true;
                        break;
                    } else {
                        minute -= 1;
                        second = 59;
                    }
                }
                if (hour == 0 && minute == 0) {
                    finish = true;

                } else {
                    hour -= 1;
                }

            }
            
            Media media = new Media(new File(".\\src\\audiAlarm\\Cool-alarm.mp3").toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            JOptionPane.showMessageDialog(null, "Time up!");
            try {
                mediaPlayer.dispose();
            } catch (Throwable ex) {
                Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            currentThread().interrupt();
            return null;
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void timeSet(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("SetTimeView.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        SetTimeViewController time = Loader.getController();

        time.setText(id, lesson);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, FileNotFoundException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ReportTimes.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReportTimesController report = Loader.getController();

        report.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    void setText(int id, int hour, int minute, int second, String lesson) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.id = id;
        if (hour >= 10 && minute >= 10 && second >= 10) {
            timeLabel.setText("" + hour + " : " + minute + " : " + second);
        } else if (hour <= 9 && minute > 9 && second > 9) {
            timeLabel.setText("0" + hour + " : " + minute + " : " + second);
        } else if (hour > 9 && minute <= 9 && second > 9) {
            timeLabel.setText("" + hour + " : 0" + minute + " : " + second);
        } else if (hour > 9 && minute > 9 && second <= 9) {
            timeLabel.setText("" + hour + " : " + minute + " : 0" + second);
        } else if (hour <= 9 && minute <= 9 && second > 9) {
            timeLabel.setText("0" + hour + " : 0" + minute + " : " + second);
        } else if (hour <= 9 && minute > 9 && second <= 9) {
            timeLabel.setText("0" + hour + " : " + minute + " : 0" + second);
        } else if (hour > 9 && minute <= 9 && second <= 9) {
            timeLabel.setText("" + hour + " : 0" + minute + " : 0" + second);
        } else if (hour <= 9 && minute <= 9 && second <= 9) {
            timeLabel.setText("0" + hour + " : 0" + minute + " : 0" + second);
        }
        this.lesson = lesson;
        lbl_lesson.setText(lesson);
    }

    @FXML
    private void startbtn(ActionEvent event) throws SQLException {
        th = new Thread(task);
        th.setDaemon(true);
        th.start();
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

    @FXML
    private void counter(ActionEvent event) {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("Counter.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TimerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        CounterController counter = Loader.getController();

        counter.setText(id, lesson);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id, String lesson) {
        this.id = id;
        th = new Thread(task);

        this.lesson = lesson;
        lbl_lesson.setText(lesson);
    }

    private void updateLesson(int learnTime) throws SQLException {
        PreparedStatement ps = null;
        String query = "UPDATE lesson SET times=? WHERE userID = ? AND title=?";

        ps = MyConnection.getConnection().prepareStatement(query);

        learnTime += (hour * 3600) + (minute * 60) + second;

        ps.setInt(1, learnTime);
        ps.setInt(2, id);
        ps.setString(3, lesson);
        if (ps.executeUpdate() > 0) {
            System.out.println("Yessssssss");
        } else {
            System.out.println("Nooooooo");
        }

    }

}
