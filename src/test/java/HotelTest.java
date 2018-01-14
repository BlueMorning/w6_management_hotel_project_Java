import guest.Guest;
import hotel.Hotel;
import reservation.ReservationRequestStatus;
import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;
import room.room.Room;
import org.junit.Before;
import org.junit.Test;
import room.room.RoomType;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HotelTest {

    Hotel hotel;
    ArrayList<Room> rooms;
    BedRoom bedRoomFor1;
    BedRoom bedRoomFor2;
    ConferenceRoom confRoom;
    DiningRoom diningRoom;
    ArrayList<Menu> menuList;
    ArrayList<Guest> guests;
    ArrayList<Guest> singleguest;

    @Before
    public void Before(){
        rooms   = new ArrayList<>();

        bedRoomFor2 = new BedRoom("Orange", BedRoomType.DOUBLE, 50);
        rooms.add(bedRoomFor2);

        bedRoomFor1 = new BedRoom("Zen", BedRoomType.SINGLE, 50);
        rooms.add(bedRoomFor1);

        confRoom = new ConferenceRoom("Kennedy", 30, 500);
        rooms.add(confRoom);

        menuList = new ArrayList<>();
        menuList.add(new Menu("Enjoyable", 15.8));
        menuList.add(new Menu("Senses", 34.5));
        menuList.add(new Menu("Experience", 60.0));

        diningRoom = new DiningRoom("The Gourmet", 100, menuList);
        rooms.add(diningRoom);


        hotel = new Hotel("Laucala Island", rooms);

        guests = new ArrayList<>();
        guests.add(new Guest("Luke", 300.0, 0.0));
        guests.add(new Guest("Linda", 400.0, 0.0));

        singleguest = new ArrayList<>();
        singleguest.add(new Guest("Luke", 300.0, 0.0));
    }

    @Test
    public void hasName(){
        assertEquals("Laucala Island", hotel.getName());
    }

    @Test
    public void hasRooms(){
        assertEquals(4, hotel.getRoomsCount());
    }

    @Test
    public void canAddRoom(){
        hotel.addNewRoom(new BedRoom("Magic", BedRoomType.DOUBLE, 100));
        assertEquals(5, hotel.getRoomsCount());
    }

    @Test
    public void hasAllReservation(){
        assertEquals(0, hotel.getAllReservationsCount());
    }

    @Test
    public void hasOnGoingReservation(){
        assertEquals(0, hotel.getOnGoingReservationsCount());
    }

    @Test
    public void hasEndedReservation(){
        assertEquals(0, hotel.getEndedReservationsCount());
    }


    @Test
    public void isRoomAvailable(){

        ReservationRequestStatus reservation1_status = hotel.checkReservationToAdd(bedRoomFor2, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationRequestStatus.SAVED, reservation1_status);

        ReservationRequestStatus reservation2_status = hotel.checkReservationToAdd(bedRoomFor2, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationRequestStatus.NOT_AVAILABLE, reservation2_status);

        ReservationRequestStatus reservation3_status = hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationRequestStatus.OVER_CAPACITY, reservation3_status);

        hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, singleguest);
        ReservationRequestStatus reservation4_status = hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationRequestStatus.NOT_AVAILABLE_AND_OVER_CAPACITY, reservation4_status);
    }


    @Test
    public void canAddReservation(){
        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoomFor2, "2018-01-12", 10, guests);
        assertEquals(1, hotel.getOnGoingReservationsCount());
    }


    @Test
    public void canCheckOutReservation(){

        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoomFor2, "2018-01-02", 3, guests);
        assertEquals(1, hotel.getOnGoingReservationsCount());

        hotel.checkOutReservation(hotel.getOnGoingReservations().get(0));

        assertEquals(0, hotel.getOnGoingReservationsCount());
    }

    @Test
    public void canCheckOutAllReservations(){

        // Note the checkOutAllEndedReservation method is called inside the checkReservationToAdd method
        // in order to end automatically the reservations which have ended.
        assertEquals(0, hotel.getAllReservationsCount());

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.checkReservationToAdd(confRoom,       LocalDate.now().toString(), 5, guests);
        hotel.checkReservationToAdd(diningRoom,     LocalDate.now().toString(), 2, guests);

        assertEquals(3, hotel.getOnGoingReservationsCount());

        hotel.checkOutAllEndedReservation();

        assertEquals(3, hotel.getOnGoingReservationsCount());

        hotel.checkReservationToAdd(bedRoomFor2,    "2018-01-03", 3, guests);
        assertEquals(4, hotel.getOnGoingReservationsCount());
        hotel.checkReservationToAdd(confRoom,   "2018-01-03", 5, guests);
        assertEquals(4, hotel.getOnGoingReservationsCount());
        hotel.checkReservationToAdd(diningRoom, "2018-01-03", 2, guests);
        assertEquals(4, hotel.getOnGoingReservationsCount());

        hotel.checkOutAllEndedReservation();

        assertEquals(3, hotel.getOnGoingReservationsCount());
    }

    @Test
    public void canSearchForRooms(){
        ArrayList<Room> foundRooms = hotel.searchForRooms(RoomType.BEDROOM, LocalDate.now().toString(), 3, 2);
        assertEquals(1, foundRooms.size());

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 1, guests);
        foundRooms = hotel.searchForRooms(RoomType.BEDROOM, LocalDate.now().toString(), 3, 2);
        assertEquals(0, foundRooms.size());
    }


}
