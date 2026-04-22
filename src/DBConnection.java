import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    DBConnection(){

        String url = "jdbc:oracle:thin:@worf.radford.edu:1521/ITEC3.radford.edu";
        String user = "bjdavis";
        String password = "23179001243927B#b";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }



}