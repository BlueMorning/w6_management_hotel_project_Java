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
    ArrayList<Menu> menuList;

    @Before
    public void Before(){
        rooms = new ArrayList<>();

        bedRoom = new BedRoom("Orange", BedRoomType.DOUBLE, 50);
        rooms.add(bedRoom);

        confRoom = new ConferenceRoom("Kennedy", 30, 500, true);
        rooms.add(confRoom);

        menuList = new ArrayList<>();
        menuList.add(new Menu("Enjoyable", 15.8));
        menuList.add(new Menu("Senses", 34.5));
        menuList.add(new Menu("Experience", 60.0));

        rooms.add(new DiningRoom("The Gourmet", 100, menuList));


        hotel = new Hotel("Laucala Island", rooms);
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
        Reservation reservation1 = new Reservation(bedRoom, "2018-01-12", 10);
        assertEquals(true, hotel.isRoomAvailable(reservation1));

        Reservation reservation2 = new Reservation(bedRoom, "2018-01-10", 1);
        assertEquals(true, hotel.isRoomAvailable(reservation2));

        Reservation reservation3 = new Reservation(bedRoom, "2018-01-23", 3);
        assertEquals(true, hotel.isRoomAvailable(reservation3));
    }


    @Test
    public void canAddReservation(){
        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom, "2018-01-12", 10);
        assertEquals(1, hotel.getOnGoingReservationsCount());
    }


    @Test
    public void isRoomAvailable_false(){

        assertEquals(0, hotel.getAllReservationsCount());
        hotel.checkReservationToAdd(bedRoom, "2018-01-12", 10);
        assertEquals(1, hotel.getOnGoingReservationsCount());

        Reservation reservation1 = new Reservation(bedRoom, "2018-01-10", 3);
        assertEquals(false, hotel.isRoomAvailable(reservation1));

        Reservation reservation2 = new Reservation(bedRoom, "2018-01-13", 2);
        assertEquals(false, hotel.isRoomAvailable(reservation2));

        Reservation reservation3 = new Reservation(bedRoom, "2018-01-21", 7);
        assertEquals(false, hotel.isRoomAvailable(reservation3));
    }




}
