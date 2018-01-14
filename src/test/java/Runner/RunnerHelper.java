package Runner;

import java.util.Scanner;

public class RunnerHelper {


    public static String getUserInput(){
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }

}
