package frm.mat_37.murder.murder.gameManager;
import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameManager {

    private int playerMin;
    private int playerMax;
    public ItemStack epeeMurder;
    public ItemStack arcDetective;
    public ItemStack gold;
    public ItemStack arc;


    private Main main;
    public GameManager(Main main) {
        this.main= main;
        playerMin = 3;
        playerMax = 12;
        epeeMurder = new ItemStack(Material.IRON_SWORD, 1);
        arcDetective = new ItemStack(Material.BOW, 1);
        gold = new ItemStack(Material.GOLD_INGOT, 1);
        arc = new ItemStack(Material.BOW, 1);
        creationArme();
    }

    private void creationArme(){
        ItemMeta customEpee = epeeMurder.getItemMeta();
        customEpee.setDisplayName("§l§eMurder");
        customEpee.setUnbreakable(true);
        customEpee.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        epeeMurder.setItemMeta(customEpee);
        ItemMeta customArcDetective = arcDetective.getItemMeta();
        customArcDetective.setDisplayName("§l§bDétective");
        customArcDetective.setUnbreakable(true);
        customArcDetective.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        customArcDetective.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        arcDetective.setItemMeta(customArcDetective);
        ItemMeta customGold = gold.getItemMeta();
        customGold.setDisplayName("§l§eGold");
        gold.setItemMeta(customGold);
        ItemMeta customArc = arc.getItemMeta();
        customArc.setDisplayName("§l§eArc");
        arc.setItemMeta(customArc);
    }



    public void playerJoin(Player player, MArena arene){

        if(arene.getListPlayers().size() < playerMax){
            player.getInventory().clear();
            player.setHealth(20);
            player.setFoodLevel(20);
            player.setLevel(0);
            //on ajoute le joueur a la liste de joueur de l'arene
            arene.joinArene(player);
            //on ajoute le joueur et l'arene au dico
            main.joueurInArene.put(player, arene);
            player.sendMessage("[Murder] Vous avez rejoint l'arène: "+ arene.getName());
            player.teleport(arene.getLobby());
            if(arene.getListPlayers().size() >= playerMin && !arene.getTimerStatue()){
                arene.setTimerStatue(true);
                arene.timerStart();
            }
        }
        else{
            player.sendMessage("Arène complète");
        }
    }
    public void pLayerLeave(Player player, MArena arene){
        if(arene.getState().equals("WATTING")) {
            //on supprime le joueur et on reset son niveau
            arene.leaveArene(player);
            player.teleport(main.hub);
            main.joueurInArene.remove(player);
            player.setLevel(0);
            player.sendMessage("[Murder] Vous avez quitté l'arène" + arene.getName());
            //on check si il y a toujours le nombres minimum de joueurs pour jouer
            if(arene.getTimerStatue()){
                if(arene.getListPlayers().size() < playerMin){
                    arene.timerStop();
                    for (Player pls:arene.getListPlayers()) {
                        pls.sendMessage("Partie annulé: pas assez de monde");
                        pls.setLevel(0);
                    }
                    arene.setTimerStatue(false);
                }
            }
        }
        else {
            arene.leaveArene(player);
            Location tempLocate = player.getLocation();
            player.teleport(main.hub);
            main.joueurInArene.remove(player);
            player.setLevel(0);
            player.setGameMode(GameMode.ADVENTURE);
            if(player == arene.getDetective()){
                arene.setDetective(null);
                for (Player pls:arene.getListPlayers()) {
                    pls.sendMessage("Le détective à quitté la partie, l'arc est à terre!");
                }
                detectiveDead(player, arene, tempLocate);
            }
            else if(player == arene.getMurder()){
                arene.setMurder(null);
                for (Player pls:arene.getListPlayers()) {
                    pls.sendMessage("Le murder à quitté la partie.");
                }
            }
            else if(arene.getInnocent().contains(player)){
                arene.getInnocent().remove(player);
                for (Player pls:arene.getListPlayers()) {
                    pls.sendMessage("Un innocent à quitté la partie.");
                }
            }
            else{
                arene.getSpectators().remove(player);
            }
            checkWin(arene);
        }
    }


    public void preparation(MArena arene){
        //variable temporaire
        List<Player> players = new ArrayList<>(arene.getListPlayers());
        Player temp;
        Random rnd = new Random();
        List<Location> spawnTemp = new ArrayList<>(arene.getSpawns());
        // on définie le role murder
        temp = players.get(rnd.nextInt(players.size()));
        arene.setMurder(temp);
        players.remove(temp);
        // on définie le role détective
        temp = players.get(rnd.nextInt(players.size()));

        arene.setDetective(temp);
        players.remove(temp);
        arene.setInnocent(players);

        //on téléporte tout le monde
        for (Player pls:arene.getListPlayers()) {
            if(spawnTemp.size() == 0)
                spawnTemp.addAll(arene.getSpawns());
            int nb = rnd.nextInt(arene.getSpawns().size());
            pls.teleport(spawnTemp.get(nb));
            spawnTemp.remove(nb);
        }
        //on lance un conte-a-rebous avant de donner les armes
        giveWeaponDetective(arene);
        arene.timerWaitingWeapon(this);
    }

    public void giveWeaponDetective(MArena arene){
        arene.getDetective().getInventory().setItem(1, arcDetective);
        arene.getDetective().getInventory().setItem(15, new ItemStack(Material.ARROW));
        arene.getDetective().updateInventory();
    }
    public void giveWeaponMurder(MArena arene){
        arene.getMurder().getInventory().setItem(1, epeeMurder);
        arene.getMurder().updateInventory();
    }

    public void murderKill(Player player, MArena arene, Player victime){
        if(victime == arene.getDetective()){
            for (Player pls:arene.getInnocent()) {
                pls.sendMessage("[Murder] Le détective est mort!\nL'arc est à terre");
            }
            for (Player pls:arene.getSpectators()) {
                pls.sendMessage("[Murder] Le détective est mort!");
            }
            victime.sendMessage("[Murder] Vous êtes mort!");
            arene.setDetective(null);
            arene.addSpectators(player);
            detectiveDead(victime, arene, player.getLocation());
        }
        else{
            arene.removeInnocent(victime);
            for (Player pls:arene.getInnocent()) {
                pls.sendMessage("[Murder] Un innocent a été tué!");
            }
            for (Player pls:arene.getSpectators()) {
                pls.sendMessage("[Murder] Un innocent a été tué!");
            }
            arene.getDetective().sendMessage("[Murder] Un innocent a été tué!");
            player.sendMessage("[Murder] Vous avez tuer un innocent");
            arene.addSpectators(victime);
            victime.getInventory().clear();
            victime.updateInventory();
        }
        victime.teleport(arene.getSpectatorSpawn());
        victime.setGameMode(GameMode.SPECTATOR);
        checkWin(arene);
    }
    public void detectiveKill(Player player, MArena arene, Player victime){
        //Verifi si celui qui est tuer et le murder
        if(victime == arene.getMurder()) {
            for(Player pls : arene.getInnocent()) {
                pls.sendMessage("[Murder] "+player.getName()+" a tué: "
                        + victime.getName() + " qui était le murder");
            }
            for(Player pls : arene.getSpectators()) {
                pls.sendMessage("[Murder] "+player.getName()+" a tué: "
                        + victime.getName() + " qui était le murder");
            }
            player.sendMessage("[Murder] Vous avez tué le murder!");
            victime.sendMessage("[Murder] Vous êtes mort!");
            victime.setGameMode(GameMode.SPECTATOR);
            arene.setMurder(null);
            arene.addSpectators(victime);
            victime.teleport(arene.getSpectatorSpawn());
            victime.setGameMode(GameMode.SPECTATOR);
        }
        else{
            //le détective meurt et l'innocent aussi
            for (Player pls:arene.getInnocent()) {
                pls.sendMessage("[Murder] Un innocent a été tué!");
                pls.sendMessage("[Murder] Le détective a été tué!\nL'arc est à terre");
            }
            for (Player pls:arene.getSpectators()) {
                pls.sendMessage("[Murder] Un innocent a été tué!");
                pls.sendMessage("[Murder] Le détective a été tué!\nL'arc est à terre");
            }
            player.sendMessage("Vous avez tué un innocent!\nVous êtes mort!");
            victime.sendMessage("Vous êtes mort!");

            arene.setDetective(null);
            arene.removeInnocent(victime);
            arene.addSpectators(victime);
            arene.addSpectators(player);
            detectiveDead(player, arene, player.getLocation());
            player.teleport(arene.getSpectatorSpawn());
            victime.teleport(arene.getSpectatorSpawn());
            victime.getInventory().clear();
            victime.updateInventory();
            victime.setGameMode(GameMode.SPECTATOR);
            player.setGameMode(GameMode.SPECTATOR);
        }
        checkWin(arene);
    }

    public void killedByBow(Player player, MArena arene, Player victime){

    }
    private void detectiveDead(Player player, MArena arene, Location location){
        player.getInventory().clear();
        player.updateInventory();
        arene.arcATerre(location);
    }

    public void checkWin(MArena arena){
        if(arena.getMurder() == null){
            for(Player plsG : arena.getListPlayers()) {
                arena.getMurder().sendMessage("zfjhdbf");
                plsG.sendMessage("[Murder] Les innocents gagnent");
                plsG.getInventory().clear();
                plsG.updateInventory();
                plsG.setLevel(0);
                plsG.setHealth(20);
                plsG.setFoodLevel(20);
                plsG.teleport(arena.getLobby());
                plsG.setGameMode(GameMode.ADVENTURE);
                main.joueurInArene.remove(plsG);
            }
            arena.reset();
        }
        else if(arena.getInnocent().size() == 0 && arena.getDetective() == null) {
                for(Player plsG : arena.getListPlayers()) {
                    plsG.sendMessage("[Murder] Le murder à gagné!");
                    plsG.getInventory().clear();
                    plsG.setLevel(0);
                    plsG.setHealth(20);
                    plsG.setFoodLevel(20);
                    plsG.teleport(arena.getLobby());
                    plsG.setGameMode(GameMode.ADVENTURE);
                    main.joueurInArene.remove(plsG);
                }
            arena.reset();
        }
    }
    public void newDetective(Player player, MArena arene){
        arene.setDetective(player);
        arene.removeInnocent(player);
        if(!player.getInventory().contains(arcDetective))
            arene.getDetective().getInventory().setItem(1, arcDetective);
        if(!player.getInventory().contains(Material.ARROW))
            player.getInventory().addItem(new ItemStack(Material.ARROW));
        else
            player.getInventory().setItem(15, new ItemStack(Material.ARROW));
        for (Player pls:arene.getInnocent()) {
            pls.sendMessage("[Murder] Un joueur a récupéré l'arc!");
        }
        for (Player pls:arene.getSpectators()) {
            pls.sendMessage("[Murder] Un joueur a récupéré l'arc!");
        }
        arene.getMurder().sendMessage("[Murder] Un joueur a récupéré l'arc!");

    }

    public void getGold(Player player){

    }
}
