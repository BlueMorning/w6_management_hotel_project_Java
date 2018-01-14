package room.diningRoom;

import guest.Guest;

import java.util.ArrayList;
import java.util.HashMap;

public class Dinner {

    private DiningRoom diningRoom;
    private HashMap<Guest, Menu> guestsForDinner;


    public Dinner(DiningRoom diningRoom, ArrayList<Guest> guests)
    {
        this.diningRoom = diningRoom;
        guestsForDinner = new HashMap<>();
    }

    public HashMap<Guest, Menu> getGuestsForDinner(){
        return guestsForDinner;
    }

    public Double calculateDinnerPrice(){

        Double totalPrice = 0.0;

        for(Guest guest: guestsForDinner.keySet()){
            totalPrice += guestsForDinner.get(guest).getPrice();
        }

        return totalPrice;

    }

    public int getGuestsForDinnerCount() {
        return guestsForDinner.size();
    }

    public void orderMenuByGuest(Guest guest, Menu menu) {
        if(guestsForDinner.containsKey(guest)) {
            guestsForDinner.replace(guest, menu);
        }
        else{
            guestsForDinner.put(guest, menu);
        }
    }

    public Menu getMenuByGuest(Guest guest) {
        return guestsForDinner.get(guest);
    }

    public DiningRoom getDiningRoom(){
        return this.diningRoom;
    }
}
