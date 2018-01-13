import guest.Guest;
import org.junit.Before;
import org.junit.Test;
import room.diningRoom.DiningRoom;
import room.diningRoom.Dinner;
import room.diningRoom.Menu;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DinnerTest {

    Dinner dinner;

    ArrayList<Guest> guests;
    Guest guest1;
    Guest guest2;
    Guest guest3;
    ArrayList<Menu> menus;
    DiningRoom diningRoom;

    @Before
    public void Before(){

        guest1 = new Guest("Jessica", 100.0, 0.0);
        guest2 = new Guest("Ashley", 200.0, 0.0);
        guest3 = new Guest("Linda", 300.0, 0.0);
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);
        guests.add(guest3);


        menus = new ArrayList<>();
        menus.add(new Menu("Enjoyable", 15.8));
        menus.add(new Menu("Senses", 34.5));
        menus.add(new Menu("Experience", 60.0));
        diningRoom = new DiningRoom("The Gourmet", 100, menus);
        dinner     = new Dinner(diningRoom, guests);

    }

    @Test
    public void hasDinnerRoom(){
        assertEquals(diningRoom, dinner.getDiningRoom());
    }


    @Test
    public void hasGuests(){
        assertEquals(3, dinner.getGuestsForDinnerCount());
    }

    @Test
    public void canOrderMenu(){
        dinner.orderMenuByGuest(guest1, menus.get(0));
        assertEquals(menus.get(0), dinner.getMenuByGuest(guest1));
    }


    @Test
    public void canCalculateDinnerPrice(){
        dinner.orderMenuByGuest(guest1, menus.get(0));
        dinner.orderMenuByGuest(guest2, menus.get(1));
        dinner.orderMenuByGuest(guest3, menus.get(2));
        assertEquals(new Double(110.3), dinner.calculateDinnerPrice());
    }



}
