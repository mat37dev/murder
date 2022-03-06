package frm.mat_37.murder.murder.commands;

import frm.mat_37.murder.murder.MArena;
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
                    return true;
                }
                //region create
                else if(args[0].equalsIgnoreCase("create")){
                    if(args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor create <name>");
                    else if(main.getArene(args[1]) != null){
                        pls.sendMessage("Une arène avec ce nom existe déjà");
                    }
                    else{
                        main.addArene(args[1]);
                        pls.sendMessage(args[1]+ " a été créé avec succés");
                        main.areneConfig.createSection("arenes."+args[1]);
                        main.areneConfig.set("arenes."+args[1]+".statue", "CREATE");
                        main.areneConfig.createSection("arenes."+args[1]+".lobby");
                        main.areneConfig.createSection("arenes."+args[1]+".spawns");
                        main.areneConfig.createSection("arenes."+args[1]+".spawnsGold");
                        main.saveArena();
                    }
                    return true;
                }
                //endregion
                //region remove
                else if(args[0].equalsIgnoreCase("remove")) {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor remove <name>");
                    else if(main.getArene(args[1]) == null ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else{
                        main.removeArena(args[1]);
                        pls.sendMessage(args[1] + " a été supprimé avec succés");
                        main.areneConfig.set("arenes."+args[1], null);
                        main.saveArena();
                    }
                    return true;
                }
                //endregion
                else if(args[0].equalsIgnoreCase("info"))
                {
                    if (args.length != 2)
                        pls.sendMessage("Erreur: La commande est: /murderEditor info <name>");
                    else if(main.getArene(args[1]) == null ){
                        pls.sendMessage("Aucune arène ne porte ce nom");
                    }
                    else
                    {
                        MArena temp = main.getArene(args[1]);
                        String message = temp.getName() + "Information:\n";
                        if(temp.getLobby() == null)
                            message += "Lobby §40/1\n";
                        else
                            message += "Lobby §a0/1\n";
                        if(temp.getSpawns().size() < 4)
                            message+="§rSpawns: §4"+temp.getSpawns().size()+"/12\n";
                        else
                            message+="§rSpawns: §a"+temp.getSpawns().size()+"/12\n";
                        if(temp.getSpawnsGolds().size() < 4)
                            message+="§rSpawns Gold: §4"+temp.getSpawnsGolds().size()+"/12\n";
                        else
                            message+="§rSpawns Gold: §a"+temp.getSpawnsGolds().size()+"/12\n";
                        if(temp.getState().equalsIgnoreCase("CREATE"))
                            message+="§rStatue: §4Non Finie";
                        else
                            message+="§rStatue: §aFinie";
                        pls.sendMessage(message);
                    }
                    return true;
                }
                else if(args[0].equalsIgnoreCase("list")) {
                        if(args.length == 1) {
                            if(main.listArene.size() != 0) {
                                String message = "Liste des arènes: \n";
                                List<MArena> arenas = new ArrayList<>();
                                for (int x =0; x < main.listArene.size(); x++){
                                    message+= main.listArene.get(x).getName()+"\n";
                                }
                                pls.sendMessage(message);

                            }else{
                                pls.sendMessage("Erreur: Il n'y a pas d'arene !");
                            }
                        }else{
                            pls.sendMessage("Erreur: La commande est => /murderEditor list");
                        }
                }
            }
        }
        return false;
    }
}
