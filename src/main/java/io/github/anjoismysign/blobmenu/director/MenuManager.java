package io.github.anjoismysign.blobmenu.director;

import io.github.anjoismysign.bloblib.entities.GenericManager;
import io.github.anjoismysign.blobmenu.BlobMenu;

public class MenuManager extends GenericManager<BlobMenu, MenuManagerDirector> {
    public MenuManager(MenuManagerDirector managerDirector) {
        super(managerDirector);
    }
}