public class NPC {
    private int npcId; 
    private String name; 
    private int roomId; 
    private String type; 

    public NPC(int npcId, String name, int roomId, String type) {
        this.npcId = npcId;
        this.name  = name; 
        this.roomId = roomId; 
        this.type = type; 
    }

    public NPC(){}

    public int getNpcId() {
        return npcId;
    }

    public void setNpcId(int npcId) {
        this.npcId = npcId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
