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
                    pls.sendMessage("Erreur: Liste des commands:");
                    pls.sendMessage("/murderEditor join <name>");
                    pls.sendMessage("/murderEditor leave <name>");
                    return true;
                }
                else if (args[1].equalsIgnoreCase("join")) {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murder join <name>");
                    else {
                        //code pour join
                        main.getArene(args[2]).joinArene(pls);
                        main.joueurInArene.put(pls, main.getArene(args[2]));
                    }
                    return true;
                }
                else if (args[1].equalsIgnoreCase("leave")) {
                    if (args.length != 1)
                        pls.sendMessage("Erreur: La commande est: /murder leave");
                    else {
                        if (main.joueurInArene.get(pls) != null) {
                            main.joueurInArene.get(pls).leaveArene(pls);
                        } else {
                            pls.sendMessage("[Murder] Tu n'es pas dans une arrène");
                        }
                    }
                    return true;
                }
            }

        }
        return false;
    }
}
