package io.github.anjoismysign.blobmenu.configuration;

import io.github.anjoismysign.blobmenu.BlobMenu;
import io.github.anjoismysign.blobmenu.entity.MenuItemCommandSender;
import io.github.anjoismysign.blobmenu.entity.PermissionMode;

import java.util.List;

public class MenuConfiguration {
    public static MenuConfiguration getInstance(){
        return BlobMenu.getInstance().getManagerDirector().getConfigurationManager().getConfiguration();
    }

    private boolean tinyDebug;
    private long loadDelay;
    private List<MenuItem> menu;

    public boolean isTinyDebug() {
        return tinyDebug;
    }

    public void setTinyDebug(boolean tinyDebug) {
        this.tinyDebug = tinyDebug;
    }

    public long getLoadDelay() {
        return loadDelay;
    }

    public void setLoadDelay(long loadDelay) {
        this.loadDelay = loadDelay;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public static class MenuItem {
        private String translatableItem;
        private int slot;
        private String permission;
        private PermissionMode permissionMode;
        private String command;
        private MenuItemCommandSender commandSender;

        public String getTranslatableItem() {
            return translatableItem;
        }

        public void setTranslatableItem(String translatableItem) {
            this.translatableItem = translatableItem;
        }

        public int getSlot() {
            return slot;
        }

        public void setSlot(int slot) {
            this.slot = slot;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }

        public PermissionMode getPermissionMode() {
            return permissionMode;
        }

        public void setPermissionMode(PermissionMode permissionMode) {
            this.permissionMode = permissionMode;
        }

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public MenuItemCommandSender getCommandSender() {
            return commandSender;
        }

        public void setCommandSender(MenuItemCommandSender commandSender) {
            this.commandSender = commandSender;
        }
    }
}
