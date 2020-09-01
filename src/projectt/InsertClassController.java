package projectt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AVAJANG
 */
public class InsertClassController implements Initializable {

    private double ftime, ltime;
    private int id;
    private ArrayList<String> lESSON = new ArrayList<>();
    private ArrayList<String> colorr = new ArrayList<>();
    private File addressFile;
    private boolean selectFile = false;

    @FXML
    private ComboBox<String> chooseLesson1;
    @FXML
    private CheckBox sat;
    @FXML
    private CheckBox mon;
    @FXML
    private CheckBox wed;
    @FXML
    private CheckBox sun;
    @FXML
    private ComboBox<Integer> ftime_m;
    @FXML
    private ComboBox<Integer> ftime_h;
    @FXML
    private ComboBox<Integer> ltime_m;
    @FXML
    private ComboBox<Integer> ltime_h;
    @FXML
    private TextArea note;
    @FXML
    private TextField subject;
    @FXML
    private CheckBox tue;
    @FXML
    private CheckBox thu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59};
        this.ftime_m.getItems().addAll(arr);
        this.ltime_m.getItems().addAll(arr);
        Integer[] arr2 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
        this.ftime_h.getItems().addAll(arr2);
        this.ltime_h.getItems().addAll(arr2);
    }

    @FXML
    private void returnMenu(ActionEvent event) throws IOException, SQLException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("ProgramList.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(InsertExammController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ProgramListController programList = Loader.getController();
        programList.setText(id, "Programs");

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void insertClass(ActionEvent event) throws IOException, SQLException, Exception {
        boolean flag = false;
        String days = "";
        if (sat.isSelected()) {
            days += "sat,";
        }
        if (sun.isSelected()) {
            days += "sun,";
        }
        if (mon.isSelected()) {
            days += "mon,";
        }
        if (tue.isSelected()) {
            days += "tue,";
        }
        if (wed.isSelected()) {
            days += "wed,";
        }
        if (thu.isSelected()) {
            days += "thu";
        }
        try {
            this.ftime = ftime_h.getValue() + (double) ftime_m.getValue() / 60;
            this.ltime = ltime_h.getValue() + (double) ltime_m.getValue() / 60;
        } catch (Exception e) {
            flag = true;
        }
        boolean chll = false;
        try {
            String l = chooseLesson1.getValue();
            if (l.equals(null)) {
                chll = true;
            }
        } catch (Exception e) {
            chll = true;
        }

        if (subject.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the subject");
        } else if (chll) {
            JOptionPane.showMessageDialog(null, "Choose a lesson");
        } else if (flag) {
            JOptionPane.showMessageDialog(null, "You shoud enter both start times and end times");
        } else if (ftime >= ltime) {
            JOptionPane.showMessageDialog(null, "Start time shoud be before the end time");
        } else if (days.equals("")) {
            JOptionPane.showMessageDialog(null, "Enter at least one day");
        } else if (!comapareTimes()) {
            JOptionPane.showMessageDialog(null, "You have another work at this time!");
        } else if (!this.selectFile) {
            JOptionPane.showMessageDialog(null, "Please choose a file");
        } else {
            ClassP classp = new ClassP();

            classp.setSubject(subject.getText());
            classp.setLesson(chooseLesson1.getValue());
            classp.setNote(note.getText());
            classp.setFtime(ftime);
            classp.setLtime(ltime);

            classp.setDaysWeek(days);
            PreparedStatement ps;

            String query = "INSERT INTO classp (subject,lesson,note,ftime,ltime,userID,color,sat,sun,mon,tue,wed,thu,fri,file)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                ps = MyConnection.getConnection().prepareStatement(query);

                ps.setString(1, classp.getSubject());
                ps.setString(2, classp.getLesson());
                ps.setString(3, classp.getNote());
                ps.setDouble(4, classp.getFtime());
                ps.setDouble(5, classp.getLtime());
                ps.setInt(6, id);
                ps.setString(7, returnColor(chooseLesson1.getValue()));

                if (sat.isSelected()) {
                    ps.setInt(8, 1);
                } else {
                    ps.setInt(8, 0);
                }
                if (sun.isSelected()) {
                    ps.setInt(9, 1);
                } else {
                    ps.setInt(9, 0);
                }
                if (mon.isSelected()) {
                    ps.setInt(10, 1);
                } else {
                    ps.setInt(10, 0);
                }
                if (tue.isSelected()) {
                    ps.setInt(11, 1);
                } else {
                    ps.setInt(11, 0);
                }
                if (wed.isSelected()) {
                    ps.setInt(12, 1);
                } else {
                    ps.setInt(12, 0);
                }
                if (thu.isSelected()) {
                    ps.setInt(13, 1);
                } else {
                    ps.setInt(13, 0);
                }

                ps.setInt(14, 0);

                try {
                    ps.setString(15, this.addressFile.getPath());

                } catch (Exception e) {
                    System.out.println("Exception: ==> " + e);
                }

                if (ps.executeUpdate() > 0) {

                    JOptionPane.showMessageDialog(null, "New Class add");

                    /*Stage stage=new Stage();
                 stage.setScene(new Scene(p));
                 stage.showAndWait();       */
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsertExammController.class.getName()).log(Level.SEVERE, null, ex);
            }

            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("ProgramList.fxml"));

            try {
                Loader.load();
            } catch (IOException ex) {
                Logger.getLogger(InsertClassController.class.getName()).log(Level.SEVERE, null, ex);
            }

            ProgramListController programList = Loader.getController();
            programList.setText(id, "Programs");

            Parent p = Loader.getRoot();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(p));
            window.show();

        }

    }

    public void setText(int id) throws SQLException {
        this.id = id;
        checkUserName();
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

    @FXML
    private void addFile(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.*"));

        File selected = fileChooser.showOpenDialog(null);

        if (selected != null) {
            System.out.println(selected.getAbsolutePath());
            selectFile = true;
            addressFile = new File(selected.getAbsolutePath());
        } else {
            System.out.println("ERRRRRRRRRROOOOOOOORRRRRRRRR");
        }
    }

    private boolean comapareTimes() throws SQLException {
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
            if (this.sat.isSelected() && rs.getInt("sat") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.sun.isSelected() && rs.getInt("sun") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.mon.isSelected() && rs.getInt("mon") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.tue.isSelected() && rs.getInt("tue") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.wed.isSelected() && rs.getInt("wed") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.thu.isSelected() && rs.getInt("thu") == 1) {
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
            if (this.sat.isSelected() && rs.getString("day").equals("SATURDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.sun.isSelected() && rs.getString("day").equals("SUNDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.mon.isSelected() && rs.getString("day").equals("MONDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.tue.isSelected() && rs.getString("day").equals("TUESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.wed.isSelected() && rs.getString("day").equals("WEDNESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.thu.isSelected() && rs.getString("day").equals("THURSDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
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
            if (this.sat.isSelected() && rs.getString("day").equals("SATURDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.sun.isSelected() && rs.getString("day").equals("SUNDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.mon.isSelected() && rs.getString("day").equals("MONDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.tue.isSelected() && rs.getString("day").equals("TUESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.wed.isSelected() && rs.getString("day").equals("WEDNESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.thu.isSelected() && rs.getString("day").equals("THURSDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
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
            if (this.sat.isSelected() && rs.getString("day").equals("SATURDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.sun.isSelected() && rs.getString("day").equals("SUNDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.mon.isSelected() && rs.getString("day").equals("MONDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.tue.isSelected() && rs.getString("day").equals("TUESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.wed.isSelected() && rs.getString("day").equals("WEDNESDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (this.thu.isSelected() && rs.getString("day").equals("THURSDAY")) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            }

        }

        return b;
    }

    public String returnColor(String c) {

        for (int j = 0; j < colorr.size(); j++) {
            if (c.compareTo(lESSON.get(j)) == 0) {
                return colorr.get(j);
            }
        }
        return null;

    }

}
