package hotel.bedroom;

public enum BedRoomType {

    SINGLE(1),
    DOUBLE(2);


    private int capacity;

    BedRoomType(int capacity){
        this.capacity = capacity;
    }

    public int getCapacity(){
        return this.capacity;
    }



}
