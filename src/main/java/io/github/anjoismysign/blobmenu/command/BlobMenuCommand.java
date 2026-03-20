package io.github.anjoismysign.blobmenu.command;

import io.github.anjoismysign.bloblib.api.BlobLibMessageAPI;
import io.github.anjoismysign.blobmenu.entity.PlayerHandler;
import io.github.anjoismysign.skeramidcommands.command.Command;
import io.github.anjoismysign.skeramidcommands.command.CommandTarget;
import io.github.anjoismysign.skeramidcommands.commandtarget.BukkitCommandTarget;
import io.github.anjoismysign.skeramidcommands.server.bukkit.BukkitAdapter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public enum BlobMenuCommand {
    INSTANCE;

    private static final String COMMAND_NAME = "blobmenu";
    private static final String COMMAND_PERMISSION = "blobmenu";
    private static final String COMMAND_DESCRIPTION = "Base command for BlobMenu plugin";

    private static final Command COMMAND = BukkitAdapter.getInstance().createCommand(COMMAND_NAME, COMMAND_PERMISSION, COMMAND_DESCRIPTION);

    private static final BlobLibMessageAPI MESSAGE_API = BlobLibMessageAPI.getInstance();

    public void load(){
        CommandTarget<Player> onlinePlayers = BukkitCommandTarget.ONLINE_PLAYERS();

        Command update = COMMAND.child("update");
        update.setParameters(onlinePlayers);
        update.onExecute((permissionMessenger, args) -> {
            if (args.length < 1){
                return;
            }
            CommandSender sender = BukkitAdapter.getInstance().of(permissionMessenger);
            Player player = onlinePlayers.parse(args[0]);
            if (player == null) {
                MESSAGE_API
                        .getMessage("Player.Not-Found", sender)
                        .toCommandSender(sender);
                return;
            }
            new PlayerHandler(player).load();
        });
    }
}
