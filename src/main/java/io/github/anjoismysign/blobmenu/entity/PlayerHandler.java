package io.github.anjoismysign.blobmenu.entity;

import io.github.anjoismysign.bloblib.entities.translatable.TranslatableItem;
import io.github.anjoismysign.blobmenu.BlobMenu;
import io.github.anjoismysign.blobmenu.configuration.MenuConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;

public record PlayerHandler(Player player) {

    public void load(){
        var menu = MenuConfiguration.getInstance().getMenu();
        menu.forEach(menuItem -> {
            String identifier = menuItem.getTranslatableItem();
            @Nullable TranslatableItem translatableItem = TranslatableItem.by(identifier);
            if (translatableItem == null){
                BlobMenu.getInstance().getLogger().severe("'"+identifier+"' TranslatableItem doesn't exist. In /plugins/BlobMenu/config.yml");
                return;
            }
        });
        PlayerMenu playerMenu = new PlayerMenu(new HashSet<>());
        menu.forEach(menuItem->{
            int slot = menuItem.getSlot();
            String identifier = menuItem.getTranslatableItem();
            @Nullable TranslatableItem translatableItem = TranslatableItem.by(identifier);
            if (translatableItem == null){
                return;
            }
            String permission = menuItem.getPermission();
            PermissionMode permissionMode = menuItem.getPermissionMode();
            PlayerInventory playerInventory = player.getInventory();
            if (permissionMode == PermissionMode.REQUIRES){
                if (!player.hasPermission(permission)){
                    @Nullable ItemStack itemStack = playerInventory.getItem(slot);
                    @Nullable TranslatableItem itemInSlot = TranslatableItem.byItemStack(itemStack);
                    if (itemInSlot == null || !itemInSlot.identifier().equals(identifier)){
                        return;
                    }
                    itemStack.setAmount(0);
                    return;
                }
            } else {
                if (player.hasPermission(permission)){
                    @Nullable ItemStack itemStack = playerInventory.getItem(slot);
                    @Nullable TranslatableItem itemInSlot = TranslatableItem.byItemStack(itemStack);
                    if (itemInSlot == null || !itemInSlot.identifier().equals(identifier)){
                        return;
                    }
                    itemStack.setAmount(0);
                    return;
                }
            }
            player.getInventory().setItem(slot,
                    translatableItem.localize(player.getLocale()).getClone());
            playerMenu.menuItems().add(new PlayerMenuItem(identifier, slot, menuItem.getCommand()));
        });
        BlobMenu.getInstance().add(player, playerMenu);
    }

}
