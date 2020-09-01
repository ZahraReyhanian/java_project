package projectt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AVAJANG
 */
public class EditProgramController implements Initializable {

    @FXML
    private Label lbl_title;
    @FXML
    private TextField subject;
    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> chooseLesson1;
    @FXML
    private TextArea note1;
    @FXML
    private ComboBox<Integer> ftime_m;
    @FXML
    private ComboBox<Integer> ftime_h;
    @FXML
    private ComboBox<Integer> ltime_m;
    @FXML
    private ComboBox<Integer> ltime_h;
    private int id;
    private String kind;
    private double ftime;
    private double ltime;
    private ArrayList<String> lESSON = new ArrayList<>();
    private ArrayList<String> colorr = new ArrayList<>();
    private int programID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
        this.ftime_m.getItems().addAll(arr);
        this.ltime_m.getItems().addAll(arr);
        Integer[] arr2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        this.ftime_h.getItems().addAll(arr2);
        this.ltime_h.getItems().addAll(arr2);
    }

    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(EditProgramController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController programList = Loader.getController();
        programList.setText(id, "Programs");

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void updateProgram(ActionEvent event) throws SQLException {

        this.ftime = ftime_h.getValue() + (double) ftime_m.getValue() / 60;
        this.ltime = ltime_h.getValue() + (double) ltime_m.getValue() / 60;

        if (subject.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the subject");
        } else if (chooseLesson1.getValue().equals(null)) {
            JOptionPane.showMessageDialog(null, "Choose a lesson");
        } else if (ftime_h.getValue().equals(null)) {
            JOptionPane.showMessageDialog(null, "Enter start hour");
        } else if (ftime_m.getValue().equals(null)) {
            JOptionPane.showMessageDialog(null, "Enter start minuter");
        } else if (ltime_h.getValue().equals(null)) {
            JOptionPane.showMessageDialog(null, "Enter end hour");
        } else if (ltime_m.getValue().equals(null)) {
            JOptionPane.showMessageDialog(null, "Enter tnd minute");
        } else if (ftime >= ltime) {
            JOptionPane.showMessageDialog(null, "Start time shoud be before the end time");
        } else if (!comapareTimes(date.getValue().getDayOfWeek() + "", Date.valueOf(date.getValue()))) {
            JOptionPane.showMessageDialog(null, "You have another work at this time!");
        } else {
            if (kind.equals("homework")) {
                updateHomework();
            } else if (kind.equals("exam")) {
                updateExam();
            }
        }
    }

    private void updateHomework() throws SQLException {
        HomeWork hw = new HomeWork();

        hw.setSubject(subject.getText());
        hw.setLesson(chooseLesson1.getValue());
        hw.setNote(note1.getText());
        hw.setDate(date.getValue());
        hw.setFtime(ftime);
        hw.setLtime(ltime);
        hw.setDay(date.getValue().getDayOfWeek() + "");

        PreparedStatement ps = null;

        String query = "UPDATE homework SET subject=?,lesson=?,note=?,date=?,ftime=?,ltime=?,color=?,day=? "
                + " WHERE id = ?";
        ps = MyConnection.getConnection().prepareStatement(query);

        Date dexam = Date.valueOf(hw.getDate());

        ps.setString(1, hw.getSubject());
        ps.setString(2, hw.getLesson());
        ps.setString(3, hw.getNote());
        ps.setDate(4, dexam);
        ps.setDouble(5, hw.getFtime());
        ps.setDouble(6, hw.getLtime());
        ps.setString(7, returnColor(chooseLesson1.getValue()));
        ps.setString(8, hw.getDay());
        ps.setInt(9, programID);

        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "Update is done");
        } else {
            JOptionPane.showMessageDialog(null, "Update isn't done");

        }
    }

    private void updateExam() throws SQLException {
        Exam exam = new Exam();

        exam.setSubject(subject.getText());
        exam.setLesson(chooseLesson1.getValue());
        exam.setNote(note1.getText());
        exam.setDate(date.getValue());
        exam.setFtime(ftime);
        exam.setLtime(ltime);
        exam.setDay(date.getValue().getDayOfWeek() + "");

        PreparedStatement ps = null;

        String query = "UPDATE exam SET subject=? ,lesson=? ,note=? ,date=? ,ftime=? ,ltime=? ,color=? ,day=? "
                + " WHERE id = ?";
        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            Date dateExam = Date.valueOf(exam.getDate());

            ps.setString(1, exam.getSubject());
            ps.setString(2, exam.getLesson());
            ps.setString(3, exam.getNote());
            ps.setDate(4, dateExam);
            ps.setDouble(5, exam.getFtime());
            ps.setDouble(6, exam.getLtime());
            ps.setString(7, returnColor(chooseLesson1.getValue()));
            ps.setString(8, exam.getDay());
            ps.setInt(9, programID);
        } catch (SQLException ex) {
            Logger.getLogger(EditProgramController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ps.executeUpdate() > 0) {
            JOptionPane.showMessageDialog(null, "Update is done");
        } else {
            JOptionPane.showMessageDialog(null, "Update isn't done");

        }

    }

    public void setText(int id, String kind, int programID) throws SQLException, IOException {
        this.id = id;
        this.kind = kind;
        this.programID = programID;
        lbl_title.setText(kind);
        if (kind.equals("exam")) {
            fillTextsExam();
        } else if (kind.equals("homework")) {
            fillTextsHomework();
        }
        checkUserName();

    }

    public String returnColor(String c) {

        for (int j = 0; j < colorr.size(); j++) {
            if (c.compareTo(lESSON.get(j)) == 0) {
                return colorr.get(j);
            }
        }
        return null;

    }

    public void checkUserName() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM lesson WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                lESSON.add(rs.getString(2));
                colorr.add(rs.getString(4));

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }
        chooseLesson1.getItems().addAll(lESSON);

    }

    private void fillTextsExam() throws SQLException, FileNotFoundException, IOException {
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM exam WHERE id=? ";

        ps = MyConnection.getConnection().prepareStatement(query);

        ps.setInt(1, programID);

        rs = ps.executeQuery();
        if (rs.next()) {
            this.subject.setText(rs.getString("subject"));
            this.chooseLesson1.setValue(rs.getString("lesson"));
            this.note1.setText(rs.getString("note"));
            this.date.setValue(rs.getDate("date").toLocalDate());

            double stime_prg = rs.getDouble("ftime");
            int sHour = (int) Math.floor(stime_prg);
            int sMinute = (int) ((stime_prg - sHour) * 60);

            this.ftime_h.setValue(sHour);
            this.ftime_m.setValue(sMinute);

            double ltime_prg = rs.getDouble("ltime");
            int lHour = (int) Math.floor(ltime_prg);
            int lMinute = (int) ((ltime_prg - lHour) * 60);

            this.ltime_h.setValue(lHour);
            this.ltime_m.setValue(lMinute);

        }
    }

    private void fillTextsHomework() {
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM homework WHERE id=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, programID);

            rs = ps.executeQuery();
            if (rs.next()) {
                this.subject.setText(rs.getString("subject"));
                this.chooseLesson1.setValue(rs.getString("lesson"));
                this.note1.setText(rs.getString("note"));
                this.date.setValue(rs.getDate("date").toLocalDate());

                double stime_prg = rs.getDouble("ftime");
                int sHour = (int) Math.floor(stime_prg);
                int sMinute = (int) ((stime_prg - sHour) * 60);

                this.ftime_h.setValue(sHour);
                this.ftime_m.setValue(sMinute);

                double ltime_prg = rs.getDouble("ltime");
                int lHour = (int) Math.floor(ltime_prg);
                int lMinute = (int) ((ltime_prg - lHour) * 60);

                this.ltime_h.setValue(lHour);
                this.ltime_m.setValue(lMinute);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EditProgramController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private boolean comapareTimes(String day, Date date) throws SQLException {

        boolean b = true;
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM classp WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(InsertClassController.class.getName()).log(Level.SEVERE, null, ex);
        }
        rs = ps.executeQuery();

        while (rs.next()) {
            if (day.equals("SATURDAY") && rs.getInt("sat") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("SUNDAY") && rs.getInt("sun") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("MONDAY") && rs.getInt("mon") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("TUESDAY") && rs.getInt("tue") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("WEDNESDAY") && rs.getInt("wed") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("THURSDAY") && rs.getInt("thu") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            }

        }

        query = "SELECT * FROM exam WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(InsertClassController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getDate("date").equals(date)) {
                if (!(kind.equals("exam") && this.programID == rs.getInt("id"))) {
                    if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                        b = false;
                        return b;
                    }
                }
                

            }
        }

        query = "SELECT * FROM homework WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(InsertClassController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getDate("date").equals(date)) {
                if (!(kind.equals("homework") && this.programID == rs.getInt("id"))) {
                    if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                        b = false;
                        return b;
                    }
                }

            }
        }
        
        query = "SELECT * FROM request WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
        } catch (SQLException ex) {
            Logger.getLogger(InsertClassController.class.getName()).log(Level.SEVERE, null, ex);
        }

        rs = ps.executeQuery();

        while (rs.next()) {
            if (rs.getDate("date").equals(date)) {
                if (!(kind.equals("homework") && this.programID == rs.getInt("id"))) {
                    if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                        b = false;
                        return b;
                    }
                }

            }
        }

        return b;
    }

}
