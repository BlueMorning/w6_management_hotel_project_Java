package Runner;

import guest.Guest;
import hotel.Hotel;
import reservation.Reservation;
import room.bedroom.BedRoom;
import room.bedroom.BedRoomType;
import room.conferenceRoom.ConferenceRoom;
import room.diningRoom.DiningRoom;
import room.diningRoom.Menu;
import room.room.Room;
import room.room.RoomType;

import java.util.ArrayList;

public class RunnerForGuest {

    private static Hotel hotel;
    private static Guest guestConnected;

    public static void main(String args[]){

        createHotelAndPopulateRoom();
        chooseHomeMenu();
    }



    public static void createHotelAndPopulateRoom(){

        hotel = new Hotel("The Blue Morning", new ArrayList<>());
        hotel.addNewRoom(new BedRoom("Blue", BedRoomType.DOUBLE, 50));
        hotel.addNewRoom(new BedRoom("Autumn", BedRoomType.DOUBLE, 60));
        hotel.addNewRoom(new BedRoom("CatchDreamer", BedRoomType.DOUBLE, 80));
        hotel.addNewRoom(new BedRoom("MilkyWay", BedRoomType.DOUBLE, 200));
        hotel.addNewRoom(new BedRoom("Zen", BedRoomType.SINGLE, 100));
        hotel.addNewRoom(new BedRoom("Summer Rain", BedRoomType.SINGLE, 200));
        hotel.addNewRoom(new ConferenceRoom("BrainStorm", 50, 200));
        hotel.addNewRoom(new ConferenceRoom("Arena", 100, 300));
        hotel.addNewRoom(new ConferenceRoom("TheGather", 200, 500));

        DiningRoom diningRoom1 = new DiningRoom("The Gourmet", 5, new ArrayList<>());
        diningRoom1.addMenu(new Menu("Enjoyable", 20.0));
        diningRoom1.addMenu(new Menu("Senses", 30.0));
        diningRoom1.addMenu(new Menu("Experience", 50.0));
        hotel.addNewRoom(diningRoom1);

        DiningRoom diningRoom2 = new DiningRoom("The Travel", 5, new ArrayList<>());
        diningRoom2.addMenu(new Menu("Europe", 30.0));
        diningRoom2.addMenu(new Menu("Asia", 30.0));
        diningRoom2.addMenu(new Menu("Africa", 30.0));
        hotel.addNewRoom(diningRoom2);
    }



    public static void chooseHomeMenu(){

        guestConnected = null;

        System.out.println("Select an option below :");
        System.out.println("1 => Create a new Guest account");
        System.out.println("2 => Connect with your account");
        String option = RunnerHelper.getUserInput();

        if(     option.equals("1")){
            createNewAccount();
        }
        else if(option.equals("2")){
            connectWithAccount();
        }
        else {
            chooseHomeMenu();
        }
    }

    public static void createNewAccount(){
        System.out.println("Enter your name :");
        String name = RunnerHelper.getUserInput();
        System.out.println("Enter your credit amount :");
        Double credit = Double.parseDouble(RunnerHelper.getUserInput());

        if(hotel.getGuestsMap().get(name) == null){
            Guest guest = new Guest(name, credit, 0.0);
            hotel.addGuest(guest);
            guestConnected = hotel.getGuestsMap().get(name);
            chooseAccountMenu();
        }
        else {
            System.out.println("Sorry, this account already exists");
            chooseHomeMenu();
        }
    }

    public static void connectWithAccount(){
        System.out.println("Enter your account name :");
        String name = RunnerHelper.getUserInput();

        if(hotel.getGuestsMap().get(name) != null) {
            guestConnected = hotel.getGuestsMap().get(name);
            System.out.println(String.format("Welcome %s", guestConnected.getName()));
            chooseAccountMenu();
        }
        else {
            System.out.println("Sorry, this account does not exist");
            chooseHomeMenu();
        }
    }

