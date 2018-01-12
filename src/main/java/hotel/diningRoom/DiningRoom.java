package hotel.diningRoom;

import hotel.room.Room;

import java.util.ArrayList;

public class DiningRoom extends Room {


    private ArrayList<Menu> menusList;

    public DiningRoom(String name, int capacity, ArrayList<Menu> menusList) {
        super(name, capacity);
        this.menusList = menusList;
    }

    public ArrayList<Menu> getMenusList(){
        return this.menusList;
    }

    public int getMenusCount() {
        return menusList.size();
    }
}
