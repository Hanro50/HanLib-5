package za.net.hanro50.util;
import org.bukkit.plugin.java.JavaPlugin;

import za.net.hanro50.util.log.Console;

/**
 * Hello world!
 *
 */
public class Spigot extends JavaPlugin
{
    @Override
    public void onEnable() {
        Console.out("Starting Hanlib!");
    }
    // Fired when plugin is disabled
    @Override
    public void onDisable() {

    }
}
