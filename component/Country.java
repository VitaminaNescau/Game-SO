package component;

import utils.Debug;

public  class Country {
    public  String name;
    public String domain;
    private int life = 100;


    public Country(String name){
        this.name = name;
    }

   public void SetDomain(String domain){
        this.domain = domain;
   }

}
