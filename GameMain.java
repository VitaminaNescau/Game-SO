import component.Country;
import component.Player;
import utils.Debug;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO use wait and notify for switch message, create variable type integer for semaphore
/** Project for operating system matter*/
public class GameMain {
    final static int playerNumber = 3;
    static ExecutorService executorService = Executors.newFixedThreadPool(playerNumber);
    static int round = 5;


    public static void main(String[] args){

        Debug.logInfo("Starting Game");
        HashMap<Integer, Country> countries = new HashMap<>
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
        //TODO add logic to save select domains
    ArrayList<Integer> positions = new ArrayList<>();
        for (int i = 0; i <= playerNumber; i++) {
            int finalI = i;
            var t =    executorService.submit(() -> {
                Player player = new Player("Player "+finalI);
                Debug.logInfo( player.name + " started");
                while (true){
                    int position = new Random().nextInt(8);
                    System.out.println(position+" "+Thread.currentThread().getName());
                    /**
                     * Mutex
                     * */
                    synchronized (countries.get(position)) {
                        if (countries.get(position).domain == null) {

                            countries.get(position).SetDomain(player.name);
                            player.domains.add(countries.get(position));
                            positions.add(position);
                            System.out.println(player.name+" Conquistou "+ countries.get(position).name);
                            return player;
                        }
                        System.out.println(player.name+" não Conquistou "+ countries.get(position).name);
                    }
                }

            });

        }



    }


}
