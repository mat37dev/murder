package frm.mat_37.murder.murder.gameManager;
import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Random;


public class GameManager {

    private int playerMin;
    private int playerMax;
    private ItemStack epeeMurder;
    private ItemStack arcDetective;


    private Main main;
    public GameManager(Main main) {
        this.main= main;
        playerMin = 1;
        playerMax = 1;
        epeeMurder = new ItemStack(Material.IRON_SWORD, 1);
        arcDetective = new ItemStack(Material.BOW, 1);
        creationArme();
    }

    private void creationArme(){
        ItemMeta customEpee = epeeMurder.getItemMeta();
        customEpee.setDisplayName("§l§eMurder");
        customEpee.setUnbreakable(true);
        customEpee.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        epeeMurder.setItemMeta(customEpee);
        ItemMeta customArc = arcDetective.getItemMeta();
        customArc.setDisplayName("§l§bDétective");
        customArc.setUnbreakable(true);
        customArc.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        customArc.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        arcDetective.setItemMeta(customArc);
    }



    public void playerJoin(Player player, MArena arene){

        if(arene.getListPlayers().size() < playerMax){
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
        //on supprime le joueur et on reset son niveau
        arene.leaveArene(player);
        player.teleport(main.hub);
        main.joueurInArene.remove(player);
        player.setLevel(0);
        player.sendMessage("[Murder] Vous avez quitté l'arène" + arene.getName());
        //on check si il y a toujours le nombres minimum de joueurs pour jouer
        if(arene.getListPlayers().size() < playerMin){
            arene.timerStop();
            for (Player pls:arene.getListPlayers()) {
                pls.sendMessage("Partie annulé: pas assez de monde");
            }
            arene.setTimerStatue(false);
        }
    }

    public void preparation(MArena arene){
        //variable temporaire
        List<Player> players = arene.getListPlayers();
        Player temp;
        Random rnd = new Random();
        List<Location> spawnTemp = arene.getSpawns();
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
                spawnTemp = arene.getSpawns();
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


}
