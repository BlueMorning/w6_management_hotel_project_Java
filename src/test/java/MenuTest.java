import org.junit.Before;
import org.junit.Test;
import room.diningRoom.Menu;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MenuTest {

    Menu menu;

    @Before
    public void Before(){
        menu = new Menu("Senses", 15.8);
    }


    @Test
    public void hasName(){
        assertEquals("Senses", menu.getName());
    }

    @Test
    public void hasPrice(){
        assertEquals(new Double(15.8), menu.getPrice());
    }



}
