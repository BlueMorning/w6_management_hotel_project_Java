package reservation;

import hotel.Room;
import hotel.bedroom.BedRoom;

public class Reservation {

    private Room room;
    private String startDate;
    private int duration;

    public Reservation(Room room, String startingDate, int duration) {
        this.room       = room;
        this.startDate  = startingDate;
        this.duration   = duration;
    }

    public Room getRoom() {
        return this.room;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public int getDuration(){
        return this.duration;
    }
}
