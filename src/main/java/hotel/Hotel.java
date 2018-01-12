package hotel;

import hotel.room.Room;
import reservation.Reservation;
import reservation.ReservationStatus;

import java.util.ArrayList;

public class Hotel {


    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;

    public Hotel(String name, ArrayList<Room> roomsList) {
        this.name           = name;
        this.rooms          = roomsList;
        this.reservations   = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public int getRoomsCount() {
        return rooms.size();
    }

    public ArrayList<Reservation> getAllReservations() {
        return reservations;
    }

    public int getAllReservationsCount(){
        return  getAllReservations().size();
    }

    public ArrayList<Reservation> getOnGoingReservations() {

        ArrayList<Reservation> onGoingReservations = new ArrayList<>();

        for(Reservation reservation: reservations){
            if(reservation.getStatus() == ReservationStatus.ONGOING){
                onGoingReservations.add(reservation);
            }
        }

        return onGoingReservations;
    }

    public int getOnGoingReservationsCount(){
        return  getOnGoingReservations().size();
    }

    public ArrayList<Reservation> getEndedReservations() {

        ArrayList<Reservation> onGoingReservations = new ArrayList<>();

        for(Reservation reservation: reservations){
            if(reservation.getStatus() == ReservationStatus.ENDED){
                onGoingReservations.add(reservation);
            }
        }

        return onGoingReservations;
    }

    public int getEndedReservationsCount(){
        return  getEndedReservations().size();
    }

    public boolean isRoomAvailable(Reservation new_reservation) {

        Boolean isRoomAvailable = true;

        for(Reservation reservation: this.getOnGoingReservations()){

            // if the new reservation schedule overlaps the start date of an ongoing reservation
            if(     (new_reservation.getStartDate().isBefore(reservation.getStartDate()) || new_reservation.getStartDate().isEqual(reservation.getStartDate()))
                &&   new_reservation.getEndDate().isAfter(reservation.getStartDate())
                )
            {
                isRoomAvailable = false;
                break;
            }
            // if the new reservation schedule is included in an ongoing reservation
            else if(    (new_reservation.getStartDate().isAfter(reservation.getStartDate()) || new_reservation.getStartDate().isEqual(reservation.getStartDate()))
                    &&  (new_reservation.getEndDate().isBefore(reservation.getEndDate()) || new_reservation.getEndDate().isEqual(reservation.getEndDate()))
                    )
            {
                isRoomAvailable = false;
                break;
            }
            // if the new reservation schedule overlaps the end date of an ongoing reservation
            else if(    new_reservation.getStartDate().isBefore(reservation.getEndDate())
                    &&  (new_reservation.getEndDate().isAfter(reservation.getEndDate()) || new_reservation.getEndDate().isEqual(reservation.getEndDate()))
                    )
            {
                isRoomAvailable = false;
                break;
            }

        }

        return isRoomAvailable;
    }

    private void addReservation(Reservation reservation){
        reservations.add(reservation);
    }

    public Boolean checkReservationToAdd(Room room, String startDate, int duration){

        Boolean reservationAdded = false;
        Reservation reservationToAdd = new Reservation(room, startDate, duration);

        if(isRoomAvailable(reservationToAdd)) {
            reservations.add(reservationToAdd);
            reservationAdded = true;
        }

        return reservationAdded;
    }

}
