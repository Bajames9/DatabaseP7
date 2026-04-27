import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    Connection conn;

    DBConnection(){

        this.conn = setupConnection();

    }


    /*
    * Changed connection logic to get a single Connection object
    *
    * */

    private Connection setupConnection(){


        String url = "jdbc:oracle:thin:@worf.radford.edu:1521/ITEC3.radford.edu";
        String user = "djuilfs";
        String password = "n2O4XI7USr2d2c8BmObb";


        try {

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected! DB: " + conn.getMetaData().getDatabaseProductName());
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    /*
    * database interaction commands
    * */


    // updates an existing explorer object
    public Explorer updateExplorer (Explorer explorer) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM EXPLORERS WHERE EXPID = ?");

            ps.setInt(1,explorer.getExpID());


            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                explorer = new Explorer
                        (         rs.getString("USERNAME")
                                , rs.getString("NAME")
                                , rs.getInt("EXPID")
                                , rs.getInt("ROOM_ID")
                                , rs.getInt("BAG_WT")
                                , rs.getInt("BAG_CNT")

                        );

            }

        } catch (Exception e) {
            System.out.println("Error with update explorer command connection layer");
            throw new RuntimeException(e);
        }

        return explorer;


    }

    // gets the explorer from a username and name used for new game startup
    public Explorer getExplorer ( String username,String Name) {


        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM EXPLORERS WHERE USERNAME = ? AND NAME = ?");

            ps.setString(1,username);
            ps.setString(2,Name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Explorer
                        (         rs.getString("USERNAME")
                                , rs.getString("NAME")
                                , rs.getInt("EXPID")
                                , rs.getInt("ROOM_ID")
                                , rs.getInt("BAG_WT")
                                , rs.getInt("BAG_CNT")


                        );

            }

        } catch (Exception e) {
            System.out.println("Error with newGame command connection layer");
            throw new RuntimeException(e);
        }

        return null;


    }



    // moves a explorer to a room
    public void Move (Explorer explorer, int RoomID) {

        try {

            CallableStatement cs = conn.prepareCall("{call move(?,?)}");

            cs.setInt(1,explorer.getExpID());
            cs.setInt(2,RoomID);

            cs.execute();



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    // grabs a treasure
    public boolean Grab (Explorer explorer, int tresID)
    {
        try {

            CallableStatement cs = conn.prepareCall("{call grab(?,?)}");

            cs.setInt(1,explorer.getExpID());
            cs.setInt(2,tresID);

            cs.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == 20003 | e.getErrorCode() == 1403) {
                System.out.println("I don't see that around here");
                return false;
            }
            else if (e.getErrorCode() == 20011 ) {
                System.out.println("You already have that item!");
                return false;
            }
            else if (e.getMessage().contains("BAGWEIGHTCK")) System.out.println("You cannot carry that much!");
            else if (e.getMessage().contains("BAGCOUNTCK")) System.out.println("You do not have enough space!"); 

        }

        return true;

    }

    // drops a treasure in current room
    public boolean Drop (Explorer explorer, int tresID)
    {

        try {

               


            //we need to make sure the item is actually in the room its in, right now I can force move items to me.

            PreparedStatement find = conn.prepareStatement("SELECT expID FROM TREASURES WHERE TRESID = ?");

            find.setInt(1, tresID); 

            ResultSet found = find.executeQuery(); 
            

            if(!found.next()) {
                System.out.println("You were imagining that item");
                return false;
            }

            int tresEXPID = found.getInt("EXPID"); 
            if (tresEXPID != explorer.getExpID()) {
                    System.out.println("You don't have that item");
                    return false;
            }
  
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE TREASURES SET ROOMID = ?, EXPID = null WHERE TRESID = ?");

            ps.setInt(1,explorer.getRoomId());
            ps.setInt(2,tresID);

            ps.executeUpdate();

            //need to get the treasure id. so we can update the weight from the explorer. 
            PreparedStatement get = conn.prepareStatement("SELECT WEIGHT FROM TREASURES WHERE TRESID = ?");
            
            get.setInt(1, tresID); 

            ResultSet rs = get.executeQuery(); 

            int weight = 0; 

            if (rs.next()) weight = rs.getInt("WEIGHT"); 
            else {
                System.out.println("ERROR IN QUERY");
                return false;
            }

            //using for update to lock the row to remove race condition with two drops too close together
            PreparedStatement lock = conn.prepareStatement(" SELECT BAG_WT, BAG_CNT FROM EXPLORERS WHERE EXPID = ? FOR UPDATE");
            
            lock.setInt(1, explorer.getExpID()); 
            
            ResultSet locked = lock.executeQuery(); 
            if (!locked.next()) {
                conn.rollback();
                return false; 
            }

            int bagwt = locked.getInt("BAG_WT");
            int bagcnt = locked.getInt("BAG_CNT"); 

            //if it doesn't exist, rollback. 


            PreparedStatement ps2 = conn.prepareStatement(
                "UPDATE Explorers SET BAG_WT = ?, BAG_CNT = ? WHERE EXPID = ?"
            );


            ps2.setInt(1, bagwt -  weight);
            ps2.setInt(2, bagcnt - 1);
            ps2.setInt(3, explorer.getExpID()); 

            ps2.executeUpdate();



        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


    public ArrayList<Treasure> getTreasuresForExplorer(Explorer explorer) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TREASURES WHERE EXPID = ?");

            ps.setInt(1,explorer.getExpID());


            ResultSet rs = ps.executeQuery();


            ArrayList<Treasure> bag = new ArrayList<>();
            while (rs.next()) {

                bag.add(new Treasure(
                          rs.getInt("TRESID")
                        , rs.getString("NAME")
                        , rs.getInt("EXPID")
                        , rs.getInt("NPCID")
                        , rs.getInt("ROOMID")
                        , rs.getString("DESCRIPTION")
                        , rs.getInt("VALUE")
                        , rs.getInt("WEIGHT")

                ));

            }

            if (!bag.isEmpty()){
                return bag;
            }

        } catch (Exception e) {
            System.out.println("Error with get Treasures bag command connection layer");
            throw new RuntimeException(e);
        }

        return null;

    }
    public ArrayList<NPC> getNPCsForRoom(int roomId) {

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM NPCS WHERE ROOMID = ?"); 

            ps.setInt(1, roomId);
            
            ResultSet rs = ps.executeQuery(); 

            ArrayList<NPC> npcs = new ArrayList<>(); 

            while(rs.next()){
                npcs.add(new NPC(
                    rs.getInt("NPCID"),
                    rs.getString("NAME"),
                    rs.getInt("ROOMID"),
                    rs.getString("TYPE")));
            } 
            return npcs; 

        }
        catch (SQLException e) {
            System.out.println(e); 
            return null; 
        }
    }
    
    public ArrayList<Treasure> getTreasuresForRoom(int roomId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM TREASURES WHERE ROOMID = ?");

            ps.setInt(1,roomId);


            ResultSet rs = ps.executeQuery();


            ArrayList<Treasure> bag = new ArrayList<>();
            while (rs.next()) {


                bag.add(new Treasure(
                        rs.getInt("TRESID")
                        , rs.getString("NAME")
                        , rs.getInt("EXPID")
                        , rs.getInt("NPCID")
                        , rs.getInt("ROOMID")
                        , rs.getString("DESCRIPTION")
                        , rs.getInt("VALUE")
                        , rs.getInt("WEIGHT")

                ));



            }

            if (!bag.isEmpty()){
                return bag;
            }

        } catch (Exception e) {
            System.out.println("Error with get room Treasures command connection layer");
            throw new RuntimeException(e);
        }

        return null;
    }

    public ArrayList<Integer> getConnectedRooms(int roomId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT ROOM2 FROM CONNECTIONS WHERE ROOM1 = ?");

            ps.setInt(1,roomId);


            ResultSet rs = ps.executeQuery();


            ArrayList<Integer> rooms = new ArrayList<>();
            while (rs.next()) {


                rooms.add(rs.getInt("ROOM2"));


            }

            if (!rooms.isEmpty()){
                return rooms;
            }

        } catch (Exception e) {
            System.out.println("Error with get connected rooms command connection layer");
            throw new RuntimeException(e);
        }

        return null;
    }
    
    public String talk(Explorer explorer, int npcId)
    {

    
        try {
        PreparedStatement ps = conn.prepareStatement("SELECT ROOMID, TYPE FROM NPCS WHERE NPCID = ?");

        ps.setInt(1, npcId);

        ResultSet rs = ps.executeQuery(); 
            
            if (rs.next()) {
                if (explorer.getRoomId() != rs.getInt("ROOMID")) {
                    return "Who are you talking to?";
                }
                else return rs.getString("TYPE"); 
            }
            else return "They must have been a figment of your imagination"; 

        }
        catch (SQLException e) {
            System.out.println(e);
            return "ERROR"; 
        }



    }


}