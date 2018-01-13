import room.room.Room;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RoomTest {

    Room room;

    @Before
    public void Before (){
        room = new Room("Blue", 2);
    }

    @Test
    public void hasName(){
        assertEquals("Blue", room.getName());
    }

    @Test
    public void hasCapacity(){
        assertEquals(2, room.getCapacity());
    }



}
