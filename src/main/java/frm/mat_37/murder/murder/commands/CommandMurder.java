package frm.mat_37.murder.murder.commands;

import frm.mat_37.murder.murder.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMurder implements CommandExecutor {
    private main main;

    public CommandMurder(main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (command.getName().equalsIgnoreCase("murder")) {
                Player pls = ((Player) sender).getPlayer();

                if (args.length == 0) {
                    String message = "Murder: Liste des commands:\n";
                    if(pls.hasPermission("murder.admin"))
                        message+="/murderEditor\n";
                    message+="/murder join <name>\n";
                    message+="/murder leave <name>\n";
                    pls.sendMessage(message);
                    return true;
                }
                //region join
                else if (args[0].equalsIgnoreCase("join")) {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murder join <name>");
                    else if(!main.joueurInArene.containsKey(pls)){
                        pls.sendMessage("Vous êtes déjà dans une arène.");
                    }
                    else if(main.listArene.contains(main.getArene(args[1]))){
                        //code pour join
                        main.getArene(args[1]).joinArene(pls);
                        main.joueurInArene.put(pls, main.getArene(args[1]));
                        pls.sendMessage("[Murder] Vous avez rejoint l'arène: "+ args[1]);
                        }
                    else{
                        pls.sendMessage("§f[§6Murder§f] l'arène §4"+args[1]+" §fn'existe pas !");
                    }
                    return true;
                }
                //endregion
                //region leave
                else if (args[0].equalsIgnoreCase("leave")) {
                    if (args.length != 1)
                        pls.sendMessage("Erreur: La commande est: /murder leave");
                    else {
                        if (main.joueurInArene.get(pls) != null) {
                            pls.sendMessage("[Murder] Vous avez quitté l'arène" + main.joueurInArene.get(pls).getName());
                            main.joueurInArene.get(pls).leaveArene(pls);
                            main.joueurInArene.remove(pls);
                        } else {
                            pls.sendMessage("[Murder] Tu n'es pas dans une arrène");
                        }
                    }
                    return true;
                }
                //endregion

            }

        }
        return false;
    }
}
