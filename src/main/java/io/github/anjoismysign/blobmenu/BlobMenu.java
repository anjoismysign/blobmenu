package io.github.anjoismysign.blobmenu;

import io.github.anjoismysign.bloblib.managers.BlobPlugin;
import io.github.anjoismysign.blobmenu.director.MenuManagerDirector;
import io.github.anjoismysign.blobmenu.entity.PlayerMenu;
import io.github.anjoismysign.blobmenu.listener.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Map;
import java.util.WeakHashMap;

public final class BlobMenu extends BlobPlugin implements Listener{
    private static BlobMenu instance;

    public static BlobMenu getInstance(){
        return instance;
    }

    private final Map<Player, PlayerMenu> menus = new WeakHashMap<>();

    private MenuManagerDirector director;

    @Override
    public void onEnable() {
        instance = this;
        director = new MenuManagerDirector(this);
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

    public MenuManagerDirector getManagerDirector() {
        return director;
    }

    public Map<Player, PlayerMenu> getMenus() {
        return Map.copyOf(menus);
    }

    public void add(Player player, PlayerMenu menu){
        menus.put(player, menu);
    }

}
