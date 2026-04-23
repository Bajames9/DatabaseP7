/*
* explorer object used to track and update player information
*
* */

public class Explorer {

    private String username;
    private String Name;
    private int ExpID;
    private int RoomId;
    private int BagWt;
    private int Bag_cnt;

    public Explorer(String username, String name, int expID, int roomId, int bagWt, int bag_cnt) {
        this.username = username;
        Name = name;
        ExpID = expID;
        RoomId = roomId;
        BagWt = bagWt;
        Bag_cnt = bag_cnt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBagWt() {
        return BagWt;
    }

    public void setBagWt(int bagWt) {
        BagWt = bagWt;
    }

    public int getBag_cnt() {
        return Bag_cnt;
    }

    public void setBag_cnt(int bag_cnt) {
        Bag_cnt = bag_cnt;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getExpID() {
        return ExpID;
    }

    public void setExpID(int expID) {
        ExpID = expID;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }
}
