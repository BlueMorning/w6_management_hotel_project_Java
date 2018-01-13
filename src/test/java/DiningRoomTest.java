import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DiningRoomTest {


    DiningRoom diningRoom;
    ArrayList<Menu> menuList;

    @Before
    public void Before(){
        menuList = new ArrayList<>();
        menuList.add(new Menu("Enjoyable", 15.8));
        menuList.add(new Menu("Senses", 34.5));
        menuList.add(new Menu("Experience", 60.0));

        diningRoom = new DiningRoom("The Gourmet", 100, menuList);
    }

    @Test
    public void hasName(){
        assertEquals("The Gourmet", diningRoom.getName());
    }

    @Test
    public void hasMenusList(){
        assertEquals(3, diningRoom.getMenusCount());
    }





}
