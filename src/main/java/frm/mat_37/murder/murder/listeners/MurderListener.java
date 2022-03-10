package frm.mat_37.murder.murder.listeners;

import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import frm.mat_37.murder.murder.gameManager.GameManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

public class MurderListener implements Listener {
    private Main main;
    private GameManager gameManager;
    public MurderListener(Main main, GameManager gameManager) {
        this.main = main;
        this.gameManager = gameManager;
    }
    //Changement de bouffe des joueurs en jeu
    @EventHandler
    public void OnchangelevelFood(FoodLevelChangeEvent e) {
        Player pls = (Player) e.getEntity();
        if(main.joueurInArene.containsKey(pls)) {
           e.setCancelled(true);
        }
    }
    //on traite els damages
    @EventHandler
    public void cancelCoup(EntityDamageByEntityEvent event){
        Player victime;
        Player killer;
        Entity damager;
        if(event.getEntity() instanceof Player) {
            victime = (Player)event.getEntity();
            damager = event.getDamager();
            if(damager instanceof Player) {
                killer = (Player) damager;
                if(main.joueurInArene.containsKey(killer)){
                    if(killer.getInventory().getItemInMainHand().isSimilar(gameManager.epeeMurder)){
                        gameManager.murderKill(killer, main.joueurInArene.get(killer),victime);
                    }
                    event.setCancelled(true);
                }
            }
            else if(damager instanceof Arrow){
                Arrow arrow = (Arrow)damager;
                if(arrow.getShooter() instanceof Player) {
                    killer = (Player) arrow.getShooter();
                    if(main.joueurInArene.containsKey(killer)){
                        if(killer.getInventory().getItemInMainHand().isSimilar(gameManager.arcDetective)){
                            gameManager.detectiveKill(killer,main.joueurInArene.get(killer),victime);
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void damabeByBlock(EntityDamageByBlockEvent event) {
        if(event.getEntity() instanceof Player){
            Player pls = (Player) event.getEntity();
            if(main.joueurInArene.containsKey(pls)) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player pls = (Player) event.getPlayer();
        if(main.joueurInArene.containsKey(pls)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player pls = (Player) event.getPlayer();
        if(main.joueurInArene.containsKey(pls)) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player pls = (Player) event.getPlayer();
        if(main.joueurInArene.containsKey(pls)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGetBow(PlayerPickupArrowEvent event){
        if(main.joueurInArene.containsKey(event.getPlayer())) {
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void OngetItem(EntityPickupItemEvent e) {

        if(e.getEntity() instanceof Player) {
            Player pls = (Player) e.getEntity();
            if(main.joueurInArene.containsKey(pls)) {
                MArena arene = main.joueurInArene.get(pls);
                if(e.getItem().getItemStack().isSimilar(gameManager.arcDetective)){
                    if(pls == arene.getMurder())
                        e.setCancelled(true);
                    else{
                        e.getItem().remove();
                        gameManager.newDetective(pls, arene);
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
