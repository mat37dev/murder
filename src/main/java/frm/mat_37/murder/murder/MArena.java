package frm.mat_37.murder.murder;



import frm.mat_37.murder.murder.runnable.Timer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

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
    private Location spectatorSpawn;
    private boolean timerStatue;
    private Timer timer;


    //region constructor
    public MArena(Main main, String name) {
        this.name = name;
        listPlayers = new ArrayList<>();
        state = "CREATE";
        spawns = new ArrayList<>();
        spawnsGolds = new ArrayList<>();
        lobby = null;
        timerStatue = false;
        this.main = main;
    }

    public MArena(Main main, String name, String state, List<Location> spawns, List<Location> spawnsGolds, Location lobby, Location spectatorSpawn)
    {
        this.name = name;
        listPlayers = new ArrayList<>();
        this.state = state;
        this.spawns = spawns;
        this.spawnsGolds = spawnsGolds;
        this.lobby = lobby;
        this.spectatorSpawn = spectatorSpawn;
        timerStatue = false;
        this.main = main;
    }


    //endregion

    //region getters
    public String getName(){return name;}
    public String getState(){return state;}
    public List<Location> getSpawnsGolds() {return spawnsGolds;}
    public List<Location> getSpawns(){return spawns;}
    public Location getLobby() {return lobby;}
    public Location getSpectatorSpawn() {return spectatorSpawn;}
    public List<Player> getListPlayers(){return listPlayers;}
    public boolean getTimerStatue(){return timerStatue;}
    //endregion

    //region setters
    public void setName(String name) {this.name = name;}
    public void setState(String state){this.state = state;}
    public void setLobby(Location lobby) {this.lobby = lobby;}
    public void setSpectatorSpawn(Location spectatorSpawn) {this.spectatorSpawn = spectatorSpawn;}
    public void setTimerStatue(Boolean timer){this.timerStatue = timer;}
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
    public void removeSpawns(int spawn){spawns.remove(spawn);}

    public void addSpawnsGolds(Location spawnsGolds) {this.spawnsGolds.add(spawnsGolds);}
    public void removeSpawnsGolds(int spawnsGolds){this.spawnsGolds.remove(spawnsGolds);}

    public void timerStart()
    {
        timer  =  new Timer(this, main);

        timer.runTaskTimer((Plugin) main, 0, 20);
    }

    public void timerStop(){
        timer.cancel();
    }
    //endregion




}
