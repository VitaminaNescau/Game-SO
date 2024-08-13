import java.util.*;

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
                try {
                    GameMain.countries.get(number).SetDomain(name);
                    domains.add(GameMain.countries.get(number));
                    break;
                }catch (RuntimeException e){
                    System.out.println(name + e.getMessage());
                }

            }
        }


        ArrayList<Country> t = new ArrayList<>(GameMain.countries.values());
        t.removeAll(domains);
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
            int number = new Random().nextInt(t.size());
            System.out.println(name+" ataca "+t.get(number).name+" que é dominado por "+( t.get(number).domain==null?"ninguém":t.get(number).domain));

            t.remove(number);
            synchronized (GameMain.lock){
                try {
                    System.out.println("Aguardando outros player");
                    GameMain.lock.wait();
                    if (GameMain.DEV_MODE) Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }




    }
}
