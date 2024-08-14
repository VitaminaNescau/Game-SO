public  class Country {
    public  String name;
    public String domain;
    private int life = 200;


    public Country(String name){
        this.name = name;
    }
    public synchronized void SetDomain(String domain) throws RuntimeException{
        if (domain != null){
            life = life-30;
        }
        this.domain = domain;
   }
    public synchronized String GetDomain(){
        System.out.println("Verify dominion: "+Thread.currentThread().getName());
        if (GameMain.DEV_MODE) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return this.domain;}
    public void invasion(int damage){
        if (life < 1) throw new RuntimeException(" Did not gain dominion because it was destroyed");

        if (domain != null){
            life = life-damage;
        }
        if (life <1)  this.domain = domain;

    }
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", life=" + life;
    }
}
