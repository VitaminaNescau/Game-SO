import java.util.*;


public class GameMain {
    static final boolean DEV_MODE = true;
    final static int playerNumber = 8;
    /**Mutex*/
    static final Object lock = new Object();
    /**Semáforo*/
    static SemaphoreCustom semaphore = new SemaphoreCustom(3);
    //static ExecutorService executorService = Executors.newFixedThreadPool(playerNumber);
    static int round = 5;
    static ArrayList<Player> players = new ArrayList<>();
    static ArrayList<Country> countries = new ArrayList<>(
        List.of(
                new Country("Brazil"),
                new Country("Spain"),
                new Country("France"),
                new Country("Germany"),
                new Country("Italy"),
                new Country("Portugal"),
                new Country("United Kingdom"),
                new Country("United States"),
                new Country("Canada")
        )
    );
    /**
     * Método principal que inicializa o jogo. Cria e inicia as threads dos jogadores,
     * e uma thread adicional para verificar o estado dos jogadores.
     *
     */
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
/**
 * Classe responsável por verificar o estado das threads dos jogadores.
 * Implementa a lógica de barreira, esperando que todos os jogadores estejam prontos
 * antes de avançar para a próxima rodada.
 */
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
                    System.out.println("Round "+GameMain.round);
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
/**
 * Classe que implementa a lógica de um semáforo personalizado.
 * Controla o número de threads que podem acessar uma seção crítica simultaneamente.
 */
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
        System.out.println(Thread.currentThread().getName() + " acquired permission");
        permits--;
    }

    public synchronized void release() {
        permits++;
        System.out.println(Thread.currentThread().getName() + " exit semaphore");
        notify();
    }
}