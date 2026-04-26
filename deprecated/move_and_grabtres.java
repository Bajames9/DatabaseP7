import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;
/*
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

public class Game {

    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Type /grab <treasureName> to grab a treasure or Type /move to move your Explorer to a New Room.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.startsWith("/grab ")) 
            {
                String treasureName = input.substring(6).trim();
                if (treasureName.isEmpty())
                {
                    System.out.println("Use a command");
                } 
                else 
                {
                    grabTreasure(treasureName);
                }
            } 
            if (input.startsWith("/move ")) 
            {
                String newRoomID = input.substring(6).trim();
                if (newRoomID.isEmpty())
                {
                    System.out.println("Use a command");
            } 
            else 
            {
                moveExplorer(expID, newRoomID);
            }
            }
            else if (input.equals("/quit")) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Unknown command. Try /grab <treasureName> or /quit");
            }
        }
    }


}
}

    private static String grabTreasure(String treasureName) {
        String url  = "jdbc:oracle:thin:@worf.radford.edu:1521/ITEC3.radford.edu";
        String user = "bjdavis";
        String pass = "23179001243927B#b";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             CallableStatement cs = conn.prepareCall("{call grab_treasure(?, ?)}")) {

            cs.setString(1, treasureName);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();

            return cs.getString(2);

        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }

     private static String moveExplorer(String movExp) {
        String url  = "jdbc:oracle:thin:@worf.radford.edu:1521/ITEC3.radford.edu";
        String user = "bjdavis";
        String pass = "23179001243927B#b";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             CallableStatement mvExp = conn.prepareCall("{call move_Explorer(?, ?)}")) {

            mvExp.setString(1, expID);
            mvExp.registerOutParameter(2, newRoomID);
            mvExp.execute();

            System.out.println("Explorer moved sucessfully")

        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }
}

 */