/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectt;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Pc.unique
 */
public class TablesController implements Initializable {

    @FXML
    private HBox hbox;

    private int id;
    private int count = 1, i;
    private String[] days = {"sat", "sun", "mon", "tue", "wed", "thu", "fri"};
    private Date[] dates = new Date[7];
    private Program programs[];
    private Pane[] pane = new Pane[7];
    private Label[] label;
    private MyConnection connect;
    @FXML
    private Label D1;
    @FXML
    private Label D2;
    @FXML
    private Label D3;
    @FXML
    private Label D4;
    @FXML
    private Label D5;
    @FXML
    private Label D6;
    @FXML
    private Label D7;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void today(ActionEvent event) throws SQLException {
        if (count > 1) {
            count = 1;
            find();
        }

    }

    @FXML
    private void menu(ActionEvent event) throws SQLException, IOException, Exception {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(TablesController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController mainMenu = Loader.getController();
        mainMenu.setText(id);

        Parent p = Loader.getRoot();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    @FXML
    private void previousPage(ActionEvent event) throws SQLException {
        if (count > 1) {
            count--;
            find();
        }

    }

    @FXML
    private void nextPage(ActionEvent event) throws SQLException {
        if (count < 4) {
            count++;
            find();
        }

    }

    public void setText(int id) throws SQLException {
        this.id = id;
        find();
    }

    public void find() throws SQLException {

        if (count == 1) {
            dates[0] = new Date(119, 5, 1);
            dates[1] = new Date(119, 5, 2);
            dates[2] = new Date(119, 5, 3);
            dates[3] = new Date(119, 5, 4);
            dates[4] = new Date(119, 5, 5);
            dates[5] = new Date(119, 5, 6);
            dates[6] = new Date(119, 5, 7);

            D1.setText("1");
            D2.setText("2");
            D3.setText("3");
            D4.setText("4");
            D5.setText("5");
            D6.setText("6");
            D7.setText("7");
        } else if (count == 2) {

            dates[0] = new Date(119, 5, 8);
            dates[1] = new Date(119, 5, 9);
            dates[2] = new Date(119, 5, 10);
            dates[3] = new Date(119, 5, 11);
            dates[4] = new Date(119, 5, 12);
            dates[5] = new Date(119, 5, 13);
            dates[6] = new Date(119, 5, 14);

            D1.setText("8");
            D2.setText("9");
            D3.setText("10");
            D4.setText("11");
            D5.setText("12");
            D6.setText("13");
            D7.setText("14");
        } else if (count == 3) {
            dates[0] = new Date(119, 5, 15);
            dates[1] = new Date(119, 5, 16);
            dates[2] = new Date(119, 5, 17);
            dates[3] = new Date(119, 5, 18);
            dates[4] = new Date(119, 5, 19);
            dates[5] = new Date(119, 5, 20);
            dates[6] = new Date(119, 5, 21);

            D1.setText("15");
            D2.setText("16");
            D3.setText("17");
            D4.setText("18");
            D5.setText("19");
            D6.setText("20");
            D7.setText("21");
        } else if (count == 4) {
            dates[0] = new Date(119, 5, 22);
            dates[1] = new Date(119, 5, 23);
            dates[2] = new Date(119, 5, 24);
            dates[3] = new Date(119, 5, 25);
            dates[4] = new Date(119, 5, 26);
            dates[5] = new Date(119, 5, 27);
            dates[6] = new Date(119, 5, 28);

            D1.setText("22");
            D2.setText("23");
            D3.setText("24");
            D4.setText("25");
            D5.setText("26");
            D6.setText("27");
            D7.setText("28");
        }

        hbox.getChildren().clear();
        for (i = 0; i < 7; i++) {
            int t = 0;
            programs = new Program[100];

            String[] type = {"homework", "exam" , "request"};

            for (int l = 0; l < 3; l++) {
                Connection con = null;
                Statement ps = null;
                ResultSet rs = null;

                con = connect.getConnection();
                ps = con.createStatement();
                String query = "SELECT * FROM " + type[l] + " WHERE userID='" + id + "' AND date='" + dates[i] + "'";

                try {
                    rs = ps.executeQuery(query);
                    while (rs.next()) {
                        programs[t] = new Program(rs.getString("color"), rs.getString("subject"),
                                rs.getDouble("ftime"), rs.getDouble("ltime"));
                        
                        t++;
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (rs!= null
                    ) try {
                        rs.close();
                    } catch (SQLException e) {
                    }
                    if (ps!= null
                    ) try {
                        ps.close();
                    } catch (SQLException e) {
                    }
                    if (con!= null
                    ) try {
                        con.close();
                    } catch (SQLException e) {
                    }

                }

            }
            PreparedStatement pss = null;
            ResultSet rss;

            String queryy = "SELECT * FROM classp WHERE userID=? ";

            try {
                pss = MyConnection.getConnection().prepareStatement(queryy);
                pss.setInt(1, id);

                rss = pss.executeQuery();
                while (rss.next()) {
                    if (rss.getInt(days[i]) == 1) {
                        programs[t] = new Program(rss.getString("color"), rss.getString("subject"),
                                rss.getDouble("ftime"), rss.getDouble("ltime"));

                        t++;

                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

            }
            print(programs, i, t);
        }

    }

    public void print(Program programs[], int i, int t) {
        this.pane[i] = new Pane();
        pane[i].setPrefHeight(626);
        pane[i].setPrefWidth(62.25);
        this.hbox.getChildren().add(pane[i]);
        this.label = new Label[programs.length];
        for (int m = 0; m < t; m++) {
            label[m] = new Label(programs[m].getSubject());
            pane[i].getChildren().add(label[m]);
            double height = (programs[m].getLtime() - programs[m].getFtime()) * 30;
            label[m].setPrefHeight(height);
            label[m].setPrefWidth(60);
            label[m].setStyle("-fx-background-color :" + programs[m].getColor());
            Font font = new Font(16);
            label[m].setFont(font);
            label[m].setTextFill(Color.WHITE);
            label[m].setLayoutX(0);
            label[m].setLayoutY(programs[m].getFtime() * 26);

        }
    }

}
