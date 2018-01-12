import guest.Guest;
import hotel.room.Room;
import org.junit.Before;
import org.junit.Test;
import reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ReservationTest {

    Reservation reservation;
    Room room;
    ArrayList<Guest> guests;

    @Before
    public void Before(){
        guests = new ArrayList<>();
        reservation = new Reservation(room, "2018-01-12", 10, guests);
    }

    @Test
    public void hasRoom(){
        assertEquals(room, reservation.getRoom());
    }


    @Test
    public void hasStartDateString(){
        assertEquals("2018-01-12", reservation.getStartDateString());
    }

    @Test
    public void hasDuration(){
        assertEquals(10, reservation.getDuration());
    }

    @Test
    public void hasStatus_Ongoing(){
        assertEquals(10, reservation.getDuration());
    }

    @Test
    public void hasStatus_Done(){
        assertEquals(10, reservation.getDuration());
    }

    @Test
    public void hasStartDate(){
        assertEquals(LocalDate.of(2018, 1, 12), reservation.getStartDate());
    }

    @Test
    public void hasEndDate(){
        assertEquals(LocalDate.of(2018, 1, 21), reservation.getEndDate());
    }
}
