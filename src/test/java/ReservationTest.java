import hotel.Room;
import hotel.bedroom.BedRoom;
import org.junit.Before;
import org.junit.Test;
import reservation.Reservation;

import static org.junit.Assert.assertEquals;


public class ReservationTest {

    Reservation reservation;
    Room room;

    @Before
    public void Before(){
        reservation = new Reservation(room, "2018/1/10", 10);
    }

    @Test
    public void hasRoom(){
        assertEquals(room, reservation.getRoom());
    }


    @Test
    public void hasStartDate(){
        assertEquals("2018/1/10", reservation.getStartDate());
    }

    @Test
    public void hasDuration(){
        assertEquals(10, reservation.getDuration());
    }

}
