package projectt;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PrograamSelect {

    private Button[] button = new Button[500];
    private int id;
    private int i = -1;

    public PrograamSelect(int id) {
        this.id = id;
    }

    public void selectHmomeWork() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM homework WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                HomeWork homework = new HomeWork(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(),
                        rs.getString("note"), rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));

                button[i] = new Button(homework.getSubject() + "\nlesson:" + homework.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");

                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(homework, id, "homework", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectExam() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM exam WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                Exam exam = new Exam(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(), rs.getString("note"),
                        rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));
                button[i] = new Button(exam.getSubject() + "\nlesson: " + exam.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(exam, id, "exam", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectClass() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM classp WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                i++;
                ClassP classp = new ClassP(rs.getString("lesson"), rs.getString("subject"), rs.getDouble("ltime"), rs.getDouble("ftime"), rs.getString("note"),
                        rs.getString("color"), rs.getInt("userID"));
                String days = "";
                if (rs.getInt("sat") == 1) {
                    days += "SATURDAY , ";
                }
                if (rs.getInt("sun") == 1) {
                    days += "SUNDAY , ";
                }
                if (rs.getInt("mon") == 1) {
                    days += "MONDAY , ";
                }
                if (rs.getInt("tue") == 1) {
                    days += "TUESDAY , ";
                }
                if (rs.getInt("wed") == 1) {
                    days += "WEDNESDAY , ";
                }
                if (rs.getInt("thu") == 1) {
                    days += "THURSDAY , ";
                }
                classp.setDaysWeek(days);
                button[i] = new Button(classp.getSubject() + "\nlesson: " + classp.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(classp, id, programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectTodayHmomeWork() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        // indexOfLesson=-1;
        String query = "SELECT * FROM homework WHERE userID=? AND date='2019-06-09'";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                HomeWork homework = new HomeWork(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(),
                        rs.getString("note"), rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));
                button[i] = new Button(homework.getSubject() + "\nlesson: " + homework.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);

                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(homework, id, "homework", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectTodayExam() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM exam WHERE userID=? AND date='2019-06-09'";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                Exam exam = new Exam(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(), rs.getString("note"),
                        rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));
                button[i] = new Button(exam.getSubject() + "\nlesson: " + exam.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(exam, id, "exam", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void selectTodayClass() throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM classp WHERE userID=? AND sun='1'";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                i++;
                ClassP classp = new ClassP(rs.getString("lesson"), rs.getString("subject"), rs.getDouble("ltime"), rs.getDouble("ftime"), rs.getString("note"),
                        rs.getString("color"), rs.getInt("userID"));
                classp.setDaysWeek("SATURDAY , ");
                button[i] = new Button(classp.getSubject() + "\nlesson: " + classp.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(classp, id, programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void search(String subject) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs;

        String query = "SELECT * FROM exam WHERE userID=? AND subject=?";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, subject);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                Exam exam = new Exam(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(), rs.getString("note"),
                        rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));
                button[i] = new Button(exam.getSubject() + "\nlesson: " + exam.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(exam, id, "exam", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ps = null;
        query = "SELECT * FROM homework WHERE userID=? AND subject=?";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, subject);
            rs = ps.executeQuery();

            while (rs.next()) {

                i++;
                HomeWork hw = new HomeWork(rs.getString("subject"), rs.getString("lesson"), rs.getDate("date").toLocalDate(),
                        rs.getString("note"), rs.getDouble("ftime"), rs.getDouble("ltime"), rs.getString("day"));
                button[i] = new Button(hw.getSubject() + "\nlesson: " + hw.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(hw, id, "homework", programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ps = null;

        query = "SELECT * FROM classp WHERE userID=? AND subject=?";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, subject);
            rs = ps.executeQuery();

            while (rs.next()) {
                i++;
                ClassP classp = new ClassP(rs.getString("lesson"), rs.getString("subject"), rs.getDouble("ltime"), rs.getDouble("ftime"), rs.getString("note"),
                        rs.getString("color"), rs.getInt("userID"));
                String days = "";
                if (rs.getInt("sat") == 1) {
                    days += "SATURDAY , ";
                }
                if (rs.getInt("sun") == 1) {
                    days += "SUNDAY , ";
                }
                if (rs.getInt("mon") == 1) {
                    days += "MONDAY , ";
                }
                if (rs.getInt("tue") == 1) {
                    days += "TUESDAY , ";
                }
                if (rs.getInt("wed") == 1) {
                    days += "WEDNESDAY , ";
                }
                if (rs.getInt("thu") == 1) {
                    days += "THURSDAY , ";
                }
                classp.setDaysWeek(days);
                button[i] = new Button(classp.getSubject() + "\nlesson: " + classp.getLesson());
                button[i].setStyle("-fx-background-color :" + rs.getString("color"));
                button[i].setPrefHeight(70);
                button[i].setPrefWidth(460);
                button[i].setCursor(Cursor.HAND);
                Font font = new Font(16);
                button[i].setFont(font);
                button[i].setTextFill(Color.WHITE);
                int programID = rs.getInt("id");
                button[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        FXMLLoader Loader = new FXMLLoader();
                        Loader.setLocation(getClass().getResource("ShowProgramInfo.fxml"));

                        try {
                            Loader.load();
                        } catch (IOException ex) {
                            Logger.getLogger(ProgramListController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        ShowProgramInfoController showProgramInfo = Loader.getController();
                        try {
                            showProgramInfo.setText(classp, id, programID);
                        } catch (SQLException ex) {
                            Logger.getLogger(PrograamSelect.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        Parent p = Loader.getRoot();
                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        window.setScene(new Scene(p));
                        window.show();

                    }
                });

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Button[] getButton() throws Exception {
        if (button.equals(null)) {
            System.out.println("Yeeeeess");
        } else {
            System.out.println("Noooooooo");

        }
        return button;
    }

    public int getI() {
        return i;
    }

}