    public static void chooseAccountMenu(){
        System.out.println(String.format("Your wallet : $%s", guestConnected.getCurrentWallet()));
        System.out.println(String.format("Your spending : $%s", guestConnected.getSpending()));
        System.out.println("Select an option below :");
        System.out.println("1 => Make a reservation");
        System.out.println("2 => See your history");
        System.out.println("3 => Quit");

        String option = RunnerHelper.getUserInput();

        if(option.equals("1")){
            makeAReservation();
        }
        else if(option.equals("2")){
            displayReservation();
        }
        else if(option.equals("3")){
            chooseHomeMenu();
        }
        else {
            chooseAccountMenu();
        }
    }

    public static void makeAReservation(){
        System.out.println("Enter the kind of room you are looking for :");
        System.out.println("\"bedroom\" or \"conference\" or \"dining\"");
        String roomType = RunnerHelper.getUserInput();

        System.out.println("Enter your start date :");
        String date = RunnerHelper.getUserInput();

        System.out.println("Enter the duration of your stay :");
        Integer duration = Integer.parseInt(RunnerHelper.getUserInput());

        System.out.println("Enter the number of guests :");
        Integer guestsNumber = Integer.parseInt(RunnerHelper.getUserInput());

        ArrayList<Room> roomsFound = hotel.searchForRooms(getRoomTypeFromString(roomType), date, duration, guestsNumber);

        for(Room room : roomsFound){
            System.out.print(String.format("Name of the room : %s", room.getName()));
            System.out.print(String.format(", Capacity : %s", room.getCapacity()));

            if(     room.getRoomType() == RoomType.BEDROOM){
                System.out.print(String.format(", Price per night : $%s", ((BedRoom)(room)).getNightRate()));
            }
            else if(room.getRoomType() == RoomType.CONFROOM){
                System.out.print(String.format(", Price per day : $%s", ((ConferenceRoom)(room)).getDailyRate()));
            }
            else if(room.getRoomType() == RoomType.DININGROOM){
                System.out.print(", Price depends on the menu you will choose :");
                for(Menu menu : ((DiningRoom)room).getMenusList()){
                    System.out.println(String.format("%s : $%s", menu.getName(), menu.getPrice()));
                }
            }
        }

        createReservation(date, duration);

    }

    public static void createReservation(String startingDate, Integer duration){

        System.out.println("Enter the name of the room you want to book :");
        String roomName = RunnerHelper.getUserInput();
        Room room = hotel.getRoomByName(roomName);

        if(room == null){
            System.out.println("This room does not exist");
            createReservation(startingDate, duration);
        }
        else {
            ArrayList<Guest> guestsList = new ArrayList<>();
            guestsList.add(guestConnected);
            Reservation reservation = hotel.checkReservationToAdd(room, startingDate, duration, guestsList);
            reservation.guestMakePayment(guestConnected, reservation.getTotalPriceToPay());
            chooseAccountMenu();
        }
    }

    public static void displayReservation(){
        for(Reservation reservation : hotel.getOnGoingReservations()){
            System.out.println(String.format("Room > %s, %s, Date : %s, Duration : %s, Price to pay $%s, Paid : $, Balance $%s",
                    reservation.getRoom().getRoomType().toString(),
                    reservation.getRoom().getName(),
                    reservation.getStartDate(),
                    reservation.getDuration(),
                    reservation.getTotalPriceToPay(),
                    reservation.getTotalPricePaid(),
                    reservation.getBalance()));
        }

        chooseAccountMenu();
    }


    public static RoomType getRoomTypeFromString(String roomTypeString){
        if(roomTypeString.equals("bedroom")){
            return RoomType.BEDROOM;
        }
        else if(roomTypeString.equals("conference")) {
            return RoomType.CONFROOM;
        }
        else if(roomTypeString.equals("dining")) {
            return RoomType.DININGROOM;
        }
        else {
            return RoomType.BEDROOM;
        }
    }


}
