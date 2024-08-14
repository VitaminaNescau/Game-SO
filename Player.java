import java.util.*;

public class Player extends Thread {

    public String name;
    public int score = 100;
    public Set<Country> domains = new HashSet<Country>();
    public Player(String name) {
        super(name);
        this.name = name;
    }

    public int randomDamageAttack(){
        try {
            GameMain.semaphore.acquire();
          var n =  new Random().nextInt(100);
            return n;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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


        synchronized (GameMain.lock){
            try {
                System.out.println(Thread.currentThread().getName()+" Waiting...");
                GameMain.lock.wait();
                //System.out.println(Thread.currentThread().getName()+" Init ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        while(GameMain.round >0) {
            int number = new Random().nextInt(GameMain.countries.size());

            try {
                GameMain.countries.get(number).invasion(randomDamageAttack());
                System.out.println(name+" attack "+GameMain.countries.get(number).name);
                if (GameMain.DEV_MODE) Thread.sleep(2000);
            }catch (RuntimeException | InterruptedException e){
                System.out.println( name + e.getMessage());
            }finally {

                GameMain.semaphore.release();
            }

            synchronized (GameMain.lock){
                try {
                    System.out.println("Aguardando outros player");
                    GameMain.lock.wait();
                    if (GameMain.DEV_MODE) Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }




    }
}
