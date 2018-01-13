import guest.Guest;
import org.junit.Before;
import org.junit.Test;
import room.diningRoom.Dinner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DinnerTest {

    Dinner dinner;

    ArrayList<Guest> guests;
    Guest guest1;
    Guest guest2;
    Guest guest3;

    @Before
    public void Before(){

        guest1 = new Guest("Jessica", 100.0, 0.0);
        guest2 = new Guest("Ashley", 200.0, 0.0);
        guest3 = new Guest("Linda", 300.0, 0.0);
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);
        guests.add(guest3);

        dinner = new Dinner(guests);
    }

    @Test
    public void hasGuests(){
        assertEquals(3, dinner.getGuestsForDinnerCount());
    }



}
