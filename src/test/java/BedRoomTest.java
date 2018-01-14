import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import org.junit.Before;
import org.junit.Test;
import room.room.RoomType;

import static org.junit.Assert.assertEquals;

public class BedRoomTest {

    BedRoom bedRoom;

    @Before
    public void Before(){
        bedRoom = new BedRoom("Orange", BedRoomType.DOUBLE, 50);
    }


    @Test
    public void hasName(){
        assertEquals("Orange", bedRoom.getName());
    }

    @Test
    public void hasNightRate(){
        assertEquals(50, bedRoom.getNightRate());
    }

    @Test
    public void hasType(){
        assertEquals(BedRoomType.DOUBLE, bedRoom.getType());
    }

    @Test
    public void hasCapacity(){
        assertEquals(2, bedRoom.getCapacity());
    }

    @Test
    public void hasRoomType(){
        assertEquals(RoomType.BEDROOM, bedRoom.getRoomType());
    }

}
