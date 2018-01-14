package room.conferenceRoom;

import room.room.Room;
import room.room.RoomType;

public class ConferenceRoom extends Room {


    private int dailyRate;

    public ConferenceRoom(String name, int capacity, int dailyRate) {
        super(name, capacity);
        this.dailyRate      = dailyRate;
        this.roomType       = RoomType.CONFROOM;
    }

    public int getDailyRate(){
        return this.dailyRate;
    }

}
