package com.github.floofcat.walliapi.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class FileManager {

    private File file;
    private String fileName;
    private JavaPlugin javaPlugin;

    public FileManager(String fileName, JavaPlugin javaPlugin) {
        this.fileName = fileName;
        this.javaPlugin = javaPlugin;
    }

    public void createFile() throws IOException {
        if(!javaPlugin.getDataFolder().exists()) {
            javaPlugin.getDataFolder().mkdir();
        }

        this.file = new File(javaPlugin.getDataFolder(), fileName);
        if(!file.exists()) {
            file.createNewFile();
        }
    }


    public File getFile() throws IOException {
        this.file = new File(javaPlugin.getDataFolder(), fileName);
        if(!this.file.exists()) {
            this.createFile();
        }

        return this.file;
    }

    public @NotNull YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(this.file);
    }

    public void save(YamlConfiguration config) throws IOException {
        config.save(this.file);
    }
}
