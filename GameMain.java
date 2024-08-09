import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO use wait and notify for switch message, create variable type integer for semaphore
/** Project for operating system matter*/
public class GameMain {
    final static int playerNumber = 2;
    static final Object lock = new Object();
     static ExecutorService executorService = Executors.newFixedThreadPool(playerNumber);
    static int round = 5;
    static ArrayList<Player> players = new ArrayList<>();
    static HashMap<Integer, Country> countries = new HashMap<>
            ( Map.of(
                    0, new Country("Brasil"),
                    1, new Country("Espanha"),
                    2, new Country("França"),
                    3, new Country("Alemanha"),
                    4, new Country("Itália"),
                    5, new Country("Portugal"),
                    6, new Country("Reino Unido"),
                    7, new Country("Estados Unidos"),
                    8, new Country("Canadá")
            )
            );

    public static void main(String[] args) throws InterruptedException {

    Debug.logInfo("Starting game");

    for (int i = 0; i < playerNumber; i++) {
        players.add(new Player("Player "+i ));
        //executorService.execute(players.get(i));
       players.get(i).start();

    }
    new Thread(()->{
        while (true){
            int valid = 0;
            for (int i = 0; i < players.size(); i++) {
               // System.out.println(players.get(i).getState().name());
                if (players.get(i).getState().name().equals("WAITING")){
                    valid++;
                };
            }
            if (valid==playerNumber){
                /** Trade message or semaphore?*/
                synchronized (lock) {lock.notifyAll();}
                break;
            }
        }
    }).start();

    }
    public  synchronized void produce() throws InterruptedException {

    }
    public static synchronized void consume(){

    }

}
