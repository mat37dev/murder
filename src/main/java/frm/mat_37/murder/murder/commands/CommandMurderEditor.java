package frm.mat_37.murder.murder.commands;

import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import frm.mat_37.murder.murder.gameManager.EditorManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMurderEditor implements CommandExecutor{
    private Main main;
    private EditorManager editorManager;
    public CommandMurderEditor(Main main, EditorManager editorManager) {
    this.main = main;
    this.editorManager = editorManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pls = ((Player) sender).getPlayer();

        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("murdereditor")) {
                if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    String message = "Murder: Liste des commands:\n";
                    message +="/murderEditor create <name>\n";
                    message +="/murderEditor remove <name>\n";
                    message +="/murderEditor setLobby <name>\n";
                    message +="/murderEditor setSpectatorSpawn <name>\n";
                    message +="/murderEditor addSpawn <name>\n";
                    message +="/murderEditor addSpawnGold <name>\n";
                    message +="/murderEditor removeSpawn <name>";
                    message +="/murderEditor removeSpawnGold <name>\n";
                    message +="/murderEditor save <name>\n";
                    message +="/murderEditor setEditor <name>\n";
                    message +="/murderEditor info <name>\n";
                    message +="/murderEditor list\n";
                    message +="/murderEditor setHub";
                    pls.sendMessage(message);
                    return true;
                }
                //region create
                else if(args[0].equalsIgnoreCase("create")){
                    if(args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor create <name>");
                    else if(arenaExists(args[1])){
                        pls.sendMessage("Une arène avec ce nom existe déjà");
                    }
                    else{
                        editorManager.createArene(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region remove
                else if(args[0].equalsIgnoreCase("remove")) {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor remove <name>");
                    else if(!arenaExists(args[1])){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else{
                        editorManager.removeArene(pls,args[1]);
                    }
                    return true;
                }
                //endregion
                //region setLobby
                else if(args[0].equalsIgnoreCase("setLobby")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor setLobby <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else{
                        editorManager.setLobby(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region setSpectatorSpawn
                else if(args[0].equalsIgnoreCase("setSpectatorSpawn")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor setSpectatorSpawn <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else{
                        editorManager.setSpectatorSpawn(pls,args[1]);
                    }
                    return true;
                }
                //endregion
                //region addSpawn
                else if(args[0].equalsIgnoreCase("addSpawn")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor addSpawn <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else{
                        editorManager.addSpawn(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region addSpawnGold
                else if(args[0].equalsIgnoreCase("addSpawnGold")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor addSpawnGold <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else{
                        editorManager.addSpawnGold(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region removeSpawn
                else if(args[0].equalsIgnoreCase("removeSpawn")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor removeSpawn <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else if(main.getArene(args[1]).getSpawns().size() == 0)
                        pls.sendMessage("Erreur: Vous n'avez pas encore ajouté de spawn");
                    else{
                        editorManager.removeSpawn(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region removeSpawnGold
                else if(args[0].equalsIgnoreCase("removeSpawnGold")){
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor removeSpawnGold <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Erreur: Vous devez passer votre arrène en mode editeur");
                    else if(main.getArene(args[1]).getSpawnsGolds().size() == 0)
                        pls.sendMessage("Erreur: Vous n'avez pas encore ajouté de spawn gold");
                    else{
                        editorManager.removeSpawnGold(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region save
                else if(args[0].equalsIgnoreCase("save")){
                    if(args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor save <name>");
                    else if(!arenaExists(args[1])){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Cette arrene est déjà save");
                    else if (main.getArene(args[1]).getSpawns().size()>=4 && main.getArene(args[1]).getLobby() != null&& main.getArene(args[1]).getSpectatorSpawn() != null){
                        editorManager.save(pls, args[1]);
                    }
                    else{
                        pls.sendMessage("Erreur: Vous n'avez pas remplie toutes les conditions pour enregistrer une arène");
                    }
                    return true;
                }
                //endregion
                //region setEditor
                else if(args[0].equalsIgnoreCase("setEditor")){
                    if(args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor setEditor <name>");
                    else if(!arenaExists(args[1])){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else if(main.getArene(args[1]).getState().equalsIgnoreCase("CREATE"))
                        pls.sendMessage("Cette arrene n'est pas encore save");
                    else{
                        editorManager.setEditor(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region info
                else if(args[0].equalsIgnoreCase("info"))
                {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor info <name>");
                    else if(!arenaExists(args[1]) ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else
                    {
                        editorManager.info(pls, args[1]);
                    }
                    return true;
                }
                //endregion
                //region list
                else if(args[0].equalsIgnoreCase("list")) {
                        if(args.length == 1) {
                            if(main.listArene.size() != 0) {
                                editorManager.list(pls);
                            }
                            else
                                pls.sendMessage("Erreur: Il n'y a pas d'arene !");
                        }
                        else
                            pls.sendMessage("Erreur: La commande est => /murderEditor list");
                }
                //endregion
                //region setHub
                else if(args[0].equalsIgnoreCase("setHub")) {
                    if(args.length == 1) {
                        editorManager.setHub(pls);
                    }
                    else{
                        pls.sendMessage("Erreur: La commande est => /murderEditor setHub");
                    }
                }
                //endregion
            }
        }
        return false;
    }
    //region methods
    private boolean arenaExists (String name){
        Boolean result = false;
        if(main.getArene(name) != null)
            result = true;
        return result;
    }
    //endregion
}
