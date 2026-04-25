import java.sql.*;
import java.util.ArrayList;

public class test {
    static CommandManager cm = new CommandManager(); 
    static DBConnection db = new DBConnection();


  public static void main(String[] args) {
    DBConnection db = new DBConnection();

    String[] fail = {"new", "Kade", "NotKade"};
    String[] success = {"new", "Kade", "Kade"}; 
    testNew(fail); 
    testNew(success); 
  }

    public static void testNew(String[] input) {

        
        Explorer explorer = null; 

        if (input.length >= 3)
        {
            explorer = db.getExplorer(input[1],input[2]);

        }
        if (explorer != null)
        {
            System.out.println(explorer.getName() + " Pass");
            cm.displayGameState();

        }
        else {
            System.out.println("Fail");
        }

    }


    /*Create
    // name >= 3 -Pass
     public static void testGamePass()
     {

        String[] input = {"new", "Joseph", "troll1"}; 
        if (input.length >= 3)
        {
            explorer1 = db.getExplorer(input[1],input[2]);
            System.out.println("Passed");
        }
        if (explorer1 != null)
        {
            System.out.println(explorer1.getName() + " is ready to play");
            displayGameState();

        }
        else
        {
            System.out.println("Failed")
        }
    }
        
        
    public static void testGameFail1()
    {
        String[] input = {"new", "Tom"};
         if (input.length >= 3)
        {
            System.out.println("Pass");
        }
        else
            {
                System.out.println("Failed");
            }
       

    }

    public void testNewFail2()
    {
        String[] input = {null,null,null};
        if (explorer1 != null)
        {
            System.out.println("Pass");
            
        }
        else
        {
                System.out.println("Failed");
        }
    }

    
    //name<3 - Fail
    //Move
    //Move to a room that exists and is connected to your room - Pass
    //Move to a room that isnt connected - Fail
    //Move to a room that doesnt exist - Fail

    //Grab
    //Grab treasure that does exsist and is in the same room as exp - Paaa
    // Grab treasure that is not in same room as exp - Fail
    //Grab treasure that doesnt exist - Fail
    //Grab a treasure that fails the wtcnt, too much weight - Fail
    //Grab a treasure that fails the bgcnt, too many items - Fail
    //Grab treasure 
    //Drop
    //Drop treasure that is in inventory - Pass
    //-Drop treasure that isnt in inventory - Fail
    
*/







}
