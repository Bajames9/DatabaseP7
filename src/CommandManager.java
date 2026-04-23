import java.util.ArrayList;

public class CommandManager {


    Explorer explorer;
    DBConnection db = new DBConnection();



    public void help(){


        System.out.println("Command List");
        System.out.println("\n");
        System.out.println("key:");
        System.out.println("user input data -> [parameter]");
        System.out.println("| alternative input -> [option 1 | option 2] ");
        System.out.println("Note: only input one option | just denotes a command takes multiple types of input");


        System.out.println("\n");
        System.out.println("/new [Username] [Name]");
        System.out.println("Note: Starts a new game and logs in as User using Username and Player Name");
        System.out.println("\n");
        System.out.println("/move [room]");
        System.out.println("Note: moves logged in player to a new room");
        System.out.println("\n");
        System.out.println("/grab [TreasureID | TreasureName]");
        System.out.println("Note: grabs a treasure from current room");
        System.out.println("\n");
        System.out.println("/drop [TreasureID | TreasureName]");
        System.out.println("Note: drops a Treasure in the current room");


    }

    public void newGame(String[] input){

        explorer = null;
        if (input.length >= 3)
        {
            explorer = db.getExplorer(input[1],input[2]);
        }

        if (explorer != null)
        {
            System.out.println(explorer.getName() + " is ready to play");
            displayGameState();

        }
        else {
            System.out.println("Error: explorer does not exist");
        }



    }

    public void move(String[] input){
        /*
        if (explorer == null){
            System.out.println("game not started");
            return;
        }
        if (input.length < 3) //change to lenght of id of room i guess it 3 
        {
            System.out.println("error missing room identifier");
            return;
        }
        int roomId = Integer.parseInt(input[1]);
        ArrayList<Integer> connectedRooms = db.getConnectedRooms(explorer.getRoomId());
        if (connectedRooms == null || !connectedRooms.contains(roomId)){
            System.out.println("error room not connected to current room");
            return;
        }
        try{
            db.Move(explorer, roomId);
            explorer = db.updateExplorer(explorer);
            //System.out.println("moved to room "+ roomId);
            displayGameState();
        }
        catch (Exception e){
            System.out.println("error moving to room");
        }

        */

    }

    public void grab(String[] input){
        /*int tresId = Integer.parseInt(input[1]);

        if(explorer == null){
            System.out.println("game not started");
            return;
        }
        if (input.length < 4) //change to lenght of id of treasure 
         {
            System.out.println("error missing treasure identifier");
            return;
        }
        
       
        try{
        db.Grab(explorer, tresId);
        explorer = db.updateExplorer(explorer);
        displayGameState();
    }
        catch (Exception e){
            System.out.println("error grabbing treasure");
        }
        */
    }

    public void drop(String[] input){
        /*int tresId = Integer.parseInt(input[1]);

        if(explorer == null){
            System.out.println("game not started");
            return;
        }
        if (input.length < 4) //change to lenght of id of treasure 
         {
            System.out.println("error missing treasure identifier");
            return;
        }
        
       
        try{
        db.Drop(explorer, tresId);
        explorer = db.updateExplorer(explorer);
        //System.out.println("dropped treasure "+ tresId);
        displayGameState();
    }
        catch (Exception e){
            System.out.println("error dropping treasure");
         }
         */

    }

    public void displayGameState() {

        explorer = db.updateExplorer(explorer);
        ArrayList<Treasure> explorerBag = db.getTreasuresForExplorer(explorer);
        ArrayList<Treasure> roomTreasures = db.getTreasuresForRoom(explorer.getRoomId());
        ArrayList<Integer> connectedRooms = db.getConnectedRooms(explorer.getRoomId());
        System.out.println(explorer.getName()+" is in room (" + explorer.getRoomId()+")");
        System.out.println("\tTreasures");
        if (explorerBag != null) {
            for (Treasure t : explorerBag) {
                System.out.println("\t\t" + t.getName() + "(" + t.getTresID() + ") VAL:" + t.getValue() + " WT:" + t.getWeight());
            }
        }
        System.out.println("\n");
        System.out.println("Room "+explorer.getRoomId());
        System.out.println("\tTreasures");
        if (roomTreasures != null){
        for(Treasure t:roomTreasures){
            System.out.println("\t\t" + t.getName() + "("+ t.getTresID()+") VAL:" + t.getValue() + " WT:" + t.getWeight());
        }
        }

        StringBuilder connections = new StringBuilder();
        connections.append("\tConnections:");
        if (connectedRooms != null){
            for(Integer room:connectedRooms){
             connections.append(" ").append(room);
            }
        }
        System.out.println(connections);


    }





}
