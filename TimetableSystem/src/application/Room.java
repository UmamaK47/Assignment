package application;

public class Room {
	private int idRoom;
    private String RoomNum;
    public Room(int id, String name) {
        this.idRoom = id;
        this.RoomNum = name;
    }
    public int getId() {
        return idRoom;
    }
    public void setId(int newId) { 
    	idRoom=newId;
    }
    public String getName() {
        return RoomNum;
    }
    public void setName(String newName) { 
    	RoomNum=newName; 
    }
    @Override
    public String toString() {
        return RoomNum;
    }

}
