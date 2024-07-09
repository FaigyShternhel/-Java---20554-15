/** this class represents a thread
 * @author (Faigy Shternel)
 * @version (26.05.2022)
 */
public class Compute extends Thread{
    private Repository rep;
    public Compute (Repository rep)
    {
        this.rep = rep;
    }

    //run method- this method turns on the threads
    @Override
    public void run(){
        int[] couple; //will contain the 2 cells from the array
        couple = rep.removeCouple();
        while (couple != null){
            rep.insert(couple[0]+couple[1]);
            couple = rep.removeCouple();
        }
    }
}