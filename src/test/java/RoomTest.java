import room.room.Room;
import org.junit.Before;
import org.junit.Test;
import room.room.RoomType;

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

    @Test
    public void hasIsActive(){
        assertEquals(true, room.isActive());
    }

    @Test
    public void canSetIsActive(){
        room.setIsActive(false);
        assertEquals(false, room.isActive());
    }

    @Test
    public void hasRoomType(){
        assertEquals(RoomType.ROOM, room.getRoomType());
    }

}
