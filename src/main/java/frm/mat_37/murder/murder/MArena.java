package frm.mat_37.murder.murder;

import org.bukkit.entity.Player;

import java.util.List;

public class MArena
{
    private String name;
    private List<Player> listPlayers;

    public MArena(String name) {
        this.name = name;
    }

    public void joinArene(Player player)
    {
        listPlayers.add(player);
    }
    public void leaveArene(Player player){
        listPlayers.remove(player);
    }

    public String getName(){
        return name;
    }

}
