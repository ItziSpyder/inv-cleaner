package io.github.itzispyder.invCleaner.client.ui;

import io.github.itzispyder.invCleaner.client.mixin.AccessorRecipeBookScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;

public class GUIHandler {

    private static GUI instance;

    public static void create() {
        instance = new GUI();
    }

    public static GUI get() {
        return instance;
    }

    public static void destroy() {
        instance = null;
    }

    public static boolean passEventPoll() {
        if (!(MinecraftClient.getInstance().currentScreen instanceof InventoryScreen inv))
            return false;
        return !((AccessorRecipeBookScreen) inv).accessRecipeBook().isOpen();
    }
}
