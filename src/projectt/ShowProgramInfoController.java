package projectt;

import static java.awt.Color.red;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ShowProgramInfoController implements Initializable {

    private ClassP classp;
    private Program program;
    private int id;
    private String replacee;
    @FXML
    private Label lessonName;
    @FXML
    private Label TypeLesson;
    @FXML
    private Label datee;
    @FXML
    private Label sTime;
    @FXML
    private Label sat;
    @FXML
    private Label textt;
    @FXML
    private Label fTime;
    @FXML
    private Label su;
    @FXML
    private Label mo;
    @FXML
    private Label tu;
    @FXML
    private Label we;
    @FXML
    private Label th;
    @FXML
    private Label fr;
    @FXML
    private AnchorPane backgroundd;
    private String kind;
    private int programID;
    @FXML
    private Pane filePane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void editProgram(ActionEvent event) throws SQLException, IOException {
        if (kind.equals("exam") || kind.equals("homework")) {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("EditProgram.fxml"));

            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            EditProgramController edit = Loader.getController();
            edit.setText(id, kind, programID);

            Parent p = Loader.getRoot();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(p));
            window.show();
        } else if (kind.equals("classp")) {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("EditClassp.fxml"));

            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
            }

            EditClasspController edit = Loader.getController();
            edit.setText(id, programID);
            Parent p = Loader.getRoot();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(p));
            window.show();
        }
    }

    @FXML
    private void returnbutton(ActionEvent event) throws SQLException, Exception {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController programList = Loader.getController();
        programList.setText(id, "Programs");

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();

        /*Stage stage=new Stage();
               stage.setScene(new Scene(p));
               stage.showAndWait();       */
    }

    public void setText(ClassP classp, int id, int programID) throws SQLException {
        this.classp = classp;
        this.id = id;
        this.programID = programID;

        System.out.println("the program ID => " + programID);
        this.kind = "classp";
        lessonName.setText(classp.getSubject());
        TypeLesson.setText(classp.getLesson());
        Button btn = new Button();
        btn.setMinSize(100, 30);
        btn.setText("your file");

        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM classp WHERE id =? AND userID=?";

        ps = MyConnection.getConnection().prepareStatement(query);

        ps.setInt(1, programID);
        ps.setInt(2, classp.getUserID());

        rs = ps.executeQuery();

        if (rs.next()) {

            String address = rs.getString("file");
            System.out.println(address);
            replacee = address.replace("\\", "\\\\");
            System.out.println(replacee);

        }

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {

                    if ((new File(replacee)).exists()) {
                        Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + replacee);
                        p.waitFor(10, TimeUnit.MILLISECONDS);
                    }
                } catch (NullPointerException ne) {
                    JOptionPane.showMessageDialog(null, "You don't have any file");
                } catch (IOException ex) {
                    Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        filePane.getChildren().add(btn);

        double stime_prg = classp.getFtime();
        int sHour = (int) Math.floor(stime_prg);
        int sMinute = (int) ((stime_prg - sHour) * 60);

        sTime.setText(sHour + " : " + sMinute);

        double ltime_prg = classp.getLtime();
        int lHour = (int) Math.floor(ltime_prg);
        int lMinute = (int) ((ltime_prg - lHour) * 60);
        fTime.setText(lHour + " : " + lMinute);

        String[] daysArray = classp.getDaysWeek().split(" , ");

        for (int i = 0; i < daysArray.length; i++) {
            if (daysArray[i].equals("SATURDAY")) {
                sat.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("SUNDAY")) {
                su.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("MONDAY")) {
                mo.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("TUESDAY")) {
                tu.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("WEDNESDAY")) {
                we.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("THURSDAY")) {
                th.setStyle("-fx-background-color : #ff0000");
            } else if (daysArray[i].equals("FRIDAY")) {
                fr.setStyle("-fx-background-color : #ff0000");
            }
        }
        textt.setText(classp.getNote());
    }

    public void setText(Program program, int id, String kind, int programID) throws SQLException {
        this.program = program;
        this.id = id;
        this.programID = programID;
        this.kind = kind;
        lessonName.setText(program.getSubject());
        TypeLesson.setText(program.getLesson());
        this.datee.setText(program.getDate() + "");
        Button btn = new Button();
        btn.setMinSize(100, 30);
        btn.setText("your file");
        if (kind.equals("homework")) {

            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM homework WHERE id =? AND userID=?";

            ps = MyConnection.getConnection().prepareStatement(query);

            ps.setInt(1, programID);
            ps.setInt(2, program.getUserID());

            rs = ps.executeQuery();

            if (rs.next()) {

                String address = rs.getString("file");
                System.out.println(address);
                replacee = address.replace("\\", "\\\\");
                System.out.println(replacee);

            }

            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {

                        if ((new File(replacee)).exists()) {
                            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + replacee);
                            p.waitFor(10, TimeUnit.MILLISECONDS);
                        }
                    } catch (NullPointerException ne) {
                        JOptionPane.showMessageDialog(null, "You don't have any file");
                    } catch (IOException ex) {
                        Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            filePane.getChildren().add(btn);

        } else {

            PreparedStatement ps;
            ResultSet rs;

            String query = "SELECT * FROM exam WHERE id =? AND userID=?";

            ps = MyConnection.getConnection().prepareStatement(query);

            ps.setInt(1, programID);
            ps.setInt(2, program.getUserID());

            rs = ps.executeQuery();

            if (rs.next()) {

                String address = rs.getString("file");
                System.out.println(address);
                replacee = address.replace("\\", "\\\\");
                System.out.println(replacee);

            }

            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    try {

                        if ((new File(replacee)).exists()) {
                            Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + replacee);
                            p.waitFor(10, TimeUnit.MILLISECONDS);
                        }
                    } catch (NullPointerException ne) {
                        JOptionPane.showMessageDialog(null, "You don't have any file");
                    } catch (IOException ex) {
                        Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ShowProgramInfoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            });

            filePane.getChildren().add(btn);

        }

        double stime_prg = program.getFtime();
        int sHour = (int) Math.floor(stime_prg);
        int sMinute = (int) ((stime_prg - sHour) * 60);
        sTime.setText(sHour + " : " + sMinute);

        double ltime_prg = program.getLtime();
        int lHour = (int) Math.floor(ltime_prg);
        int lMinute = (int) ((ltime_prg - lHour) * 60);
        fTime.setText(lHour + " : " + lMinute);

        if (program.getDay().equals("SATURDAY")) {
            sat.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("SUNDAY")) {
            su.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("MONDAY")) {
            mo.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("TUESDAY")) {
            tu.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("WEDNESDAY")) {
            we.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("THURSDAY")) {
            th.setStyle("-fx-background-color : #ff0000");
        } else if (program.getDay().equals("FRIDAY")) {
            fr.setStyle("-fx-background-color : #ff0000");
        }

        textt.setText(program.getNote());

    }

}
