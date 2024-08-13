public  class Country {
    public  String name;
    public String domain;
    private int life = 100;


    public Country(String name){
        this.name = name;
    }
    public synchronized void SetDomain(String domain){
        if (life < 1) throw new RuntimeException(" Did not gain dominion because it was destroyed");
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

}
