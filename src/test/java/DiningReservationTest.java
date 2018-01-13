import guest.Guest;
import hotel.Hotel;
import org.junit.Before;
import org.junit.Test;
import reservation.DiningReservation.DiningReservation;
import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DiningReservationTest {

    DiningReservation dinnerReservation;
    ArrayList<Menu> menuList;
    DiningRoom diningRoom;
    ArrayList<Guest> guests;

    @Before
    public void Before(){

        menuList = new ArrayList<>();
        menuList.add(new Menu("Enjoyable", 15.8));
        menuList.add(new Menu("Senses", 34.5));
        menuList.add(new Menu("Experience", 60.0));

        diningRoom = new DiningRoom("The Gourmet", 100, menuList);

        guests = new ArrayList<>();
        guests.add(new Guest("Luke", 300.0, 0.0));

        dinnerReservation = new DiningReservation(diningRoom, "2018-01-12", 1, guests);
    }


    @Test
    public void hasDinner(){
        assertEquals(diningRoom ,dinnerReservation.getDinner().getDiningRoom());
    }




}
