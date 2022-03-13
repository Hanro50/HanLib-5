package za.net.hanro50.util;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.api.plugin.Plugin;
import za.net.hanro50.util.log.Console;
import za.net.hanro50.util.log.Settings;

public class Bungee extends Plugin {
    final Settings settings = (Settings) new Settings(new File(this.getDataFolder(), "HanLib.prop"),
            getLogger().getParent());
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

    @Override
    public void onDisable() {
        Console.out("Hanlib has been unloaded :(");
    }
}
