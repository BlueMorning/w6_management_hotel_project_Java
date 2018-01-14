import room.conferenceRoom.ConferenceRoom;
import org.junit.Before;
import org.junit.Test;
import room.room.RoomType;

import static org.junit.Assert.assertEquals;


public class ConferenceRoomTest {

    ConferenceRoom confRoom;

    @Before
    public void Before(){
        confRoom = new ConferenceRoom("Kennedy", 30, 500);
    }

    @Test
    public void hasName(){
        assertEquals("Kennedy", confRoom.getName());
    }

    @Test
    public void hasDailyRate(){
        assertEquals(500, confRoom.getDailyRate());
    }

    @Test
    public void hasRoomType(){
        assertEquals(RoomType.CONFROOM, confRoom.getRoomType());
    }

}
