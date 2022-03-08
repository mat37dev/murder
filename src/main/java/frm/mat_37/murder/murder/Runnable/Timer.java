package frm.mat_37.murder.murder.Runnable;

import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer extends BukkitRunnable {
    MArena arene;
    Main main;
    int timer;
    public Timer(MArena arene, Main main) {
        this.arene = arene;
        this.main = main;
        timer = 20;
    }

    @Override
    public void run() {
        for (Player player:arene.getListPlayers()) {
            player.setLevel(timer);
        }
        if(timer == 20||timer == 15||timer == 10||timer == 5||timer == 4||timer == 3||timer == 2||timer == 1){
            for (Player player:arene.getListPlayers()) {
                player.sendMessage("§f[§eMurder§f]  §bLa partie commence dans: "+timer);
                player.playSound(player.getLocation(), Sound.BLOCK_LADDER_HIT, 20f, 12);
            }
        }
        else if(timer == 0){
            for (Player player:arene.getListPlayers()) {
                player.sendMessage("§f[§Murder§f]  §bLa partie commence dans: "+timer);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 20f, 12);
            }
            arene.setState("PLAYING");
            main.areneConfig.set("arenes."+arene+".statue", "WAITTING");
            arene.setTimerStatue(false);
            main.saveArena();
            cancel();
        }
        timer--;
    }
}
