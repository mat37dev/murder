package frm.mat_37.murder.murder;

import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    private StateM state;
    public List<MArena> listArene;
    public Map<Player, MArena> joueurInArene;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvent(new );

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
