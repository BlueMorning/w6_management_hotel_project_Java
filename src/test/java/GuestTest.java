import guest.Guest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuestTest {

    Guest guest;

    @Before
    public void Before(){
        guest = new Guest("William", 250.7, 300.0);
    }


    @Test
    public void hasName(){
        assertEquals("William", guest.getName());
    }

    @Test
    public void hasWallet(){
        assertEquals(new Double(250.7), guest.getCurrentWallet());
    }

    @Test
    public void hasSpending(){
        assertEquals(new Double(300.0), guest.getSpending());
    }

    @Test
    public void canPay(){
        guest.Pay(50.7);
        assertEquals(new Double(200.0), guest.getCurrentWallet());
        assertEquals(new Double(350.7), guest.getSpending());
    }

    @Test
    public void canCreditwallet(){
        guest.creditWallet(500.0);
        assertEquals(new Double(750.7), guest.getCurrentWallet());
    }

    @Test
    public void canFakeGuest(){
        Guest fakedGuest = Guest.GetFakedGuest();
        assertEquals("", fakedGuest.getName());
    }

}
