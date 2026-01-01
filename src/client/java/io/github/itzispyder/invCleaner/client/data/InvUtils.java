package io.github.itzispyder.invCleaner.client.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.screen.sync.ItemStackHash;

import java.util.function.IntConsumer;
import java.util.function.Predicate;

public final class InvUtils {

    public static PlayerInventory inv() {
        return MinecraftClient.getInstance().player.getInventory();
    }

    public static boolean dropSlot(int slot, boolean full) {
        return sendSlotPacket(slot, full ? 1 : 0, SlotActionType.THROW);
    }

    public static int search(Item item) {
        return search(stack -> stack.isOf(item));
    }

    public static int search(Predicate<ItemStack> item) {
        if (item == null)
            return -1;

        for (int i = 0; i < inv().getMainStacks().size(); i++) {
            ItemStack stack = inv().getStack(i);
            if (stack == null || stack.isEmpty()) continue;
            if (item.test(stack)) return i;
        }

        return -1;
    }

    public static void forEachSlot(Predicate<ItemStack> item, IntConsumer slotAction) {
        if (item == null)
            return;

        for (int i = 0; i < inv().getMainStacks().size(); i++) {
            ItemStack stack = inv().getStack(i);
            if (stack == null || stack.isEmpty()) continue;
            if (item.test(stack))
                slotAction.accept(i);
        }
    }

    public static boolean sendSlotPacket(int slot, int button, SlotActionType action) {
        ItemStack stack = inv().getStack(slot);
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayNetworkHandler network = client.getNetworkHandler();

        if (slot <= 8)
            slot += 36;
        if (stack == null || client.player == null || network == null)
            return false;

        int hashSlot = slot;
        ItemStackHash hash = ItemStackHash.fromItemStack(stack, component -> hashSlot);
        ClickSlotC2SPacket swap = new ClickSlotC2SPacket(0, 1, (short) slot, (byte) button, action, Int2ObjectMaps.singleton(slot, hash), hash);
        network.sendPacket(swap);
        return true;
    }
}