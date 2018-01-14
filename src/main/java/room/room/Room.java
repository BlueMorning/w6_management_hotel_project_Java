package room.room;

public class Room {


    protected int capacity;
    protected String name;
    protected Boolean isActive;
    protected RoomType roomType;

    public Room(String name, int capacity){
        this.name     = name;
        this.capacity = capacity;
        this.isActive = true;
        this.roomType = RoomType.ROOM;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity(){
        return this.capacity;
    }

    public RoomType getRoomType(){
        return this.roomType;
    }

    public Boolean isActive(){
        return this.isActive;
    }

    public void setIsActive(Boolean isActive){
        this.isActive = isActive;
    }
}
