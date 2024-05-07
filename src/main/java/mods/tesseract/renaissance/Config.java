package mods.tesseract.renaissance;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    public static boolean disableWoodPunch = false;

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        disableWoodPunch = configuration.getBoolean("disableWoodPunch", Configuration.CATEGORY_GENERAL, disableWoodPunch, "You cannot punch wood without tool.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
