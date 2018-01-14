package turnover;

import guest.Guest;
import reservation.Reservation;

import java.util.ArrayList;

public class GuestTurnover extends Turnover {

    private Guest guest;

    public GuestTurnover(Guest guest)
    {
        this.guest        = guest;
        this.turnover     = 0.0;
    }

    public Double calculateTurnoverByGuest(ArrayList<Reservation> reservations) {

        this.turnover = 0.0;

        for(Reservation reservation: reservations){
            for(Guest guest: reservation.getGuestPayments().keySet()){
                if(this.guest == guest){
                    this.turnover += reservation.getGuestPayments().get(guest).doubleValue();
                }
            }
        }

        return this.turnover;
    }
}
