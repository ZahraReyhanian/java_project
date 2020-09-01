/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AVAJANG
 */
public class UpdateUserController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField userName;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private TextField uni;
    @FXML
    private TextField study;
    @FXML
    private PasswordField pass;
    @FXML
    private ComboBox<String> choicebox;
    
    private int id;
    private boolean imageFileChanged;
    private File imageFile;
    @FXML
    private ImageView imgView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }    

    @FXML
    private void exit_edit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void edit(ActionEvent event) throws SQLException, FileNotFoundException, IOException {
        if(name.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Enter the name");
        }
        else if(userName.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Enter the username");
        }
        else if(pass.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Enter the password");
        }
        else if(pass.getText().length()<5){
            JOptionPane.showMessageDialog(null,"Password shoud contain atleast 5 character");
        }
        else if(email.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Enter the email");
        }
        else if(!isValidEmail(email.getText())){
            JOptionPane.showMessageDialog(null,"Enter the valid email");
        }else{
        PreparedStatement ps=null;
        
        if(imageFileChanged){

        String query = "UPDATE user SET name=?, user=?, pass=?, email=?, gender=?, study=? ,uni=? ,city=? ,image=? "+
                "WHERE id = ?";
        ps = MyConnection.getConnection().prepareStatement(query);
        ps.setString(1, name.getText());
        ps.setString(2, userName.getText());
        ps.setString(3, pass.getText());
        ps.setString(4, email.getText());
        ps.setString(5, choicebox.getValue());
        ps.setString(6, study.getText());
        ps.setString(7, uni.getText());
        ps.setString(8, city.getText());
        
        
        InputStream fis = new FileInputStream(this.imageFile.getPath());
                
            try {
            ps.setBlob(9,fis);
           
            } catch (Exception e) {
            System.out.println("Exception: ==> " + e);
            }
            
        ps.setInt(10 , id);
        }else{
            String query = "UPDATE user SET name=?, user=?, pass=?, email=?, gender=?, study=? ,uni=? ,city=? "+
                "WHERE id = ?";
        ps = MyConnection.getConnection().prepareStatement(query);
        ps.setString(1, name.getText());
        ps.setString(2, userName.getText());
        ps.setString(3, pass.getText());
        ps.setString(4, email.getText());
        ps.setString(5, choicebox.getValue());
        ps.setString(6, study.getText());
        ps.setString(7, uni.getText());
        ps.setString(8, city.getText());
        ps.setInt(9, id);
        }
        
        if(ps.executeUpdate() >0){
            JOptionPane.showMessageDialog(null, "Update is done");
        }else{
            JOptionPane.showMessageDialog(null, "Update isn't done");
            
        }
        FXMLLoader Loader=new FXMLLoader();
               Loader.setLocation(getClass().getResource("MainMenu.fxml"));
               
               try{
               Loader.load();
               }catch(IOException ex){
                   Logger.getLogger(UpdateUserController.class.getName()).log(Level.SEVERE,null,ex);
               }
               
               MainMenuController mainM=Loader.getController();
               
               mainM.setText(id);
               
               Parent p=Loader.getRoot();
               
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(new Scene(p));
         window.show();
        }
            
        
    }

    @FXML
    private void return_edit(ActionEvent event) throws IOException, SQLException {
        FXMLLoader Loader=new FXMLLoader();
               Loader.setLocation(getClass().getResource("MainMenu.fxml"));
               
               try{
               Loader.load();
               }catch(IOException ex){
                   Logger.getLogger(UpdateUserController.class.getName()).log(Level.SEVERE,null,ex);
               }
               
               MainMenuController mainM=Loader.getController();
               
               mainM.setText(id);
               
               Parent p=Loader.getRoot();
               
                Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
         window.setScene(new Scene(p));
         window.show();
    }

     public  boolean isNumeric(String s) {
        
    return java.util.regex.Pattern.matches("\\d+", s);
    }
     
   
    public void setText(int id) throws IOException {
    this.id=id;
    System.out.println(id);
    imageFileChanged=false;
    choicebox.getItems().add("زن");
    choicebox.getItems().add("مرد");
        try {
            fillTexts();
        } catch (SQLException ex) {
            Logger.getLogger(UpdateUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillTexts() throws SQLException, FileNotFoundException, IOException {
        PreparedStatement ps;
        ResultSet rs;
        
        String query = "SELECT * FROM user WHERE id=? ";
        
            ps = MyConnection.getConnection().prepareStatement(query);
            
            ps.setInt(1, id);
            
            rs=ps.executeQuery();
            System.out.println(id);
      if(rs.next()){
          this.name.setText(rs.getString("name"));
          this.choicebox.setValue(rs.getString("gender"));
          this.email.setText(rs.getString("email"));
          this.city.setText(rs.getString("city"));
          this.pass.setText(rs.getString("pass"));
          this.study.setText(rs.getString("study"));
          this.uni.setText(rs.getString("uni"));
          this.userName.setText(rs.getString("user"));
          
          InputStream is = rs.getBinaryStream("image");
          OutputStream os = new FileOutputStream(new File("img.jpg"));
          byte [] content = new byte [1024];
          int size =0;
           while((size=is.read(content))!=-1){
               os.write(content, 0, size);
               
           }
           os.close();
           is.close();
           javafx.scene.image.Image image1 = new Image("file:img.jpg",imgView.getFitWidth(),imgView.getFitHeight(),true,true);
                     
          this.imgView.setImage(image1);
          ////////////////////////////////
          ////////////////////////////////
      }
    }

    @FXML
    private void changeImg(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        
        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);
        
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        
        
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));
        
        fileChooser.setInitialDirectory(userDirectory);
        
        
        File tmpImageFile = fileChooser.showOpenDialog(stage);
        
        if (tmpImageFile != null)
        {
            imageFile = tmpImageFile;
        
            if (imageFile.isFile())
            {
                try
                {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    imgView.setImage(img);
                    imageFileChanged = true;
                }
                catch (IOException e)
                {
                    System.err.println(e.getMessage());
                }
            }
        }
        
    }

    private boolean isValidEmail(String email) {
           String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
           return email.matches(regex);
    }
    
}
