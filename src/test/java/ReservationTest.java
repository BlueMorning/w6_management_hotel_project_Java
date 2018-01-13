import guest.Guest;
import reservation.PaymentStatus;
import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import room.room.Room;
import org.junit.Before;
import org.junit.Test;
import reservation.Reservation;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class ReservationTest {

    Reservation reservation;
    BedRoom room;
    ArrayList<Guest> guests;
    Guest guest1;
    Guest guest2;


    @Before
    public void Before(){
        guest1 = new Guest("Jessica", 200.0, 0.0);
        guest2 = new Guest("Ashley", 200.0, 0.0);


        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);


        room   = new BedRoom("Blue", BedRoomType.DOUBLE, 50);
        reservation = new Reservation(room, "2018-01-12", 10, guests);
    }

    @Test
    public void hasRoom(){
        assertEquals(room, reservation.getRoom());
    }

    @Test
    public void hasGuests(){
        assertEquals(2, reservation.getGuestsCount());
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

    @Test
    public void hasTotalPriceToPay(){
        assertEquals(new Double(500) ,reservation.getTotalPriceToPay());
    }

    @Test
    public void hasTotalPricePaid(){
        reservation.guestMakePayment(guest1, 149.01);
        reservation.guestMakePayment(guest2, 50.99);
        assertEquals(new Double(200) ,reservation.getTotalPricePaid());
    }

    @Test
    public void hasBalance(){
        reservation.guestMakePayment(guest1, 149.01);
        reservation.guestMakePayment(guest2, 50.99);
        assertEquals(new Double(300) ,reservation.getBalance());
    }

    @Test
    public void hasPaymentStatus (){
        PaymentStatus statusDone = reservation.guestMakePayment(guest1, 149.01);
        assertEquals(PaymentStatus.DONE ,statusDone);

        PaymentStatus statusINSUFFICIENT_CREDIT = reservation.guestMakePayment(guest1, 149.01);
        assertEquals(PaymentStatus.INSUFFICIENT_CREDIT ,statusINSUFFICIENT_CREDIT);
    }

}
