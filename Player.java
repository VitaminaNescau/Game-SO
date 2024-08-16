import java.util.*;

public class Player extends Thread {

    public String name;
    public Player(String name) {
        super(name);
        this.name = name;
    }
    /**
     * Método que gera um ataque de dano aleatório no intervalo de 0 a 99.
     * O método utiliza um semáforo para garantir que apenas uma thread possa
     * realizar um ataque de cada vez.
     *
     * @return Um valor inteiro representando o dano do ataque.
     */
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
            int number = new Random().nextInt(GameMain.playerNumber+1);

            if (GameMain.countries.get(number).GetDomain() == null){
                try {
                    GameMain.countries.get(number).SetDomain(name);

                    break;
                }catch (RuntimeException e){
                    System.out.println(name + e.getMessage());
                }

            }
        }

        // Espera até que todos os jogadores estejam prontos para iniciar a rodada
        synchronized (GameMain.lock){
            try {
                System.out.println(Thread.currentThread().getName()+" Waiting...");
                GameMain.lock.wait();
                //System.out.println(Thread.currentThread().getName()+" Init ");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        // Realiza ataques em outros países enquanto houverem rodadas restantes

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
            // Espera até que todos os jogadores tenham completado suas ações na rodada atual
            synchronized (GameMain.lock){
                try {
                    System.out.println("awaiting other players");
                    GameMain.lock.wait();
                    if (GameMain.DEV_MODE) Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }




    }
}
