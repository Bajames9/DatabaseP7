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
        System.out.println("new [Username] [Name]");
        System.out.println("Note: Starts a new game and logs in as User using Username and Player Name");
        System.out.println("\n");
        System.out.println("move [room]");
        System.out.println("Note: moves logged in player to a new room");
        System.out.println("\n");
        System.out.println("grab [TreasureID | TreasureName]");
        System.out.println("Note: grabs a treasure from current room");
        System.out.println("\n");
        System.out.println("drop [TreasureID | TreasureName]");
        System.out.println("Note: drops a Treasure in the current room");


    }

    public boolean newGame(String[] input){

        explorer = null;
        if (input.length >= 3)
        {
            explorer = db.getExplorer(input[1],input[2]);
        }

        if (explorer != null)
        {
            System.out.println(explorer.getName() + " is ready to play");
            displayGameState();
            return true;

        }
        else {
            System.out.println("Error: explorer does not exist");
        }


        return false;
    }

    public boolean move(String[] input){

        if (explorer == null){
            System.out.println("game not started");
            return false;
        }
        if (input.length != 2) //change to lenght of id of room i guess it 3
        {
            System.out.println("error invalid cmd structure");
            return false;
        }
        int roomId = Integer.parseInt(input[1]);
        ArrayList<Integer> connectedRooms = db.getConnectedRooms(explorer.getRoomId());
        if (connectedRooms == null || !connectedRooms.contains(roomId)){
            System.out.println("error room not connected to current room");
            return false;
        }
        try{
            db.Move(explorer, roomId);
            explorer = db.updateExplorer(explorer);
            System.out.println("Moved to room "+ roomId);
            displayGameState();
            return true;
        }
        catch (Exception e){
            System.out.println("error moving to room");
        }

        return false;


    }

    public boolean grab(String[] input){
        int tresId = Integer.parseInt(input[1]);

        if(explorer == null){
            System.out.println("game not started");
            return false;
        }

        /*
        * changed check to avoid errors
        *
        * */
        if (input.length != 2) //change to lenght of id of treasure
         {
            System.out.println("error invalid cmd structure");
             return false;
        }
        
       
        try{
        boolean success = db.Grab(explorer, tresId);
        explorer = db.updateExplorer(explorer);
        System.out.println("Grabbed treasure " + tresId);
        displayGameState();
        return success;
    }
        catch (Exception e){
            System.out.println("error grabbing treasure");
        }

        return false;
    }

    public boolean drop(String[] input){
        int tresId = Integer.parseInt(input[1]);

        if(explorer == null){
            System.out.println("game not started");
            return false;
        }
        if (input.length != 2) //change to lenght of id of treasure
         {
            System.out.println("error invalid cmd structure");
             return false;
        }
        
       
        try{
            boolean success = db.Drop(explorer, tresId);
            explorer = db.updateExplorer(explorer);
            System.out.println("Dropped treasure "+ tresId);
            displayGameState();
            return success;
        }
        catch (Exception e){
            System.out.println("error dropping treasure");
         }


        return false;
    }

    public boolean talk(String[] input) {
        int npcId = Integer.parseInt(input[1]); 

        if(explorer == null){
            System.out.println("game not started");
            return false;
        }

        if (input.length != 2) //change to lenght of id of treasure
         {
            System.out.println("error invalid cmd structure");
            return false;
        }
        String type = db.talk(explorer, npcId);
        if (type.equals("Wizard")) {
            //if NPC IS A WIZARD, HE'S A JERK. He makes you drop all your stuff and teleports you to the first room
            System.out.println("You feel as if you are being turned inside out."); 

            ArrayList<Treasure> explorerBag = db.getTreasuresForExplorer(explorer);
            for (Treasure t: explorerBag) {
                db.Drop(explorer, t.getTresID()); 
            }
            db.Move(explorer, 111); 
            displayGameState();
            return true; 
        }
        return false; 
    }

    public void displayGameState() {

        if(explorer == null){
            System.out.println("game not started");
            return;
        }

        explorer = db.updateExplorer(explorer);
        ArrayList<Treasure> explorerBag = db.getTreasuresForExplorer(explorer);
        ArrayList<Treasure> roomTreasures = db.getTreasuresForRoom(explorer.getRoomId());
        ArrayList<Integer> connectedRooms = db.getConnectedRooms(explorer.getRoomId());
        System.out.println(explorer.getName()+" is in room (" + explorer.getRoomId()+")");
        ArrayList<NPC> npcs = db.getNPCsForRoom(explorer.getRoomId()); 

        System.out.println("Bag Weight: " + explorer.getBagWt() + " Bag Count: " + explorer.getBag_cnt()); 
        
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
        System.out.println("\tNPCS");
        if (npcs != null) {
            for (NPC n : npcs) {
                System.out.println("\t\t" + n.getName() + " (" + n.getNpcId() + ") stands in the room.");
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
    /*
    public void talk(String[] input) {

        if (explorer == null) {
        System.out.println("game not started");
        return;
        }
        Integer npcId = Integer.parseInt(input[1]);

        if (npcId == null)
        {
        System.out.println("error missing npc id");
        System.out.println("use talk [npcID]");
        return;
        }
        try {
        String npcResponse = db.TalkNpc(explorer, npcId);
        System.out.println(npcResponse);

        explorer = db.updateExplorer(explorer);
        displayGameState();
        }
        catch (Exception e) {
        System.out.println("error talking to npc");
        }
  
    }
*/



}
