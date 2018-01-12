package hotel.conferenceRoom;

import hotel.Room;

public class ConferenceRoom extends Room {


    private int dailyRate;
    private Boolean isSelfCatered;

    public ConferenceRoom(String name, int capacity, int dailyRate, boolean isSelfCatered) {
        super(name, capacity);
        this.dailyRate      = dailyRate;
        this.isSelfCatered  = isSelfCatered;
    }

    public int getDailyRate(){
        return this.dailyRate;
    }

    public Boolean isSelfCatered(){
        return this.isSelfCatered;
    }
}
