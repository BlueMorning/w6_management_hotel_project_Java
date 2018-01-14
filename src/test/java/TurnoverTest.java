import guest.Guest;
import hotel.Hotel;
import org.junit.Before;
import org.junit.Test;
import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;
import room.room.Room;
import turnover.Turnover;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TurnoverTest {

    Turnover turnover;

    Hotel hotel;
    ArrayList<Room> rooms;
    BedRoom bedRoomFor1;
    BedRoom bedRoomFor2;
    ConferenceRoom confRoom;
    DiningRoom diningRoom;
    ArrayList<Menu> menuList;
    ArrayList<Guest> guests;
    ArrayList<Guest> singleguest;
    Guest guest1;
    Guest guest2;

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
        guest1 = new Guest("Luke", 1000.0, 0.0);
        guest2 = new Guest("Linda", 2000.0, 0.0);
        guests.add(guest1);
        guests.add(guest2);

        singleguest = new ArrayList<>();
        singleguest.add(new Guest("Luke", 1000.0, 0.0));
    }


    @Test
    public void canGetTurnoverForARoom(){
        hotel.checkReservationToAdd(bedRoomFor2, "2018-01-01", 10, guests);
        hotel.getOnGoingReservationsByRoom(bedRoomFor2).get(0).guestMakePayment(guest1, 500.0);

        turnover = new Turnover(bedRoomFor2);
        assertEquals(new Double(500.0), turnover.calculateTurnoverByRoom(hotel.getAllReservations()));
    }

    @Test
    public void canGetTurnoverForAGuest(){
        hotel.checkReservationToAdd(bedRoomFor2, "2018-01-01", 10, guests);
        hotel.getOnGoingReservationsByRoom(bedRoomFor2).get(0).guestMakePayment(guests.get(0), 500.0);

        turnover = new Turnover(guest1);
        assertEquals(new Double(500.0), turnover.calculateTurnoverByGuest(hotel.getAllReservations()));

        turnover = new Turnover(guest2);
        assertEquals(new Double(0.0), turnover.calculateTurnoverByGuest(hotel.getAllReservations()));
    }

}
