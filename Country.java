public  class Country {
    public  String name;
    public String domain;
    private int life = 100;
    private final boolean DEV_MODE = true;

    public Country(String name){
        this.name = name;
    }
    public void SetDomain(String domain){
        this.domain = domain;
   }
    public synchronized String GetDomain(){
        System.out.println("Verify dominion: "+Thread.currentThread().getName());
        if (DEV_MODE) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return this.domain;}

}
