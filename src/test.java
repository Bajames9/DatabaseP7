import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class test {
    static CommandManager cm = new CommandManager();
    static DBConnection db = new DBConnection();



    public static void main(String[] args) {


        PrintStream originalOut = System.out;

        PrintStream consumeOut = (new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));







        String[] failN = {"new", "Kade", "NotKade"};
        String[] successN = {"new", "Kade", "Kade"};
        testNew(failN);
        testNew(successN);


 
        Explorer testExplorer = db.getExplorer("test","test");
        cm.explorer = testExplorer;
        String[] failM = {"move","111"}; //Room that is not connected
        String[] failM2 = {"move","101"}; //Room the Explorer is already in
        String[] failM3 = {"move","555"}; //Room that doesnt Exist
        String[] successM = {"move","105"}; //Room that is connected
        testMove(failM);
        testMove(failM2);
        testMove(failM3);
        testMove(successM);


        String[] failG = {"grab","500"}; // Treasure that is not in the room
        String[] successG = {"grab","109"}; // Treasure that is in the room(its not we are in roomid 105 now, change when room 105 treasure id is found)
        String[] failG2 = {"grab","109"}; // Treasure is not on the ground but in inventory already
        testGrab(failG);
        testGrab(successG);


        System.setOut(consumeOut);
        cm.grab(successG);
        System.setOut(originalOut);
        testGrab(failG2);



        String[] failD = {"drop","500"}; // Treasure that the explorer doesnt hold
        String[] successD = {"drop","109"}; //Treasure the explorer does hold
        testDrop(failD);

        System.setOut(consumeOut);
        cm.grab(successG);
        System.setOut(originalOut);

        testDrop(successD);

        /*
        String[] failM = {"move 111"}; //Room that is not connected
        String[] failM2 = {"move 101"}; //Room the Explorer is already in
        String[] failM3 = {"move 555"}; //Room that doesnt Exist
        String[] successM = {"move 105"}; //Room that is connected
        testMove(failM);
        testMove(failM2);
        testMove(failM3);
        testMove(successM);
        String[] failG = {"grab 500"}; // Treasure that is not in the room
        String[] successG = {"grab 102"}; // Treasure that is in the room(its not we are in roomid 105 now, change when room 105 treasure id is found)
        String[] failG2 = {"grab 102"}; // Treasure is not on the ground but in inventory already
        testGrab(failG);
        testGrab(successM);
        testGrab(failG2);
        String[] failD = {"drop 500"}; // Treasure that the explorer doesnt hold
        String[] successD = {"drop 102"}; //Treasure the explorer does hold
        testDrop(failD);
        testDrop(successD);

         */
    }


    public static void testNew(String[] input) {


       // used to capture system.out from function
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        

        if( cm.newGame(input)){
            cm.explorer = null;
            System.setOut(originalOut);
            System.out.println("Pass");

        } else {
            System.setOut(originalOut);
            System.out.println("Fail");
        }

    }


    public static void testMove( String[] command) {


        // used to capture system.out from function
        PrintStream originalOut = System.out;

        final String[] failMsg = new String[1];

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {

            }
        }));

        
        String[] returnCMD = {"move",cm.explorer.getRoomId()+""};


        if( cm.move(command)){
            cm.move(returnCMD);
            System.setOut(originalOut);
            System.out.println("Pass");

        } else {
            System.setOut(originalOut);
            System.out.println("Fail");
        }


    }

    /*



    public static void testMove(String[] input) {
        Explorer explorer1 = null;
        if (explorer1 == null) {
            System.out.println("game not started");
            return;
        }
        if (input.length != 2) //change to lenght of id of room i guess it 3
        {
            System.out.println("error invalid cmd structure");
            return;
        }
        int roomId = Integer.parseInt(input[1]);
        ArrayList<Integer> connectedRooms = db.getConnectedRooms(explorer1.getRoomId());
        if (connectedRooms == null || !connectedRooms.contains(roomId)) {
            System.out.println("error room not connected to current room, Fail");
            return;
        }
        try {
            db.Move(explorer1, roomId);
            explorer1 = db.updateExplorer(explorer1);
            System.out.println("Pass ");
            cm.displayGameState();
        } catch (Exception e) {
            System.out.println("error moving to room, Fail");
        }

    }

     */

    public static void testGrab( String[] command) {


        // used to capture system.out from function
        PrintStream originalOut = System.out;



        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));






        String[] returnCMD = {"drop",command[1]};


        // changed cmds to grab from move
        if( cm.grab(command)){
            cm.drop(returnCMD);
            System.setOut(originalOut);
            System.out.println("Pass");

        } else {
            System.setOut(originalOut);
            System.out.println("Fail");
        }


    }

    /*
    public static void testGrab(String[] input) {
        Explorer explorer2 = null;
        int tresId = Integer.parseInt(input[1]);

        if (explorer2 == null) {
            System.out.println("game not started");
            return;
        }


        if (input.length != 2) //change to lenght of id of treasure
        {
            System.out.println("error invalid cmd structure");
            return;
        }


        try {
            db.Grab(explorer2, tresId);
            explorer2 = db.updateExplorer(explorer2);
            System.out.println("Pass");
            cm.displayGameState();
        } catch (Exception e) {
            System.out.println("error grabbing treasure, Fail");
        }

    }
    */


     public static void testDrop( String[] command) {


        // used to capture system.out from function
        PrintStream originalOut = System.out;

        System.setOut(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


         String[] returnCMD = {"grab",command[1]};

        // changed cmds to grab from move
        if( cm.drop(command)){
            cm.drop(returnCMD);
            System.setOut(originalOut);
            System.out.println("Pass");

        } else {
            System.setOut(originalOut);
            System.out.println("Fail");
        }


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




    







