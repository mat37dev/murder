package frm.mat_37.murder.murder.commands;

import frm.mat_37.murder.murder.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMurder implements CommandExecutor {
    private Main main;
    private GameManager gameManager;

    public CommandMurder(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("murder")) {
                Player pls = ((Player) sender).getPlayer();

                if (args.length == 0||args[0].equalsIgnoreCase("help")) {
                    String message = "Murder: Liste des commands:\n";
                    if(pls.hasPermission("murder.admin"))
                        message+="/murderEditor\n";
                    message+="/murder join <name>\n";
                    message+="/murder leave\n";
                    pls.sendMessage(message);
                    return true;
                }
                //region join
                else if (args[0].equalsIgnoreCase("join")) {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murder join <name>");
                    else if(!arenaExists(args[1]))
                        pls.sendMessage("§f[§6Murder§f] l'arène §4"+args[1]+" §fn'existe pas !");
                    else if(main.joueurInArene.containsKey(pls)){
                        pls.sendMessage("Vous êtes déjà dans une arène.");
                    }
                    else if(!main.getArene(args[1]).getState().equalsIgnoreCase("WAITTING")){
                        pls.sendMessage("Cette arène n'est pas disponible");
                    }
                    else {
                        //on appel la fonction join du GameManager
                        gameManager.playerJoin(pls,main.getArene(args[1]));
                    }
                    return true;
                }
                //endregion
                //region leave
                else if (args[0].equalsIgnoreCase("leave")) {
                    if (args.length != 1)
                        pls.sendMessage("Erreur: La commande est: /murder leave");
                    else if(!main.joueurInArene.containsKey(pls))
                        pls.sendMessage("Erreur: vous n'etes pas dans une arene");
                    else if(main.joueurInArene.get(pls) != null) {
                        gameManager.pLayerLeave(pls,main.joueurInArene.get(pls));
                    }
                    else {
                        pls.sendMessage("[Murder] Tu n'es pas dans une arrène");
                    }
                    return true;
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
