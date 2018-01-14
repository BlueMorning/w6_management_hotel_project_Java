package turnover;

import guest.Guest;
import reservation.Reservation;
import room.room.Room;

import java.util.ArrayList;


public class RoomTurnover extends Turnover {


    private Room room;



    public RoomTurnover(Room room)
    {
        this.room         = room;
        this.turnover     = 0.0;
    }




    public static Double calculateTotalTurnover(ArrayList<Reservation> reservations) {

        Double totalTurnover = 0.0;

        for(Reservation reservation: reservations){

            totalTurnover += reservation.getTotalPricePaid();
        }

        return totalTurnover;
    }


    public Double calculateTurnoverByRoom(ArrayList<Reservation> reservations) {

        this.turnover = 0.0;

        for(Reservation reservation: reservations){

            if(reservation.getRoom() == this.room) {
                this.turnover += reservation.getTotalPricePaid();
            }
        }

        return this.turnover;
    }
}
