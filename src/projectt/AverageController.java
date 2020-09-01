/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectt;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AVAJANG
 */
public class AverageController implements Initializable {

    @FXML
    private GridPane gridPane;

    private int id;
    private TextField[] grade;
    private Label[] unit;
    private double average = 0;
    private int num;
    private int u[];
    @FXML
    private AnchorPane mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Average(ActionEvent event) {
        int s = 0;
        boolean flag = false;
        for (int i = 0; i < num; i++) {
            try {
                double mark = Double.parseDouble(grade[i].getText());
                if(mark>20){
                    mark=20;
                }
                if(mark<0){
                    mark=0;
                }
                average += (mark * u[i]);
                s += u[i];

            } catch (Exception e) {
                flag = true;
                average = 0;
                s = 0;
                break;
            }
        }
        if (!flag) {
            JOptionPane.showMessageDialog(null, "Your average is : " + average / s);
            average = 0;
            s = 0;
        } else {
            JOptionPane.showMessageDialog(null, "نمره باید عدد باشد و نباید خالی باشد");
        }
    }

    @FXML
    private void returnMenu(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("MainMenu.fxml"));

        try {
            Loader.load();
        } catch (IOException ex) {
            Logger.getLogger(AverageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        MainMenuController classP = Loader.getController();

        classP.setText(id);

        Parent p = Loader.getRoot();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(p));
        window.show();
    }

    public void setText(int id) throws SQLException {
        this.id = id;
        findLesson();
    }

    public void findLesson() throws SQLException {

        PreparedStatement ps = null;
        ResultSet rs;
        Lesson[] lsn = new Lesson[100];

        // indexOfLesson=-1;
        String query = "SELECT * FROM lesson WHERE userID=? ";

        try {
            ps = MyConnection.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Label[] label = new Label[100];
            unit = new Label[100];
            grade = new TextField[100];
            u = new int[100];
            int i = -1;
            while (rs.next()) {
                i++;
                unit[i] = new Label(rs.getString(5));
                unit[i].setMaxHeight(40);
                unit[i].setMaxWidth(165);
                Font font = new Font(16);
                unit[i].setFont(font);
                unit[i].setAlignment(Pos.CENTER);
                u[i] = Integer.parseInt(rs.getString(5));

                label[i] = new Label(rs.getString(2));
                label[i].setStyle("-fx-background-color :" + rs.getString(4));
                label[i].setMaxHeight(40);
                label[i].setMaxWidth(165);
                label[i].setFont(font);
                label[i].setTextFill(Color.WHITE);
                label[i].setAlignment(Pos.CENTER);

                grade[i] = new TextField();
                grade[i].setMaxHeight(40);
                grade[i].setMaxWidth(165);
                grade[i].setAlignment(Pos.CENTER);

                gridPane.add(label[i], 2, i + 1);
                gridPane.add(unit[i], 1, i + 1);
                gridPane.add(grade[i], 0, i + 1);
                num = i + 1;
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ScrollPane sp = new ScrollPane(this.gridPane);
        sp.setPrefSize(490, 360);
        sp.setLayoutX(17);
        sp.setLayoutY(116);
        this.mainPane.getChildren().add(sp);

    }

}
