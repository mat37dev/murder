package frm.mat_37.murder.murder;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MArena
{
    private String name;
    private List<Player> listPlayers;
    private String state;
    private List<Location> spawns;
    private List<Location> spawnsGolds;
    private Location lobby;

    //region constructor
    public MArena(String name) {
        this.name = name;
        listPlayers = new ArrayList<>();
        state = "CREATE";
        spawns = new ArrayList<>();
        spawnsGolds = new ArrayList<>();
        lobby = null;
    }

    public MArena(String name, String state, List<Location> spawns,List<Location> spawnsGolds, Location lobby)
    {
        this.name = name;
        listPlayers = new ArrayList<>();
        this.state = state;
        this.spawns = spawns;
        this.spawnsGolds = spawnsGolds;
        this.lobby = lobby;
    }


    //endregio

    //region getters
    public String getName(){return name;}
    public String getState(){return state;}
    public List<Location> getSpawnsGolds() {return spawnsGolds;}
    public List<Location> getSpawns(){return spawns;}
    public Location getLobby() {return lobby;}
    //endregion

    //region setters
    public void setName(String name) {this.name = name;}
    public void setState(String state){this.state = state;}
    public void setLobby(Location lobby) {this.lobby = lobby;}
    //endregion

    //region methods
    public void joinArene(Player player)
    {
        listPlayers.add(player);
    }
    public void leaveArene(Player player){
        listPlayers.remove(player);
    }

    public void addSpawns(Location spawn)
    {
        spawns.add(spawn);
    }
    public void removeSpawns(Location spawn){
        spawns.remove(spawn);
    }

    public void addSpawnsGolds(Location spawnsGolds) {this.spawnsGolds.add(spawnsGolds);}
    public void removeSpawnsGolds(Location spawnsGolds){this.spawnsGolds.remove(spawnsGolds);}
    //endregion




}
