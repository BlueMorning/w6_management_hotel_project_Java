import guest.Guest;
import hotel.Hotel;
import hotel.bedroom.BedRoom;
import hotel.bedroom.BedRoomType;
import hotel.conferenceRoom.ConferenceRoom;
import hotel.diningRoom.DiningRoom;
import hotel.diningRoom.Menu;
import hotel.room.Room;
import org.junit.Before;
import org.junit.Test;
import reservation.Reservation;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class HotelTest {

    Hotel hotel;
    ArrayList<Room> rooms;
    BedRoom bedRoom;
    ConferenceRoom confRoom;
    DiningRoom diningRoom;
    ArrayList<Menu> menuList;
    ArrayList<Guest> guests;

    @Before
    public void Before(){
        rooms   = new ArrayList<>();

        bedRoom = new BedRoom("Orange", BedRoomType.DOUBLE, 50);
        rooms.add(bedRoom);

        confRoom = new ConferenceRoom("Kennedy", 30, 500, true);
        rooms.add(confRoom);

        menuList = new ArrayList<>();
        menuList.add(new Menu("Enjoyable", 15.8));
        menuList.add(new Menu("Senses", 34.5));
        menuList.add(new Menu("Experience", 60.0));

        diningRoom = new DiningRoom("The Gourmet", 100, menuList);
        rooms.add(diningRoom);


        hotel = new Hotel("Laucala Island", rooms);

        guests = new ArrayList<>();
    }

    @Test
    public void hasName(){
        assertEquals("Laucala Island", hotel.getName());
    }

    @Test
    public void hasRooms(){
        assertEquals(3, hotel.getRoomsCount());
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
    public void isRoomAvailable_true(){
        Reservation reservation1 = new Reservation(bedRoom, "2018-01-12", 10, guests);
        assertEquals(true, hotel.isRoomAvailable(reservation1));

        Reservation reservation2 = new Reservation(bedRoom, "2018-01-10", 1, guests);
        assertEquals(true, hotel.isRoomAvailable(reservation2));

        Reservation reservation3 = new Reservation(bedRoom, "2018-01-23", 3, guests);
        assertEquals(true, hotel.isRoomAvailable(reservation3));
    }


    @Test
    public void canAddReservation(){
        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom, "2018-01-12", 10, guests);
        assertEquals(1, hotel.getOnGoingReservationsCount());
    }


    @Test
    public void isRoomAvailable_false(){

        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom, "2018-01-12", 10, guests);
        assertEquals(1, hotel.getOnGoingReservationsCount());

        Reservation reservation1 = new Reservation(bedRoom, "2018-01-10", 3, guests);
        assertEquals(false, hotel.isRoomAvailable(reservation1));

        Reservation reservation2 = new Reservation(bedRoom, "2018-01-13", 2, guests);
        assertEquals(false, hotel.isRoomAvailable(reservation2));

        Reservation reservation3 = new Reservation(bedRoom, "2018-01-21", 7, guests);
        assertEquals(false, hotel.isRoomAvailable(reservation3));
    }

    @Test
    public void canCheckOutReservation(){

        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom, "2018-01-02", 3, guests);
        assertEquals(1, hotel.getOnGoingReservationsCount());

        hotel.checkOutReservation(hotel.getOnGoingReservations().get(0));

        assertEquals(0, hotel.getOnGoingReservationsCount());
    }

    @Test
    public void canCheckOutAllReservations(){

        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom,    "2018-01-02", 3, guests);
        hotel.checkReservationToAdd(confRoom,   "2018-01-05", 5, guests);
        hotel.checkReservationToAdd(diningRoom, "2018-01-10", 2, guests);

        assertEquals(3, hotel.getOnGoingReservationsCount());

        hotel.checkOutAllEndedReservation();

        assertEquals(0, hotel.getOnGoingReservationsCount());
    }



}
