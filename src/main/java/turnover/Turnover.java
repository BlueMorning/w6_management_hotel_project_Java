package turnover;

import guest.Guest;
import reservation.Reservation;
import room.room.Room;

import java.util.ArrayList;


public class Turnover {

    private Double turnover;
    private Room room;
    private Guest guest;
    private TurnoverType turnoverType;


    public Turnover(Room room)
    {
        this.room         = room;
        this.turnover     = 0.0;
        this.turnoverType = TurnoverType.ROOM;
    }

    public Turnover(Guest guest)
    {
        this.guest        = guest;
        this.turnover     = 0.0;
        this.turnoverType = TurnoverType.GUEST;
    }


    public static Double calculateTotalTurnover(ArrayList<Reservation> reservations) {

        Double totalTurnover = 0.0;

        for(Reservation reservation: reservations){

            totalTurnover += reservation.getTotalPricePaid();
        }

        return totalTurnover;
    }

    public Double getTurnover(){
        return this.turnover;
    }

    public Double calculateTurnoverByRoom(ArrayList<Reservation> reservations) {

        Double totalTurnover = 0.0;

        for(Reservation reservation: reservations){

            if(reservation.getRoom() == this.room) {
                totalTurnover += reservation.getTotalPricePaid();
            }
        }

        return totalTurnover;
    }

    public Double calculateTurnoverByGuest(ArrayList<Reservation> reservations) {

        Double totalTurnover = 0.0;

        for(Reservation reservation: reservations){
            for(Guest guest: reservation.getGuestPayments().keySet()){
                if(this.guest == guest){
                    totalTurnover += reservation.getGuestPayments().get(guest).doubleValue();
                }
            }
        }

        return totalTurnover;
    }
}
