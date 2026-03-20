package io.github.anjoismysign.blobmenu.listener;

import io.github.anjoismysign.bloblib.entities.translatable.TranslatableItem;
import io.github.anjoismysign.bloblib.events.ProfileLoadEvent;
import io.github.anjoismysign.blobmenu.BlobMenu;
import io.github.anjoismysign.blobmenu.entity.PlayerHandler;
import io.github.anjoismysign.blobmenu.entity.PlayerMenu;
import io.github.anjoismysign.blobmenu.entity.PlayerMenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

public class MenuListener implements Listener {

    @Nullable
    private PlayerMenu menu(Player player){
        return BlobMenu.getInstance().getMenus().get(player);
    }

    @EventHandler
    public void onLoad(ProfileLoadEvent event){
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskLater(BlobMenu.getInstance(), ()->{
            if (!player.isConnected()){
                return;
            }
            new PlayerHandler(player).load();
        }, 20);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        @Nullable PlayerMenu menu = menu(player);
        if (menu == null){
            return;
        }
        TranslatableItem item = TranslatableItem.byItemStack(event.getCurrentItem());
        if (item == null) {
            return;
        }
        for (PlayerMenuItem menuItem : menu.menuItems()) {
            if (!item.identifier().equals(menuItem.translatableItem())) {
                continue;
            }
            event.setCancelled(true);
            event.setCursor(null);
            menuItem.commandData().apply(player);
            return;
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        @Nullable PlayerMenu menu = menu(player);
        if (menu == null){
            return;
        }
        TranslatableItem translatableItem = TranslatableItem.byItemStack(event.getOffHandItem());
        if (translatableItem == null) {
            return;
        }
        for (PlayerMenuItem menuItem : menu.menuItems()) {
            if (!translatableItem.identifier().equals(menuItem.translatableItem())) {
                continue;
            }
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (event.getAction() == Action.PHYSICAL) {
            return;
        }
        Player player = event.getPlayer();
        @Nullable PlayerMenu menu = menu(player);
        if (menu == null){
            return;
        }
        TranslatableItem translatableItem = TranslatableItem
                .byItemStack(event.getItem());
        if (translatableItem == null) {
            return;
        }
        for (PlayerMenuItem menuItem : menu.menuItems()) {
            if (!translatableItem.identifier().equals(menuItem.translatableItem())) {
                continue;
            }
            event.setCancelled(true);
            menuItem.commandData().apply(player);
            return;
        }
    }

    @EventHandler
    public void onLocaleChange(PlayerLocaleChangeEvent event) {
        Player player = event.getPlayer();
        @Nullable PlayerMenu menu = menu(player);
        if (menu == null){
            return;
        }
        for (PlayerMenuItem itemSlot : menu.menuItems()) {
            int slot = itemSlot.slot();
            String identifier = itemSlot.translatableItem();
            @Nullable TranslatableItem translatableItem = TranslatableItem.by(identifier);
            if (translatableItem == null){
                BlobMenu.getInstance().getLogger().severe("'"+identifier+"' TranslatableItem doesn't exist. Fix it in /plugins/BlobMenu/config.yml");
                continue;
            }
            player.getInventory().setItem(slot,
                    translatableItem.localize(player.getLocale()).getClone());
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        @Nullable PlayerMenu menu = menu(player);
        if (menu == null){
            return;
        }
        TranslatableItem item = TranslatableItem.byItemStack(event.getItemDrop().getItemStack());
        if (item == null) {
            return;
        }
        for (PlayerMenuItem menuItem : menu.menuItems()) {
            if (!item.identifier().equals(menuItem.translatableItem())) {
                continue;
            }
            event.setCancelled(true);
            return;
        }
    }
}
