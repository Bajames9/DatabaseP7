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
        String user = "bjdavis";
        String password = "23179001243927B#b";


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
    public void Grab (Explorer explorer, int tresID)
    {
        try {

            CallableStatement cs = conn.prepareCall("{call grab(?,?)}");

            cs.setInt(1,explorer.getExpID());
            cs.setInt(2,tresID);

            cs.execute();

        } catch (SQLException e) {
            if (e.getErrorCode() == 20003 | e.getErrorCode() == 1403) {
                System.out.println("I don't see that around here");
            }
            else if (e.getErrorCode() == 20011 ) {
                System.out.println("You already have that item!"); 
            }

        }

    }

    // drops a treasure in current room
    public void Drop (Explorer explorer, int tresID)
    {

        try {

            //we need to make sure the item is actually in the room its in, right now I can force move items to me.

            PreparedStatement find = conn.prepareStatement("SELECT expID FROM TREASURES WHERE TRESID = ?");

            find.setInt(1, tresID); 

            ResultSet found = find.executeQuery(); 
            
            if (found.next()) {
                int tresEXPID = found.getInt("EXPID"); 
                if (tresEXPID != explorer.getExpID()) {
                    System.out.println("You don't have that item"); 
                    return; 
                }
            }
            if (!found.next()) {
                System.out.println("You were imagining that item"); 
                return; 
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

            System.out.println(tresID); 
            if (rs.next()) weight = rs.getInt("WEIGHT"); 
            else System.out.println("ERROR IN QUERY"); 
   

            PreparedStatement ps2 = conn.prepareStatement(
                "UPDATE Explorers SET BAG_WT = ?, BAG_CNT = ? WHERE EXPID = ?"
            );


            ps2.setInt(1, explorer.getBagWt()- weight);
            ps2.setInt(2, explorer.getBag_cnt()-1);
            ps2.setInt(3, explorer.getExpID()); 

            ps2.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
    /*
    public String TalkNpc(Explorer explorer, int npcId) throws SQLException 
    {

        String message = "";

        CallableStatement rs= conn.prepareCall("{call talk_to_npc(?, ?, ?)}");

        rs.setInt(1, explorer.getExpID());
        rs.setInt(2, npcId);
        rs.registerOutParameter(3, Types.VARCHAR);

        rs.execute();

        message = rs.getString(3);

        rs.close();

        return message;
    }
*/

}