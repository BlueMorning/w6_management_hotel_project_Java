package hotel;

import guest.Guest;
import reservation.ReservationRequestStatus;
import room.bedroom.BedRoom;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.room.Room;
import reservation.Reservation;
import reservation.ReservationStatus;
import room.room.RoomType;

import java.time.LocalDate;
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

    public ArrayList<Reservation> getOnGoingReservationsByRoom(Room room) {

        ArrayList<Reservation> onGoingReservations = new ArrayList<>();

        for(Reservation reservation: reservations){
            if(reservation.getRoom() == room && reservation.getStatus() == ReservationStatus.ONGOING){
                onGoingReservations.add(reservation);
            }
        }

        return onGoingReservations;
    }

    public int getOnGoingReservationsCountByRoom(Room room){
        return  getOnGoingReservationsByRoom(room).size();
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

    public ReservationRequestStatus isRoomAvailable(Reservation new_reservation) {

        Boolean isRoomAvailable      = true;
        Boolean isCapacitySufficient = true;

        if(    new_reservation.getRoom().getRoomType() == RoomType.BEDROOM
            || new_reservation.getRoom().getRoomType() == RoomType.CONFROOM)
        {


            for (Reservation reservation : this.getOnGoingReservationsByRoom(new_reservation.getRoom())) {

                // if the new reservation schedule start date overlaps an ongoing start date reservation
                if ((new_reservation.getStartDate().isBefore(reservation.getStartDate()) || new_reservation.getStartDate().equals(reservation.getStartDate()))
                        && (new_reservation.getEndDate().isAfter(reservation.getStartDate()) || new_reservation.getEndDate().equals(reservation.getStartDate()))
                        )
                {
                    isRoomAvailable = false;
                    break;
                }
                // or if the new reservation schedule end date overlaps an ongoing end date reservation
                else if ((new_reservation.getStartDate().isBefore(reservation.getEndDate()) || new_reservation.getStartDate().equals(reservation.getEndDate()))
                        && (new_reservation.getEndDate().isAfter(reservation.getEndDate()) || new_reservation.getEndDate().equals(reservation.getEndDate()))
                        )
                {
                    isRoomAvailable = false;
                    break;
                } else if (new_reservation.getStartDate().isAfter(reservation.getStartDate())
                        && new_reservation.getEndDate().isBefore(reservation.getEndDate())) {
                    isRoomAvailable = false;
                    break;
                }
            }

            isCapacitySufficient = new_reservation.getRoom().getCapacity() >= new_reservation.getGuestsCount();
        }
        else if(new_reservation.getRoom().getRoomType() == RoomType.DININGROOM)
        {
            int nbSeatsAlreadyBooked = 0;

            for (Reservation reservation : this.getOnGoingReservationsByRoom(new_reservation.getRoom())) {

                // if the new reservation schedule start date overlaps an ongoing start date reservation
                if ((new_reservation.getStartDate().isBefore(reservation.getStartDate()) || new_reservation.getStartDate().equals(reservation.getStartDate()))
                        && (new_reservation.getEndDate().isAfter(reservation.getStartDate()) || new_reservation.getEndDate().equals(reservation.getStartDate()))
                        )
                {
                    nbSeatsAlreadyBooked += reservation.getGuestsCount();
                }
                // or if the new reservation schedule end date overlaps an ongoing end date reservation
                else if ((new_reservation.getStartDate().isBefore(reservation.getEndDate()) || new_reservation.getStartDate().equals(reservation.getEndDate()))
                        && (new_reservation.getEndDate().isAfter(reservation.getEndDate()) || new_reservation.getEndDate().equals(reservation.getEndDate()))
                        )
                {
                    nbSeatsAlreadyBooked += reservation.getGuestsCount();

                } else if (new_reservation.getStartDate().isAfter(reservation.getStartDate())
                        && new_reservation.getEndDate().isBefore(reservation.getEndDate()))
                {

                    nbSeatsAlreadyBooked += reservation.getGuestsCount();
                }
            }

            if(nbSeatsAlreadyBooked + new_reservation.getGuestsCount() > new_reservation.getRoom().getCapacity())
            {
                isRoomAvailable      = false;
                isCapacitySufficient = false;
            }
        }

        if(isRoomAvailable && ! isCapacitySufficient)
        {
            return ReservationRequestStatus.OVER_CAPACITY;
        }
        else if(! isRoomAvailable && isCapacitySufficient)
        {
            return ReservationRequestStatus.NOT_AVAILABLE;
        }
        else if(! isRoomAvailable && ! isCapacitySufficient)
        {
            return ReservationRequestStatus.NOT_AVAILABLE_AND_OVER_CAPACITY;
        }
        else
        {
            return ReservationRequestStatus.OK;
        }
    }


    public ReservationRequestStatus checkReservationToAdd(Room room, String startDate, int duration,  ArrayList<Guest> guests){

        checkOutAllEndedReservation();

        Reservation reservationToAdd               = new Reservation(room, startDate, duration, guests);
        ReservationRequestStatus reservationStatus = isRoomAvailable(reservationToAdd);

        if(reservationStatus == ReservationRequestStatus.OK) {
            reservations.add(reservationToAdd);
            reservationStatus = ReservationRequestStatus.SAVED;
        }

        return reservationStatus;
    }

    public void checkOutReservation(Reservation reservation){
        reservation.endReservation();
    }

    public void checkOutAllEndedReservation(){

        for(Reservation reservation: this.getOnGoingReservations())
        {
            if(reservation.getEndDate().isBefore(LocalDate.now()))
            {
                reservation.endReservation();
            }
        }
    }

    public int addNewRoom(Room new_room){
        this.rooms.add(new_room);
        return this.getRoomsCount();
    }


    public ArrayList<Room> searchForRooms(RoomType roomType, String startDate, int duration, int guestsNumber)
    {
        ArrayList<Room> foundRooms = new ArrayList<>();
        ArrayList<Guest> guests    = new ArrayList<>();

        for(int guestNumberToAdd = 0; guestNumberToAdd < guestsNumber; guestNumberToAdd++){
            guests.add(Guest.GetFakedGuest());
        }

        for(Room room : rooms){

            if(        room.isActive()
                    && room.getRoomType() == roomType
                    && this.isRoomAvailable(new Reservation(room, startDate, duration, guests)) == ReservationRequestStatus.OK
              ) {
                foundRooms.add(room);
            }
        }

        return foundRooms;
    }


}
