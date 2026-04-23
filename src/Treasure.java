public class Treasure {

    int TresID;
    String Name;
    int ExpID;
    int NpcID;
    int RoomID;
    String Description;
    int Value;
    int Weight;


    public Treasure(int tresID, String name, int expID, int npcID, int roomID, String description, int value, int weight) {
        TresID = tresID;
        Name = name;
        ExpID = expID;
        NpcID = npcID;
        RoomID = roomID;
        Description = description;
        Value = value;
        Weight = weight;
    }

    public int getTresID() {
        return TresID;
    }

    public void setTresID(int tresID) {
        TresID = tresID;
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

    public int getNpcID() {
        return NpcID;
    }

    public void setNpcID(int npcID) {
        NpcID = npcID;
    }

    public int getRoomID() {
        return RoomID;
    }

    public void setRoomID(int roomID) {
        RoomID = roomID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int weight) {
        Weight = weight;
    }
}
