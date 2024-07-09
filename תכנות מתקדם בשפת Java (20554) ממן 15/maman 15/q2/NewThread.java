/**
 * This class represents a thread that is part of an array of threads,
 * each thread checks the value of his neighbors - if they are both bigger than him he increeses his value in one,
 * if they are both smaller than him he decreeses his value in one, otherwise he doesn't change.
 *
 * @author (Faigy Shternel)
 * @version (1.6.2022)
 */
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewThread extends Thread {
    private int index; //thread id;
    private int randNum; //thread num
    private NewThread[] arr;
    static int readCounter = 0;
    static int writeCounter = 0;
    static Lock lock1 = new ReentrantLock();
    static Condition cond1 = lock1.newCondition();
    static Lock lock2 = new ReentrantLock();
    static Condition cond2 = lock2.newCondition();

    /*Constructor*/
    public NewThread(int index, int randNum, NewThread[] arr) {
        this.index = index;
        this.randNum = randNum;
        this.arr = arr;
    }

    public void run() {
        super.run();
        int myLeft = getLeft();//get left neighbor
        int myRight = getRight();//get right neighbor
        if (myLeft < randNum && myRight < randNum) //set my value
            setNum(randNum - 1);
        else if (myLeft > randNum && myRight > randNum)
            setNum(randNum + 1);
        else  setNum(randNum);
    }

    /*return randNum*/
    public int getNum() {
        return randNum;
    }

    /*This function gets the value to be set and sets this thread in arr to a new thread containing the correct values for next round*/
    public void setNum(int num) {
        lock1.lock();
        try {
            while (readCounter < arr.length)
                cond1.await(); //wait for all threads to read there neighbors
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock1.unlock();
        }
        arr[index] = new NewThread(index, num, arr);//update num, creating new thread in order to have another round if needed
        lock2.lock();
        writeCounter++; //this thread finished writing
        try {
            if (writeCounter == arr.length) {
                cond2.signalAll(); //wake up who ever is waiting for all threads to finish running
            }
        } finally {
            lock2.unlock();
        }
    }

    /*this function returns the left neighbor*/
    public int getLeft() {
        return arr[(index - 1 + arr.length) % arr.length].getNum(); //left neighbor
    }

    /*this function returns the right neighbor*/
    public int getRight() {
        int temp = arr[(index + 1) % arr.length].getNum(); //right neighbor
        lock1.lock();
        readCounter++; //finished to read my neighbors
        try {
            if (readCounter == arr.length) {
                readCounter++;
                cond1.signalAll();//wake up whoever is waiting to write
            }
        } finally {
            lock1.unlock();
        }
        return temp;
    }

    /*this function returns a String representing the current state of arr and resets all static needed values*/
    public String getArr() {
        lock2.lock();
        try {
            while (writeCounter < arr.length)
                cond2.await(); //wait till all threads update there num and release the lock meanwhile
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            lock2.unlock();
        }
        lock1.lock();
        readCounter = 0;//finished round
        lock1.unlock();
        lock2.lock();
        writeCounter = 0;//finished round
        lock2.unlock();
        return arr[0].toString();
    }

    /*this function returns a String representing the current state of arr*/
    public String toString() {
        String s = "[";
        for (NewThread t : arr)
            s = s + t.getNum() + ",";
        return s.substring(0, s.length() - 1) + "]";
    }
}
