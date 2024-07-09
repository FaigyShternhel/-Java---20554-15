/** This class represents a vector created by an array
 * and methods that summeries the total of all numbers in the array
 * @author (Faigy Shternel)
 * @version (26.05.2022)
 */
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class MyRepo implements Repository {
    private Vector<Integer> vec; //vector to transfor the array
    private int n;
    private int counter = 0;

    //constractor
    public MyRepo(Integer[] arr, int n) {
        this.vec = new Vector<>();
        Collections.addAll(vec, arr);
        this.n = n;
    }

    // this method removes 2 elements from the vector and keeps them in an array to be returend
    public synchronized int[] removeCouple() {
        int [] temp = new int[2];
        while(vec.size() < 2){
            try {
                counter++;
                if(counter >= n){
                    notifyAll();
                    return null;
                }
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        temp[0] = vec.remove(0);
        temp[1] = vec.remove(0);
        return temp;
    }

    //this method gets a numberan d adds it to the array
    public synchronized void insert(int num) {
        vec.add(num);
        notifyAll();
        counter = 0;
    }

    //this method returns the total sum of the array
    public int getSum()
    {
        return vec.get(0);
    }

    //get method
    public Vector<Integer> getVec() {
        return vec;
    }


}
