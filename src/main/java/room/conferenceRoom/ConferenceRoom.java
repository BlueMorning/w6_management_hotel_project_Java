package room.conferenceRoom;

import room.room.Room;

public class ConferenceRoom extends Room {


    private int dailyRate;

    public ConferenceRoom(String name, int capacity, int dailyRate) {
        super(name, capacity);
        this.dailyRate      = dailyRate;
    }

    public int getDailyRate(){
        return this.dailyRate;
    }

}
