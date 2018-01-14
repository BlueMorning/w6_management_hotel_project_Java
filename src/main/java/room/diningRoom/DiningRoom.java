package room.diningRoom;

import room.room.Room;
import room.room.RoomType;

import java.util.ArrayList;

public class DiningRoom extends Room {


    private ArrayList<Menu> menusList;

    public DiningRoom(String name, int capacity, ArrayList<Menu> menusList) {
        super(name, capacity);
        this.menusList = menusList;
        this.roomType  = RoomType.DININGROOM;
    }

    public ArrayList<Menu> getMenusList(){
        return this.menusList;
    }

    public int getMenusCount() {
        return menusList.size();
    }

}
