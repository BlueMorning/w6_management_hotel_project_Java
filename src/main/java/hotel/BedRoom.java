package hotel;

public class BedRoom extends Room {


    private int nightRate;

    public BedRoom(String name, int capacity, int nightRate) {
        super(name, capacity);
        this.nightRate = nightRate;
    }

    public int getNightRate(){
        return this.nightRate;
    }
}
