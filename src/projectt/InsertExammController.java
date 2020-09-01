package projectt;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class InsertExammController implements Initializable {

    @FXML
    private TextField subject;

    private int id;
    private ArrayList<String> lESSON = new ArrayList<>();
    private ArrayList<String> colorr = new ArrayList<>();
    private File addressFile;
    private boolean selectFile = false;

    @FXML
    private DatePicker date;
    @FXML
    private ComboBox<String> chooseLesson1;
    @FXML
    private TextArea note1;
    private double ftime;
    private double ltime;
    @FXML
    private ComboBox<Integer> ftime_m;
    @FXML
    private ComboBox<Integer> ftime_h;
    @FXML
    private ComboBox<Integer> ltime_m;
    @FXML
    private ComboBox<Integer> ltime_h;

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
    private void insertExam(ActionEvent event) throws IOException, SQLException, Exception {
        boolean flag = false;
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

        boolean dtt = false;
        try {
            Date d = Date.valueOf(date.getValue());
            if (d.equals(null)) {
                dtt = true;
            }
        } catch (Exception e) {
            dtt = true;
        }
        if (subject.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the subject");
        } else if (chll) {
            JOptionPane.showMessageDialog(null, "Choose a lesson");
        } else if (dtt) {
            JOptionPane.showMessageDialog(null, "Enter the date");
        } else if (flag) {
            JOptionPane.showMessageDialog(null, "You shoud enter both start times and end times");
        } else if (ftime >= ltime) {
            JOptionPane.showMessageDialog(null, "Start time shoud be before the end time");
        } else if (!comapareTimes(date.getValue().getDayOfWeek() + "", Date.valueOf(date.getValue()))) {
            JOptionPane.showMessageDialog(null, "You have another work at this time!");
        } else if (!this.selectFile) {
            JOptionPane.showMessageDialog(null, "Please choose a file");
        } else {
            Exam exam = new Exam();

            Date dexam = Date.valueOf(date.getValue());
            exam.setSubject(subject.getText());
            exam.setLesson(chooseLesson1.getValue());
            exam.setNote(note1.getText());
            exam.setDate(date.getValue());
            exam.setFtime(ftime);
            exam.setLtime(ltime);
            exam.setDay(date.getValue().getDayOfWeek() + "");


            PreparedStatement ps;
            String query = "INSERT INTO exam (subject,lesson,note,date,ftime,ltime,userID,color,day,file)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";

            try {
                ps = MyConnection.getConnection().prepareStatement(query);

                ps.setString(1, exam.getSubject());
                ps.setString(2, exam.getLesson());
                ps.setString(3, exam.getNote());
                ps.setDate(4, dexam);
                ps.setDouble(5, exam.getFtime());
                ps.setDouble(6, exam.getLtime());
                ps.setInt(7, id);
                ps.setString(8, returnColor(chooseLesson1.getValue()));
                ps.setString(9, exam.getDay());
                try {
                    ps.setString(10, this.addressFile.getPath());

                } catch (Exception e) {
                    System.out.println("Exception: ==> " + e);
                }

                if (ps.executeUpdate() > 0) {

                    JOptionPane.showMessageDialog(null, "New Exam add");

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

                    /*Stage stage=new Stage();
                 stage.setScene(new Scene(p));
                 stage.showAndWait();       */
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsertExammController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param UserID
     * @throws SQLException
     */
    public String returnColor(String c) {

        for (int j = 0; j < colorr.size(); j++) {
            if (c.compareTo(lESSON.get(j)) == 0) {
                return colorr.get(j);
            }
        }
        return null;

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
            if (rs.getDate("date").equals(date)) {
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
            if (rs.getDate("date").equals(date)) {
                if (!((ftime < rs.getDouble("ftime") && ftime < rs.getDouble("ltime")) || (ltime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            }
        }

        return b;
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

}
