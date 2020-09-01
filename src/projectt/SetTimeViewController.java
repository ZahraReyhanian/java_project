
package projectt;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class SetTimeViewController implements Initializable {

    

    private int id;
    @FXML
    private ComboBox<Integer> hour;
    @FXML
    private ComboBox<Integer> minute;
    @FXML
    private ComboBox<Integer> second;
    
    private int hours;
    private int minutes;
    private int seconds;
    private String lesson;
    
    @Override
    @SuppressWarnings("empty-statement")
    public void initialize(URL url, ResourceBundle rb) {
        Integer[] arr={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59};
        minute.getItems().addAll(arr);
        second.getItems().addAll(arr);
        Integer[] arr2={0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
        hour.getItems().addAll(arr2);
    }    

    @FXML
    private void OkButton(ActionEvent event) {
        try{
            hours=hour.getValue();
        }catch(Exception e){
            hours=0;
        }
        try{
            minutes=minute.getValue();
        }catch(Exception e){
            minutes=0;
        }
        try{
            seconds=second.getValue();
        }catch(Exception e){
            seconds=0;
        }
        
        
        FXMLLoader Loader=new FXMLLoader();
               Loader.setLocation(getClass().getResource("Timer.fxml"));
               
               try{
               Loader.load();
               }catch(IOException ex){
                   Logger.getLogger(SetTimeViewController.class.getName()).log(Level.SEVERE,null,ex);
               }
               
               TimerController timer=Loader.getController();
               
               timer.setText(id,hours,minutes,seconds,lesson);
               
               Parent p=Loader.getRoot();
               
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(new Scene(p));
         window.show();
    }

    void setText(int id,String lesson) {
        this.id=id;
        this.lesson=lesson;
    }

    
    
}
