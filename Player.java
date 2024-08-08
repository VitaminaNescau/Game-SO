import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player extends Thread {

    public String name;
    public int life = 100;
    public Set<Country> domains = new HashSet<Country>();

    public Player(String name) {
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
        System.out.println("Finalizado");
//            while(true){
//                System.out.println(1);
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            }

    }
}
