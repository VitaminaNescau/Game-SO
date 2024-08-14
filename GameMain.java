import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//TODO use wait and notify for switch message, create variable type integer for semaphore
/** Project for operating system matter*/
public class GameMain {
    static final boolean DEV_MODE = true;
    final static int playerNumber = 8;
    /**Mutex*/
    static final Object lock = new Object();
    /**Semaphore*/
    static SemaphoreCustom semaphore = new SemaphoreCustom(2);
    //static ExecutorService executorService = Executors.newFixedThreadPool(playerNumber);
    static int round = 5;
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Country> countries = new ArrayList<>(
        List.of(
                new Country("Brasil"),
                new Country("Espanha"),
                new Country("França"),
                new Country("Alemanha"),
                new Country("Itália"),
                new Country("Portugal"),
                new Country("Reino Unido"),
                new Country("Estados Unidos"),
                new Country("Canadá")
        )
    );
    public static void main(String[] args) throws InterruptedException {

    Debug.logInfo("Starting game");

    for (int i = 0; i < playerNumber; i++) {
        players.add(new Player("Player "+i ));
        //executorService.execute(players.get(i));
       players.get(i).start();

    }
    new Thread(new Verify()).start();

    }

}
/** Trade message  or barrier?*/
class Verify implements Runnable  {
    public void run() {
        while (true){
            int valid = 0;
            for (int i = 0; i < GameMain.players.size(); i++) {
                // System.out.println(players.get(i).getState().name());
                if (GameMain.players.get(i).getState().name().equals("WAITING")){
                    valid++;
                };
            }
            if (valid==GameMain.playerNumber){

                try {
                    System.out.println("All players are ready");
                    GameMain.round--;
                    if (GameMain.DEV_MODE) Thread.sleep(2000);

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    synchronized (GameMain.lock) {GameMain.lock.notifyAll();}
                }
                // break;
            }
            if (GameMain.round ==0){
                System.out.println("Dominion report");
                GameMain.countries.forEach(System.out::println);
                break;
            };

        }
    }
}
/** Custom logic of semaphore*/
class SemaphoreCustom{
    private int permits;

    SemaphoreCustom(int permits){
        this.permits = permits;
    }
    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            if (GameMain.DEV_MODE) System.out.println(Thread.currentThread().getName()+" awaiting permission");
            wait();
        }
        System.out.println(Thread.currentThread().getName() + " acquired ");
        permits--;
    }

    public synchronized void release() {
        permits++;
        notify();
    }
}