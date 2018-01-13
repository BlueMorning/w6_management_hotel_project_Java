package room.diningRoom;

import guest.Guest;

import java.util.ArrayList;
import java.util.HashMap;

public class Dinner {

    private HashMap<Guest, Menu> guestsForDinner;

    public Dinner(ArrayList<Guest> guests)
    {
        guestsForDinner = new HashMap<>();

        for(Guest guest: guests){
            guestsForDinner.put(guest, null);
        }

    }

    public HashMap<Guest, Menu> getGuestsForDinner(){
        return guestsForDinner;
    }

    public Double calculateDinerPrice(){

        Double totalPrice = 0.0;

        for(Guest guest: guestsForDinner.keySet()){
            totalPrice += guestsForDinner.get(guest).getPrice();
        }

        return totalPrice;

    }

    public int getGuestsForDinnerCount() {
        return guestsForDinner.size();
    }
}
