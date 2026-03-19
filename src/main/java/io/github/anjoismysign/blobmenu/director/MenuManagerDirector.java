package io.github.anjoismysign.blobmenu.director;

import io.github.anjoismysign.bloblib.entities.GenericManagerDirector;
import io.github.anjoismysign.blobmenu.BlobMenu;
import io.github.anjoismysign.blobmenu.director.manager.MenuConfigurationManager;
import io.github.anjoismysign.blobmenu.entity.PlayerHandler;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class MenuManagerDirector extends GenericManagerDirector<BlobMenu> {
    public MenuManagerDirector(BlobMenu plugin) {
        super(plugin);
        addManager("ConfigurationManager",
                new MenuConfigurationManager(this));
    }

    /**
     * From top to bottom, follow the order.
     */
    @Override
    public void reload() {
        getConfigurationManager().reload();
        Bukkit.getOnlinePlayers().forEach(player -> {
            new PlayerHandler(player).load();
        });
    }

    @Override
    public void unload() {
    }

    @NotNull
    public final MenuConfigurationManager getConfigurationManager() {
        return getManager("ConfigurationManager", MenuConfigurationManager.class);
    }
}