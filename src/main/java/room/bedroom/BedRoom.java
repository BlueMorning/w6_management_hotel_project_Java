package room.bedroom;

import room.room.Room;
import room.room.RoomType;

public class BedRoom extends Room {


    private int nightRate;
    private BedRoomType type;

    public BedRoom(String name, BedRoomType bedRoomType, int nightRate) {
        super(name, bedRoomType.getCapacity());
        this.nightRate = nightRate;
        this.type      = bedRoomType;
        this.roomType  = RoomType.BEDROOM;
    }

    public int getNightRate(){
        return this.nightRate;
    }

    public BedRoomType getType() {
        return type;
    }
}
