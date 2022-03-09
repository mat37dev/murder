package frm.mat_37.murder.murder;
import frm.mat_37.murder.murder.commands.CommandMurder;
import frm.mat_37.murder.murder.commands.CommandMurderEditor;
import frm.mat_37.murder.murder.gameManager.EditorManager;
import frm.mat_37.murder.murder.gameManager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin {
    //varible qui stocke les arenes
    public List<MArena> listArene;
    //dico qui permet de retrouver une arene en fonction du joueur
    public Map<Player, MArena> joueurInArene;

    //hub
    public Location hub;
    
    //variable arene.yml
    private File areneFile;
    public YamlConfiguration areneConfig;

    private GameManager gameManager;
    private EditorManager editorManager;

    @Override
    public void onEnable() {
        gameManager = new GameManager(this);
        editorManager = new EditorManager(this);
        //PluginManager pm = getServer().getPluginManager();
        //pm.registerEvents(gameManager,this);

        getCommand("murderEditor").setExecutor((CommandExecutor) new CommandMurderEditor(this, editorManager));


        getCommand("murder").setExecutor((CommandExecutor) new CommandMurder(this, gameManager));

        checkDataArena();

        //on récupère la liste d'arrene
        listArene = new ArrayList<>();
        loadArena();
        //initialisation
        joueurInArene = new HashMap<>();
    }
    //region arene.yml
    //vérification du dossier et du fichier lros du lancement du serveur
    public void checkDataArena() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        areneFile = new File(getDataFolder() + File.separator + "arenes.yml");
        if (!areneFile.exists()) {
            try {
                areneFile.createNewFile();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            areneConfig = YamlConfiguration.loadConfiguration(areneFile);
            areneConfig.createSection("arenes");
            areneConfig.set("hub","");
            saveArena();
        } else {
            areneConfig = YamlConfiguration.loadConfiguration(areneFile);
        }
    }

    public void loadArena()
    {
        Location hubTemp = convertLocation(areneConfig.getString("hub"));
        if(hubTemp == null)
            hubTemp = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        hub = hubTemp;
        saveArena();
        for (String name : areneConfig.getConfigurationSection("arenes.").getKeys(false))
        {
            Location lobbyTemp = convertLocation(areneConfig.getString("arenes."+name+".lobby"));
            Location spectatorSpawnTemp = convertLocation(areneConfig.getString("arenes."+name+".lobby"));

            String stateTemp = (String) areneConfig.get("arenes."+name+".statue");
            List<Location> spawnsTemp = new ArrayList<>();
            List<Location> spawnsGoldsTemp = new ArrayList<>();
            if(areneConfig.getConfigurationSection("arenes."+name+".spawns") != null){
                for (String spawn : areneConfig.getConfigurationSection("arenes."+name+".spawns").getKeys(false))
                {
                    spawnsTemp.add(convertLocation((String) areneConfig.get("arenes."+name+".spawns."+spawn)));

                }
            }
            if(areneConfig.getConfigurationSection("arenes."+name+".spawnsGold") != null){
                for (String spawnGold : areneConfig.getConfigurationSection("arenes."+name+".spawnsGold").getKeys(false))
                {
                    spawnsGoldsTemp.add(convertLocation((String) areneConfig.get("arenes."+name+".spawnsGold."+spawnGold)));
                }
            }
            MArena temp = new MArena(this,name,stateTemp,spawnsTemp,spawnsGoldsTemp,lobbyTemp,spectatorSpawnTemp);
            listArene.add(temp);
        }
    }

    //sauvegarde de fichier yml
    public void saveArena()
    {
        try {
            areneConfig.save(areneFile);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //permet de convertir la location écris dans le fichier yml
    private Location convertLocation(String locationString)
    {
        Location result = null;

        if(!locationString.equalsIgnoreCase("")){
            String[] parsedLoc = locationString.split(",");
            String world = parsedLoc[0];
            double x = Double.valueOf(parsedLoc[1]);
            double y = Double.valueOf(parsedLoc[2]);
            double z = Double.valueOf(parsedLoc[3]);
            result = new Location(Bukkit.getWorld(world),x,y,z);
        }
        return result;
    }
    //endregion
    //region Arene
    public void addArene(String name){
        MArena temp = new MArena(this,name);
        listArene.add(temp);
    }

    public void removeArena(String name){
        listArene.remove(getArene(name));
    }

    public MArena getArene(String name) {
        MArena temp = null;
        for (MArena ar : listArene) {
            if (ar.getName().equalsIgnoreCase(name)) {
                temp = ar;
            }
        }
        return temp;
    }
    //endregion
    //region getter GameManager
    public GameManager getGameManager(){
        return gameManager;
    }
    //endregion
}
