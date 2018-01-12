package hotel.bedroom;

import hotel.Room;

public class BedRoom extends Room {


    private int nightRate;
    private BedRoomType type;

    public BedRoom(String name, BedRoomType bedRoomType, int nightRate) {
        super(name, bedRoomType.getCapacity());
        this.nightRate = nightRate;
        this.type      = bedRoomType;
    }

    public int getNightRate(){
        return this.nightRate;
    }

    public BedRoomType getType() {
        return type;
    }
}
