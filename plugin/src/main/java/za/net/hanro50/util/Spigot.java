package za.net.hanro50.util;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import za.net.hanro50.util.log.Console;
import za.net.hanro50.util.log.Settings;

/**
 * Hello world!
 *
 */
public class Spigot extends JavaPlugin {
    final Settings settings = (Settings) new Settings(new File(this.getDataFolder(), "HanLib.prop"),Bukkit.getLogger());

    @Override
    public void onEnable() {
        try {
            settings.init().save();
        } catch (IOException e) {
            Console.crt(
                    "Hanlib has failed to load its own config file.\nIt cannot continue to operate due to this error!\nPlease check disk permissions before reporting this as a bug.");
                    this.onDisable();
            return;
        }
        Settings.Register("HanLib");
        Console.out("Hanlib has been loaded!");
    }

    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        Console.out("Hanlib has been unloaded :(");
    }
}
