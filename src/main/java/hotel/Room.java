package hotel;

public class Room {


    protected int capacity;
    protected String name;

    public Room(String name, int capacity){
        this.name     = name;
        this.capacity = capacity;
    }

    public String getName() {
        return this.name;
    }

    public int getCapacity(){
        return this.capacity;
    }
}
