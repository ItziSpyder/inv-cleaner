package io.github.itzispyder.invCleaner.client.mixin;

import io.github.itzispyder.invCleaner.client.ui.GUIHandler;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParentElement.class)
public interface MixinParentElement {

    @Inject(method = "mouseClicked", at = @At("HEAD"))
    default void mouseClicked(Click click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
        ParentElement parent = (ParentElement) this;
        if (parent instanceof InventoryScreen inv && !((AccessorRecipeBookScreen) inv).accessRecipeBook().isOpen())
            GUIHandler.get().mouseClicked(click, doubled);
    }

    @Inject(method = "keyReleased", at = @At("HEAD"))
    default void keyReleased(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        ParentElement parent = (ParentElement) this;
        if (parent instanceof InventoryScreen inv && !((AccessorRecipeBookScreen) inv).accessRecipeBook().isOpen())
            GUIHandler.get().keyReleased(input);
    }
}
