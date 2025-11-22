package net.pop.wagoo2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WagooConfig {

    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("wagoo2.json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static ConfigData data = new ConfigData();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                data = GSON.fromJson(reader, ConfigData.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTargetIp() { return data.targetServerIP; }
    public static String getCommand1() { return data.command1; }
    public static String getCommand2() { return data.command2; }

    public static class ConfigData {
        public String targetServerIP = "172.93.110.127:25561";
        public String command1 = "pskill";
        public String command2 = "sskill";
    }
}