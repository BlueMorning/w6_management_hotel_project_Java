import room.diningRoom.Menu;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MenuTest {

    Menu menu;


    @Before
    public void Before(){
        menu = new Menu("Senses", 34.8);
    }


    @Test
    public void hasName(){
        assertEquals("Senses", menu.getName());
    }

    @Test
    public void hasPrice(){
        assertEquals(new Double(34.8), menu.getPrice());
    }



}
