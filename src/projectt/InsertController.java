package projectt;

import com.mysql.cj.protocol.Resultset;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import static javafx.scene.AccessibleAttribute.VALUE;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class InsertController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField userName;
    @FXML
    private TextField study;
    @FXML
    private PasswordField pass;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private TextField uni;
    @FXML
    private AnchorPane mainparent;
    @FXML
    private ComboBox<String> choicebox;
    @FXML
    private Label imgLable;
    @FXML
    private ImageView imgView;

    private File imageFile
            = imageFile = new File("./src/images/defaultPerson.jpg");
    private boolean imageFileChanged;
    private User user;
    private int id;

    @FXML
    private void exit_register(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void register(ActionEvent event) throws IOException {
        if (name.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the name");
        } else if (userName.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the username");
        } else if (pass.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the password");
        } else if (pass.getText().length() < 5) {
            JOptionPane.showMessageDialog(null, "Password shoud contain atleast 5 character");
        } else if (email.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Enter the email");
        } else if (!isValidEmail(email.getText())) {
            JOptionPane.showMessageDialog(null, "Enter the valid email");
        } else {
            user = new User();
            user.setName(name.getText());
            user.setUser_name(userName.getText());
            user.setPassword(pass.getText());
            user.setStudy(study.getText());
            user.setCity(city.getText());
            user.setUni(uni.getText());
            user.setEmail(email.getText());

            if (choicebox.getValue() == null) {
                user.setGender("");
            } else {
                user.setGender(choicebox.getValue());
            }

            if (imageFileChanged) {
                user.setImageFile(imageFile);
            } else {
                imageFile = new File("./src/img/defaultPerson.jpg");
            }

            PreparedStatement ps;
            String query = "INSERT INTO user (name,user,pass,email,gender,study,uni,city,image)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            try {
                ps = MyConnection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, user.getName());
                ps.setString(2, user.getUser_name());
                ps.setString(3, user.getPassword());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getGender());
                ps.setString(6, user.getStudy());
                ps.setString(7, user.getUni());
                ps.setString(8, user.getCity());

                InputStream fis = new FileInputStream(this.imageFile.getPath());

                try {
                    ps.setBlob(9, fis);

                } catch (Exception e) {
                    System.out.println("Exception: ==> " + e);
                }

                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "New User Add");
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            id = generatedKeys.getInt(1);
                        }

                    }

                    FXMLLoader Loader = new FXMLLoader();
                    Loader.setLocation(getClass().getResource("MainMenu.fxml"));

                    try {
                        Loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    MainMenuController mainMenu = Loader.getController();
                    mainMenu.setText(id);

                    Parent p = Loader.getRoot();
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(new Scene(p));
                    window.show();
                }

            } catch (SQLException ex) {
                Logger.getLogger(InsertController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    @FXML
    private void return_register(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("menu1.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageFileChanged = false;
        choicebox.getItems().add("woman");
        choicebox.getItems().add("man");

    }

    @FXML
    private void ButtonImg(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("Image File (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("Image File (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFilter, pngFilter);

        String userDirectoryString = System.getProperty("user.home") + "\\Pictures";
        File userDirectory = new File(userDirectoryString);

        if (!userDirectory.canRead()) {
            userDirectory = new File(System.getProperty("user.home"));
        }

        fileChooser.setInitialDirectory(userDirectory);

        File tmpImageFile = fileChooser.showOpenDialog(stage);

        if (tmpImageFile != null) {
            imageFile = tmpImageFile;

            if (imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    imgView.setImage(img);
                    imageFileChanged = true;
                } catch (IOException e) {
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
