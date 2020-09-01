
package projectt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class User {
    private String name;
    private String user_name;
    private String password;
    private String gender;
    private String study;
    private String city;
    private String uni;
    private String email;
    private File imageFile;
    
    public User(){
        setImageFile(new File("./src/img/defaultPerson.jpg"));
    }
    
     public void copyImageFile() throws IOException
    {
        //create a new Path to copy the image into a local directory
        Path sourcePath = imageFile.toPath();
        
        
        Path targetPath = Paths.get("./src/images/"+imageFile.getName());
        
        //copy the file to the new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        //update the imageFile to point to the new File
        imageFile = new File(targetPath.toString());
    }
    
    public String getName(){
        return name;
    }
    public String getUser_name(){
        return user_name;
    }
    public String getPassword(){
        return password;
    }
    public String getGender(){
        return gender;
    }
    public String getStudy(){
        return study;
    }
    public String getCity(){
        return city;
    }
    public String getUni(){
        return uni;
    }
    public String getEmail(){
        return email;
    }
    
    public void setName (String name){
        this.name=name;
    }
    
    public void setUser_name (String user_name){
        this.user_name=user_name;
    }
    
    public void setPassword (String password){
        this.password=password;
    }
    
    public void setGender (String gender){
        this.gender=gender;
    }
    
    public void setStudy (String study){
        this.study=study;
    }
    
    public void setCity (String city){
        this.city=city;
    }
    
    public void setUni (String uni){
        this.uni=uni;
    }
    
    public void setEmail (String email){
        this.email=email;
    }

    public void setImageFile(File file) {
        try{
        this.imageFile = file;
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public File getImageFile(){
        return imageFile;
    }
    
    
    
}
