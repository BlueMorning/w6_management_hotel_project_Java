package hotel;

public class BedRoom extends Room {


    private int rate;

    public BedRoom(String name, int capacity, int rate) {
        super(name, capacity);
        this.rate = rate;
    }

    public int getRate(){
        return this.rate;
    }
}
