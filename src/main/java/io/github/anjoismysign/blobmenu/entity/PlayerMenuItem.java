package io.github.anjoismysign.blobmenu.entity;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record PlayerMenuItem(String translatableItem,
                             int slot,
                             String command,
                             MenuItemCommandSender sender) {

    private static final CommandSender CONSOLE_SENDER = Bukkit.createCommandSender(component->{});

    public void apply(@NotNull Player player){
        if (sender == MenuItemCommandSender.PLAYER){
            player.performCommand(command);
        } else {
            Bukkit.dispatchCommand(CONSOLE_SENDER, command.replace("%player%", player.getName()));
        }
    }

}
