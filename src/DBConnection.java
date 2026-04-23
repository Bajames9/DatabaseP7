import java.sql.*;

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
            throw new RuntimeException(e);
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



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // drops a treasure in current room
    public void Drop (Explorer explorer, int tresID)
    {


        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE TREASURES SET ROOMID = ?, EXPID = null WHERE TRESID = ?");

            ps.setInt(1,explorer.getRoomId());
            ps.setInt(2,tresID);

            ps.executeUpdate();



        } catch (Exception e) {
            System.out.println("Error with drop command connection layer");
            throw new RuntimeException(e);
        }


    }




}