package frm.mat_37.murder.murder.gameManager;
import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EditorManager {
    Main main;

    public EditorManager(Main main) {
        this.main= main;
    }

    //region createArene
    public void createArene(Player player, String arene){
        main.addArene(arene);
        player.sendMessage(arene+ " a été créé avec succés");
        main.areneConfig.createSection("arenes."+arene);
        main.areneConfig.set("arenes."+arene+".statue", "CREATE");
        main.areneConfig.set("arenes."+arene+".lobby","");
        main.areneConfig.set("arenes."+arene+".spectatorSpawn","");
        main.areneConfig.createSection("arenes."+arene+".spawns");
        main.areneConfig.createSection("arenes."+arene+".spawnsGold");
        main.saveArena();
    }
    //endregion
    //region removeArene
    public void removeArene(Player player,String arene){
        main.removeArena(arene);
        player.sendMessage(arene + " a été supprimé avec succés");
        main.areneConfig.set("arenes."+arene, null);
        main.saveArena();
    }
    // endregion
    //region setLobby
    public void setLobby(Player player, String arene){
        main.getArene(arene).setLobby(player.getLocation());
        main.areneConfig.set("arenes."+arene+".lobby", player.getWorld().getName()+","+player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        player.sendMessage("Le lobby à bien été set");
        main.saveArena();
    }
    //endregion
    //region setSpectatorSpawn
    public void setSpectatorSpawn(Player player, String arene){
        main.getArene(arene).setSpectatorSpawn(player.getLocation());
        main.areneConfig.set("arenes."+arene+".spectatorSpawn", player.getWorld().getName()+","+player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        player.sendMessage("Le spawn des Spectateurs à bien été set");
        main.saveArena();
    }
    //endregion
    //region addSpawn
    public void addSpawn(Player player, String arene){
        MArena mArena = main.getArene(arene);
        int nbSpawns = mArena.getSpawns().size();
        if(nbSpawns == 12)
            player.sendMessage("Nombre de spawn max atteind");
        else{
            mArena.addSpawns(player.getLocation());
            main.areneConfig.set("arenes."+arene+".spawns."+(nbSpawns+1), player.getWorld().getName()+","+player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
            player.sendMessage("Un spawn a été ajouté");
            main.saveArena();
        }
    }
    //endregion
    //region addSpawnGold
    public void addSpawnGold(Player player, String arene){
        MArena mArena = main.getArene(arene);
        int nbSpawnsGolds = mArena.getSpawnsGolds().size();
        if(nbSpawnsGolds == 24)
            player.sendMessage("Nombre de Spawn Gold max atteind");
        else{
            mArena.addSpawnsGolds(player.getLocation());
            main.areneConfig.set("arenes."+arene+".spawnsGold."+(nbSpawnsGolds+1), player.getWorld().getName()+","+player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
            player.sendMessage("Un Spawn Gold a été ajouté");
            main.saveArena();
        }
    }
    //endregion
    //region removeSpawn
    public void removeSpawn(Player player, String arene){
        MArena mArena = main.getArene(arene);
        int nbSpawns = mArena.getSpawns().size();
        mArena.removeSpawns(nbSpawns-1);
        main.areneConfig.set("arenes."+arene+".spawns."+(nbSpawns), null);
        main.saveArena();
        player.sendMessage("Le dernier spawn a été retiré");
    }
    //endregion
    //region removeSpawnGold
    public void removeSpawnGold(Player player, String arene){
        MArena mArena = main.getArene(arene);
        int nbSpawnsGold = mArena.getSpawns().size();
        mArena.removeSpawnsGolds(nbSpawnsGold-1);
        main.areneConfig.set("arenes."+arene+".spawnsGold."+(nbSpawnsGold), null);
        main.saveArena();
        player.sendMessage("Le dernier Spawn Gold a été retiré");
    }
    //endregion
    //region save
    public void save(Player player, String arene){
        main.getArene(arene).setState("WAITTING");
        main.areneConfig.set("arenes."+arene+".statue", "WAITTING");
        main.saveArena();
        player.sendMessage("Arène enregistré");
    }
    //endregion
    //region seteditor
    public void setEditor(Player player, String arene){
        main.getArene(arene).setState("CREATE");
        main.areneConfig.set("arenes."+arene+".statue", "CREATE");
        main.saveArena();
        player.sendMessage("Arène passé en mode editeur");
    }
    //endregion
    //region info
    public void info(Player player, String arene){
        MArena mArena = main.getArene(arene);
        String message = mArena.getName() + "Information:\n";
        if(mArena.getLobby() == null)
            message += "Lobby §40/1\n";
        else
            message += "Lobby §a1/1\n";
        if(mArena.getSpectatorSpawn() == null)
            message += "§rSpectator Spawn §40/1\n";
        else
            message += "§rSpectator Spawn §a1/1\n";
        if(mArena.getSpawns().size() < 4)
            message+="§rSpawns: §4"+mArena.getSpawns().size()+"/12\n";
        else
            message+="§rSpawns: §a"+mArena.getSpawns().size()+"/12\n";
        message+="§rSpawns Gold: §a"+mArena.getSpawnsGolds().size()+"\n";
        if(mArena.getState().equalsIgnoreCase("CREATE"))
            message+="§rStatue: §4Non Finie";
        else
            message+="§rStatue: §aFinie";
        player.sendMessage(message);
    }
    //endregion
    //region list
    public void list(Player player){
        String message = "Liste des arènes: \n";
        for (MArena arena:main.listArene) {
            message += arena.getName()+"\n";
        }
        player.sendMessage(message);
    }
    //endregion
    //region setHub
    public void setHub(Player player){
        main.hub = player.getLocation();
        main.areneConfig.set("hub", player.getWorld().getName()+","+player.getLocation().getX()+","+player.getLocation().getY()+","+player.getLocation().getZ());
        player.sendMessage("Vous venez de définir le hub");
        main.saveArena();
    }
    //endregion
}
