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

}
