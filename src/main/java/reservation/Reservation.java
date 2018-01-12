package reservation;

import hotel.room.Room;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {

    private Room room;
    private String startDateString;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private ReservationStatus status;

    public Reservation(Room room, String startingDate, int duration) {

        this.room               = room;
        this.startDateString    = startingDate;
        this.duration           = duration;
        this.startDate          = LocalDate.parse(this.startDateString);
        this.status             = ReservationStatus.ONGOING;
        calculateEndDate();
    }

    public Room getRoom() {
        return this.room;
    }

    public String getStartDateString() {
        return this.startDateString;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public int getDuration(){
        return this.duration;
    }

    public ReservationStatus getStatus(){
        return this.status;
    }

    public void calculateEndDate(){
        this.endDate = this.startDate.plusDays(this.duration);
    }

    public void setStartDate(String startDate){
        this.startDate = LocalDate.parse(startDate);
        calculateEndDate();
    }

    public void setDuration(){
        calculateEndDate();
    }
}
