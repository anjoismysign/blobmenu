package io.github.anjoismysign.blobmenu.entity;

import io.github.anjoismysign.bloblib.entities.CommandData;

public record PlayerMenuItem(String translatableItem,
                             int slot,
                             CommandData commandData) {
}
