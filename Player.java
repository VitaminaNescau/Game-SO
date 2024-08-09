import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player extends Thread {

    public String name;
    public int life = 100;
    public Set<Country> domains = new HashSet<Country>();

    public Player(String name) {
        super(name);
        this.name = name;
    }
    public void RandomAttack(){
        int number = new Random().nextInt(GameMain.playerNumber);
        if (!domains.add(GameMain.countries.get(number))){}

    }


    @Override
    public void run() {
        System.out.println(name + " starting... "+System.currentTimeMillis());
        while(true){
            int number = new Random().nextInt(GameMain.playerNumber);

            if (GameMain.countries.get(number).GetDomain() == null){
                GameMain.countries.get(number).SetDomain(name);
                domains.add(GameMain.countries.get(number));
                break;
            }
        }
        //System.out.println("lock");
        synchronized (GameMain.lock){
            try {
                System.out.println(Thread.currentThread().getName()+" Waiting...");
                GameMain.lock.wait();
                //System.out.println(Thread.currentThread().getName()+" Init ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 1; i <= GameMain.round; i++) {
            System.out.println(name+" ataca "+GameMain.countries.get(6).name+" que é dominado por "+GameMain.countries.get(6).domain);
            synchronized (GameMain.lock){
                try {
                    System.out.println("Aguardando outros player");
                    GameMain.lock.wait();
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }




    }
}
