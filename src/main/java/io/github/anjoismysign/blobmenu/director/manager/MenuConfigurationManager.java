package io.github.anjoismysign.blobmenu.director.manager;

import io.github.anjoismysign.blobmenu.BlobMenu;
import io.github.anjoismysign.blobmenu.configuration.MenuConfiguration;
import io.github.anjoismysign.blobmenu.director.MenuManager;
import io.github.anjoismysign.blobmenu.director.MenuManagerDirector;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuConfigurationManager extends MenuManager {
    private MenuConfiguration configuration;

    public MenuConfigurationManager(MenuManagerDirector managerDirector) {
        super(managerDirector);
        reload();
    }

    @Override
    public void reload() {
        BlobMenu plugin = getPlugin();
        plugin.saveResource("config.yml", false);
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        Constructor constructor = new Constructor(MenuConfiguration.class, new LoaderOptions());
        Yaml yaml = new Yaml(constructor);
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            configuration = yaml.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @NotNull
    public MenuConfiguration getConfiguration() {
        return configuration;
    }
}