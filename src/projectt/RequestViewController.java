package projectt;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class RequestViewController implements Initializable {

    private int id;
    private double ftime;
    private double ltime;
    private String ClientName;

    @FXML
    private DatePicker datee;
    @FXML
    private ComboBox<Integer> ftime_m;
    @FXML
    private ComboBox<Integer> ftime_h;
    @FXML
    private ComboBox<Integer> ltime_m;
    @FXML
    private ComboBox<Integer> ltime_h;
    @FXML
    private TextField name;

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
    private void returnButton(ActionEvent event) throws SQLException, IOException {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void saveButton(ActionEvent event) throws SQLException {
        boolean flag = false;
        try {
            this.ftime = ftime_h.getValue() + (double) ftime_m.getValue() / 60;
            this.ltime = ltime_h.getValue() + (double) ltime_m.getValue() / 60;
        } catch (Exception e) {
            flag = true;
        }

        boolean dtt = false;
        try {
            Date d = Date.valueOf(datee.getValue());
            if (d.equals(null)) {
                dtt = true;
            }
        } catch (Exception e) {
            dtt = true;
        }

        if (name.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the subject");
        } else if (dtt) {
            JOptionPane.showMessageDialog(null, "Enter the date");
        } else if (flag) {
            JOptionPane.showMessageDialog(null, "You shoud enter both start times and end times");
        } else if (ftime >= ltime) {
            JOptionPane.showMessageDialog(null, "Start time shoud be before the end time");
        } else if (!dtt && !comapareTimes(datee.getValue().getDayOfWeek() + "", Date.valueOf(datee.getValue()))) {
            JOptionPane.showMessageDialog(null, "You have another work at this time!");
        } else {
            Program prg = new Program();

            Date dexam = Date.valueOf(datee.getValue());
            prg.setSubject(name.getText());
            prg.setDate(datee.getValue());
            prg.setFtime(ftime);
            prg.setLtime(ltime);
            prg.setDay(datee.getValue().getDayOfWeek() + "");

            PreparedStatement ps;
            String query = "INSERT INTO request (subject,date,ftime,ltime,userID,color,day)"
                    + "VALUES (?,?,?,?,?,?,?)";

            try {
                ps = MyConnection.getConnection().prepareStatement(query);

                ps.setString(1, prg.getSubject());
                ps.setDate(2, dexam);
                ps.setDouble(3, prg.getFtime());
                ps.setDouble(4, prg.getLtime());
                ps.setInt(5, id);
                ps.setString(6, "#364F6B");
                ps.setString(7, prg.getDay());

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Saved!");

                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("Tables.fxml"));
                    try {
                        Loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(RequestViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    TablesController tables = Loader.getController();
                    tables.setText(id);

                    Parent p = Loader.getRoot();
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(new Scene(p));
                    window.show();
                } else {
                    JOptionPane.showMessageDialog(null, "Fail to save:(");
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsertExammController.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("SUNDAY") && rs.getInt("sun") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("MONDAY") && rs.getInt("mon") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("TUESDAY") && rs.getInt("tue") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("WEDNESDAY") && rs.getInt("wed") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            } else if (day.equals("THURSDAY") && rs.getInt("thu") == 1) {
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
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
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
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
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
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
                if (!((ftime < rs.getDouble("ftime") && ltime < rs.getDouble("ltime")) || (ftime > rs.getDouble("ftime") && ltime > rs.getDouble("ltime")))) {
                    b = false;
                    return b;
                }

            }
        }

        return b;
    }

    public void setText(int id, String name) {
        this.id = id;
        this.ClientName = name;
    }

}
