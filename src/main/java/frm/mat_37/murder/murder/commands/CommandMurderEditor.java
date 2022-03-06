package frm.mat_37.murder.murder.commands;

import frm.mat_37.murder.murder.main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMurderEditor implements CommandExecutor{
    private main main;
    public CommandMurderEditor(main main) {
    this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pls = ((Player) sender).getPlayer();

        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("murdereditor")) {
                if(args.length == 0) {
                    pls.sendMessage("Erreur: Liste des commands:");
                    pls.sendMessage("/murderEditor create <name>");
                    pls.sendMessage("/murderEditor remove <name>");

                }
                else if(args[1].equalsIgnoreCase("create")){
                    if(args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor create <name>");
                    else{
                        main.addArene(args[2]);
                    }
                }
                else if(args[1].equalsIgnoreCase("remove"))
                    if(args.length != 2)
                    pls.sendMessage("Erreur: La commande est: /murderEditor remove <name>");
                else{
                    main.removeArena(args[2]);
                }
            }

        }
        return false;
    }
}
