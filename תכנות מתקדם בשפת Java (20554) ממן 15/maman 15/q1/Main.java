/**
 * @author (Faigy Shternel)
 * @version (26.05.2022)
 */
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Hello please enter number of treads");
        int n = s.nextInt();
        System.out.println("Hello please enter number of elements");
        int m = s.nextInt();
        Random rand = new Random();
        Integer[] arr = new Integer[m];

        for (int i = 0; i < m; i++) {
            arr[i] = rand.nextInt(100);
        }

        Thread[] threads = new Thread[n];
        MyRepo repo = new MyRepo(arr, n);
        for (int i = 0; i < n; i++) {
            threads[i] = new Compute(repo);
            threads[i].start();
        }
        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //ALL THREADS FINISHED
        if (repo.getVec().size() != 1) {
            System.out.println("ERROR, vec size: " + repo.getVec().size());
        } else {
            System.out.println("sum is " + repo.getSum());
        }
    }
}