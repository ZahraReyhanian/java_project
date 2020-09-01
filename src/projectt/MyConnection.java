package projectt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyConnection {

    public static Connection getConnection() {

        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/java_classpluss", "root", "");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return con;
    }

}
