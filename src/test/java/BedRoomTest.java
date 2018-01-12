import hotel.BedRoom;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BedRoomTest {

    BedRoom bedRoom;

    @Before
    public void Before(){
        bedRoom = new BedRoom("Orange", 2, 50);
    }


    @Test
    public void hasName(){
        assertEquals("Orange", bedRoom.getName());
    }



}
