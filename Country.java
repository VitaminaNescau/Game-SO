public  class Country {
    public  String name;
    public String domain;
    private int life = 200;


    public Country(String name){
        this.name = name;
    }
    /**
     * Método sincronizado que define o domínio do país. Se o domínio já estiver definido,
     * a vida do país é reduzida em 30 unidades.
     *
     * @param domain O nome do jogador que está tentando dominar o país.
     */
    public synchronized void SetDomain(String domain){
        if (domain != null){
            life = life-30;
        }
        this.domain = domain;
   }
    /**
     * Método sincronizado que retorna o domínio atual do país (quem controla o país).
     * Se o jogo estiver no modo de desenvolvimento, há um atraso de 1 segundo para simular a verificação.
     *
     * @return O nome do jogador que controla o país.
     */
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
    /**
     * Método que simula uma invasão ao país, causando dano à sua vida.
     * Se a vida do país for menor que 1, ele é destruído e uma exceção é lançada.
     *
     * @param damage A quantidade de dano causada pela invasão.
     * @throws RuntimeException Se o país for destruído antes de ser conquistado.
     */
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
