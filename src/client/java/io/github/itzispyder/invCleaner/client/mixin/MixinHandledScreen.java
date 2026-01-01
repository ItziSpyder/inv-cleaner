package io.github.itzispyder.invCleaner.client.mixin;

import io.github.itzispyder.invCleaner.client.ui.GUI;
import io.github.itzispyder.invCleaner.client.ui.GUIHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.input.KeyInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HandledScreen.class)
public abstract class MixinHandledScreen {

    @Inject(method = "mouseScrolled", at = @At("HEAD"))
    void mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount, CallbackInfoReturnable<Boolean> cir) {
        ParentElement parent = (ParentElement) this;
        if (parent instanceof InventoryScreen)
            GUIHandler.get().mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
    }

    @Inject(method = "keyPressed", at = @At("HEAD"))
    void keyPressed(KeyInput input, CallbackInfoReturnable<Boolean> cir) {
        ParentElement parent = (ParentElement) this;
        if (parent instanceof InventoryScreen)
            GUIHandler.get().keyPressed(input);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (MinecraftClient.getInstance().currentScreen instanceof InventoryScreen)
            GUIHandler.get().tick();
    }

    @Inject(method = "close", at = @At("TAIL"), cancellable = true)
    public void close(CallbackInfo ci) {
        GUI gui = GUIHandler.get();
        if (gui != null && gui.focused == gui.search) {
            ci.cancel();
            return;
        }
        GUIHandler.destroy();
    }
}
