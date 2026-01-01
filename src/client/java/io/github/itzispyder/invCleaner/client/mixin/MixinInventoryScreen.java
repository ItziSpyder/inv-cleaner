package io.github.itzispyder.invCleaner.client.mixin;

import io.github.itzispyder.improperui.render.constants.Visibility;
import io.github.itzispyder.invCleaner.client.ui.GUI;
import io.github.itzispyder.invCleaner.client.ui.GUIHandler;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public abstract class MixinInventoryScreen {

    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        GUIHandler.create();
    }

    @Inject(method = "mouseReleased", at = @At("HEAD"))
    void mouseReleased(Click click, CallbackInfoReturnable<Boolean> cir) {
        ParentElement parent = (ParentElement) this;
        if (parent instanceof InventoryScreen inv && !((AccessorRecipeBookScreen) inv).accessRecipeBook().isOpen())
            GUIHandler.get().mouseReleased(click);
    }

    @Inject(method = "render", at = @At("TAIL"))
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci) {
        AccessorRecipeBookScreen rbs = (AccessorRecipeBookScreen) this;
        GUI gui = GUIHandler.get();

        if (rbs.accessRecipeBook().isOpen()) {
            if (gui.getRoot().visibility == Visibility.VISIBLE)
                gui.getRoot().visibility = Visibility.INVISIBLE;
            return;
        }
        else if (gui.getRoot().visibility != Visibility.VISIBLE) {
            gui.getRoot().visibility = Visibility.VISIBLE;
        }

        gui.render(context, mouseX, mouseY, deltaTicks);
    }
}
