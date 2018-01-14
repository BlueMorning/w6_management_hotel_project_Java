package hotel;

import guest.Guest;
import reservation.DiningReservation.DiningReservation;
import room.diningRoom.DiningRoom;
import room.room.Room;
import reservation.Reservation;
import reservation.ReservationStatus;
import room.room.RoomType;
import turnover.GuestTurnover;
import turnover.RoomTurnover;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Hotel {


    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Reservation> reservations;
    private HashMap<String, Guest> guestsMap;

    public Hotel(String name, ArrayList<Room> roomsList) {
        this.name           = name;
        this.rooms          = roomsList;
        this.reservations   = new ArrayList<>();
        this.guestsMap      = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void addGuest(Guest guest){
        this.guestsMap.put(guest.getName(), guest);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public HashMap<String, Guest> getGuestsMap(){
        return guestsMap;
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

    public ArrayList<Guest> getAllGuestPayers(){

        ArrayList<Guest> guestPayers = new ArrayList<>();

        for(Reservation reservation : reservations){
            for(Guest guestPayer: reservation.getGuestPayments().keySet()){

                if(! guestPayers.contains(guestPayer))
                {
                    guestPayers.add(guestPayer);
                }
            }
        }

        return guestPayers;
    }

    public ReservationStatus isReservationCanBeMade(Reservation new_reservation) {

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
             new_reservation.setStatus(ReservationStatus.OVER_CAPACITY);
        }
        else if(! isRoomAvailable && isCapacitySufficient)
        {
            new_reservation.setStatus(ReservationStatus.NOT_AVAILABLE);
        }
        else if(! isRoomAvailable && ! isCapacitySufficient)
        {
            new_reservation.setStatus(ReservationStatus.NOT_AVAILABLE_AND_OVER_CAPACITY);
        }
        else
        {
            new_reservation.setStatus(ReservationStatus.READY_FOR_BOOKING);
        }

        return new_reservation.getStatus();
    }


    public Reservation checkReservationToAdd(Room room, String startDate, int duration, ArrayList<Guest> guests){

        checkOutAllEndedReservation();
        Reservation reservationToAdd = null;

        if(     room.getRoomType() == RoomType.BEDROOM || room.getRoomType() == RoomType.CONFROOM) {
            reservationToAdd = new Reservation(room, startDate, duration, guests);
        }
        else if(room.getRoomType() == RoomType.DININGROOM ){
            reservationToAdd = new DiningReservation((DiningRoom)room, startDate, duration, guests);
        }

        isReservationCanBeMade(reservationToAdd);

        if(reservationToAdd.getStatus() == ReservationStatus.READY_FOR_BOOKING) {
            reservations.add(reservationToAdd);
            reservationToAdd.setStatus(ReservationStatus.ONGOING);
        }

        return reservationToAdd;
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
                    && this.isReservationCanBeMade(new Reservation(room, startDate, duration, guests)) == ReservationStatus.READY_FOR_BOOKING
              ) {
                foundRooms.add(room);
            }
        }

        return foundRooms;
    }


    public Double getTurnoverByRoom(Room room){

        return new RoomTurnover(room).calculateTurnoverByRoom(this.reservations);
    }

    public HashMap<Room, RoomTurnover> getTurnoverForAllRooms(){

        HashMap<Room, RoomTurnover> roomsTurnover = new HashMap<>();

        for(Room room: rooms){
            RoomTurnover turnover = new RoomTurnover(room);
            turnover.calculateTotalTurnover(this.reservations);
            roomsTurnover.put(room, turnover);
        }

        return roomsTurnover;
    }

    public Double getTurnoverByGuest(Guest guest){
        return new GuestTurnover(guest).calculateTurnoverByGuest(this.reservations);
    }

    public HashMap<Guest, GuestTurnover> getTurnoverForAllGuests(){

        HashMap<Guest, GuestTurnover> guestsTurnover = new HashMap<>();

        for(Guest guest: this.getAllGuestPayers()){
            GuestTurnover turnover = new GuestTurnover(guest);
            turnover.calculateTurnoverByGuest(this.reservations);
            guestsTurnover.put(guest, turnover);
        }

        return guestsTurnover;
    }

    public Double getTotalTurnover(){

        return RoomTurnover.calculateTotalTurnover(this.reservations);
    }


    public Room getRoomByName(String roomName) {
        for(Room room : rooms){
            if(room.getName().equals(roomName)){
                return room;
            }
        }
        return null;
    }
}
