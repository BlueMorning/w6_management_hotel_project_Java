import guest.Guest;
import hotel.Hotel;
import reservation.DiningReservation.DiningReservation;
import reservation.ReservationStatus;
import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;
import room.room.Room;
import org.junit.Before;
import org.junit.Test;
import room.room.RoomType;
import turnover.GuestTurnover;

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
    Guest guest1;
    Guest guest2;
    Guest guest3;

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


        guest1 = new Guest("Luke", 3000.0, 0.0);
        guest2 = new Guest("Ashley", 4000.0, 0.0);
        guest3 = new Guest("Bruce", 1000.0, 0.0);

        guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);

        singleguest = new ArrayList<>();
        singleguest.add(guest1);
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
    public void isRoomCanBeBooked(){

        ReservationStatus reservation1_status = hotel.checkReservationToAdd(bedRoomFor2, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationStatus.ONGOING, reservation1_status);

        ReservationStatus reservation2_status = hotel.checkReservationToAdd(bedRoomFor2, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationStatus.NOT_AVAILABLE, reservation2_status);

        ReservationStatus reservation3_status = hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationStatus.OVER_CAPACITY, reservation3_status);

        hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, singleguest);
        ReservationStatus reservation4_status = hotel.checkReservationToAdd(bedRoomFor1, LocalDate.now().toString(), 10, guests);
        assertEquals(ReservationStatus.NOT_AVAILABLE_AND_OVER_CAPACITY, reservation4_status);
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


    @Test
    public void canGetTurnoverByBedRoom(){
        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 150.0);
        assertEquals(new Double(150), hotel.getTurnoverByRoom(bedRoomFor2));
    }

    @Test
    public void canGetTurnoverByDiningRoom(){
        hotel.checkReservationToAdd(diningRoom,    LocalDate.now().toString(), 1, guests);
        DiningReservation diningReservation = (DiningReservation)(hotel.getOnGoingReservations().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest1, diningRoom.getMenusList().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest2, diningRoom.getMenusList().get(1));

        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 40.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 5.3);

        assertEquals(new Double(45.3), hotel.getTurnoverByRoom(diningRoom));
        assertEquals(new Double(45.3), diningReservation.getTotalPricePaid());
        assertEquals(new Double(50.3), diningReservation.getTotalPriceToPay());
        assertEquals(new Double(5.0), diningReservation.getBalance());
    }

    @Test
    public void canGetTurnoverByGuest(){

        hotel.checkReservationToAdd(diningRoom,    LocalDate.now().toString(), 1, guests);
        DiningReservation diningReservation = (DiningReservation)(hotel.getOnGoingReservations().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest1, diningRoom.getMenusList().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest2, diningRoom.getMenusList().get(1));

        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 40.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 5.3);

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 100.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 50.0);

        assertEquals(new Double(140.0), hotel.getTurnoverByGuest(guest1));
        assertEquals(new Double(55.3), hotel.getTurnoverByGuest(guest2));
    }

    @Test
    public void canGetTotalTurnover(){

        hotel.checkReservationToAdd(diningRoom,    LocalDate.now().toString(), 1, guests);
        DiningReservation diningReservation = (DiningReservation)(hotel.getOnGoingReservations().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest1, diningRoom.getMenusList().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest2, diningRoom.getMenusList().get(1));

        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 40.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 5.3);

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest1, 100.0);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest2, 50.0);


        hotel.checkReservationToAdd(confRoom,       LocalDate.now().toString(), 4, guests);
        hotel.getOnGoingReservations().get(2).guestMakePayment(guest2, 1500.0);

        assertEquals(new Double(1695.3), hotel.getTotalTurnover());

    }

    @Test
    public void canGetTurnoverForAllRooms(){

        hotel.checkReservationToAdd(diningRoom,    LocalDate.now().toString(), 1, guests);
        DiningReservation diningReservation = (DiningReservation)(hotel.getOnGoingReservations().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest1, diningRoom.getMenusList().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest2, diningRoom.getMenusList().get(1));

        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 40.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 5.3);

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest1, 100.0);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest2, 50.0);


        hotel.checkReservationToAdd(confRoom,       LocalDate.now().toString(), 4, guests);
        hotel.getOnGoingReservations().get(2).guestMakePayment(guest2, 1500.0);

        assertEquals(4, hotel.getTurnoverForAllRooms().size());

        assertEquals(new Double(45.3),      hotel.getTurnoverByRoom(diningRoom));
        assertEquals(new Double(150.0),     hotel.getTurnoverByRoom(bedRoomFor2));
        assertEquals(new Double(1500.0),    hotel.getTurnoverByRoom(confRoom));
        assertEquals(new Double(0.0),       hotel.getTurnoverByRoom(bedRoomFor1));
    }

    @Test
    public void canGetTurnoverForAllGuests(){

        hotel.checkReservationToAdd(diningRoom,    LocalDate.now().toString(), 1, guests);
        DiningReservation diningReservation = (DiningReservation)(hotel.getOnGoingReservations().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest1, diningRoom.getMenusList().get(0));
        diningReservation.getDinner().orderMenuByGuest(guest2, diningRoom.getMenusList().get(1));

        hotel.getOnGoingReservations().get(0).guestMakePayment(guest1, 40.0);
        hotel.getOnGoingReservations().get(0).guestMakePayment(guest2, 5.3);

        hotel.checkReservationToAdd(bedRoomFor2,    LocalDate.now().toString(), 3, guests);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest1, 100.0);
        hotel.getOnGoingReservations().get(1).guestMakePayment(guest2, 50.0);


        hotel.checkReservationToAdd(confRoom,       LocalDate.now().toString(), 4, guests);
        hotel.getOnGoingReservations().get(2).guestMakePayment(guest2, 1500.0);

        assertEquals(2, hotel.getTurnoverForAllGuests().size());

        assertEquals(new Double(140.0),   hotel.getTurnoverForAllGuests().get(guest1).getTurnover());
        assertEquals(new Double(1555.3),  hotel.getTurnoverForAllGuests().get(guest2).getTurnover());
        assertEquals(new Double(0.0),     hotel.getTurnoverByGuest(guest3));
    }
}
