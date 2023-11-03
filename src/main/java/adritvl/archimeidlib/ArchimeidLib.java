package adritvl.archimeidlib;

import org.bukkit.plugin.java.JavaPlugin;

public final class ArchimeidLib extends JavaPlugin {
    public static ArchimeidLib plugin;
    @Override
    public void onEnable() {
        plugin = this;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
