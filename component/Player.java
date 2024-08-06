package component;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Player {

    public String name;
    public int score = 1000;

    public Set<Country> domains = new HashSet<Country>();

    public Player(String name) {
        this.name = name;
    }

}
