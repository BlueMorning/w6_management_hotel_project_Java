package reservation.DiningReservation;
import guest.Guest;
import reservation.Reservation;
import room.diningRoom.DiningRoom;
import room.diningRoom.Dinner;

import java.util.ArrayList;

public class DiningReservation extends Reservation {

    private Dinner dinner;


    public DiningReservation(DiningRoom diningRoom, String startingDate, int duration, ArrayList<Guest> guests) {
        super(diningRoom, startingDate, duration, guests);
        this.dinner = new Dinner(diningRoom, guests);
    }

    public Dinner getDinner(){
        return this.dinner;
    }
}
