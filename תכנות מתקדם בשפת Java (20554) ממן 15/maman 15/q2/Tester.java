/**
 * This class represents the main function to create and activate threads array
 * that goes over the array m rounds and update it as wanted
 *
 * @author (Faigy Shternel)
 * @version (1.6.2022)
 */
import java.util.Random;
import java.util.Scanner;

public class Tester {

    public static void main(String[] args){
        int maxRand =  100;
        Scanner scan = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("Please enter number of Threads: ");
        int n = scan.nextInt(); //num of threads
        System.out.println("Please enter number of rounds: ");
        int m = scan.nextInt(); //num of rounds
        NewThread[] arr = new NewThread[n]; //array of threads
        for (int i=0; i<arr.length; i++) //create threads
            arr[i] = new NewThread(i,rand.nextInt(maxRand)+1, arr);
        System.out.println("Our array at beginning status is: " + arr[0]); //Print array in beginning status
        for (int j=1; j<=m; j++) {
            for (int i = 0; i < arr.length; i++) //run Threads for this round
                arr[i].start();
            System.out.println("Our array after "+j+" checks: " + arr[0].getArr()); //Print array after current round
        }
    }
}
