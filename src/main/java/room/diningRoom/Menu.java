package room.diningRoom;

public class Menu {

    private String name;
    private Double price;

    public Menu(String name, Double price){
        this.name  = name;
        this.price = price;
    }

    public String getName(){
        return this.name;
    }

    public Double getPrice(){
        return this.price;
    }


}
