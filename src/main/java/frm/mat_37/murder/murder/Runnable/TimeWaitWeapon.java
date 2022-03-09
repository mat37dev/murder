package frm.mat_37.murder.murder.Runnable;

import frm.mat_37.murder.murder.MArena;
import frm.mat_37.murder.murder.gameManager.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimeWaitWeapon extends BukkitRunnable {
    MArena arene;
    GameManager gameManager;
    int timer;

    public TimeWaitWeapon(GameManager gameManager, MArena arene) {
        this.arene = arene;
        this.gameManager = gameManager;
        timer = 5;
    }

    @Override
    public void run() {
        if(timer == 20||timer == 15||timer == 10||timer == 5||timer == 4||timer == 3||timer == 2||timer == 1){
            for (Player player:arene.getListPlayers()) {
                player.sendMessage("§f[§eMurder§f]  §bLe Murder reçoit son arme dans: "+timer+ " s");
            }
        }
        else if(timer == 0){
            for (Player player:arene.getListPlayers()) {
                player.sendMessage("§f[§Murder§f]  §bLe murder vient de recevoir son arme");
            }
            gameManager.giveWeaponMurder(arene);
            cancel();
        }
        timer--;
    }
}
