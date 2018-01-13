package reservation;

import guest.Guest;
import reservation.DiningReservation.DiningReservation;
import room.bedroom.BedRoom;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.diningRoom.Dinner;
import room.room.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Reservation {

    private Room room;
    private String startDateString;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private ReservationStatus status;
    private HashMap<Guest, Double> guestPayments;
    private Double balance;

    public Reservation(Room room, String startingDate, int duration, ArrayList<Guest> guests) {

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
        this.endDate = this.startDate.plusDays(this.duration-1);
    }

    public void setStartDate(String startDate){
        this.startDate = LocalDate.parse(startDate);
        calculateEndDate();
    }

    public void setDuration(){
        calculateEndDate();
    }

    public void endReservation(){
        this.status = ReservationStatus.ENDED;
    }

    public Double getTotalPriceToPay(){

        Double totalAmountToPay = 0.0;

        if(     BedRoom.class.isInstance(room)){
            totalAmountToPay = new Double(((BedRoom)room).getNightRate()) * this.duration;
        }
        else if(ConferenceRoom.class.isInstance(room))
        {
            totalAmountToPay = new Double(((ConferenceRoom)room).getDailyRate()) * this.duration;
        }
        else if(DiningRoom.class.isInstance(room))
        {
            totalAmountToPay = ((DiningReservation)(this)).getDinner().calculateDinnerPrice();
        }


        return totalAmountToPay;

    }

    public HashMap<Guest, Double> getGuestPayments(){
        return guestPayments;
    }

    public Double getTotalPricePaid(){

        Double totalPaid = 0.0;

        for(Guest guest : guestPayments.keySet())
        {
            totalPaid += guestPayments.get(guest).doubleValue();
        }

        return totalPaid;
    }

    public Double getBalance(){
        return this.getTotalPriceToPay() - this.getTotalPricePaid();
    }
}
