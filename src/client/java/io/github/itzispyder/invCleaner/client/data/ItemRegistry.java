package io.github.itzispyder.invCleaner.client.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class ItemRegistry {

    public static void forEachDisplayed(String query, Consumer<Item> action) {
        List<String> list = Config.readList();
        Registries.ITEM.stream()
                .filter(item -> test(item, query, list))
                .limit(64)
                .sorted(Comparator.comparing(Item::getTranslationKey))
                .forEach(action);
    }

    public static void forEach(String query, Consumer<Item> action) {
        Registries.ITEM.stream()
                .filter(item -> test(item, query))
                .forEach(action);
    }

    public static List<Item> collect(String query) {
        List<String> list = Config.readList();
        return Registries.ITEM.stream()
                .filter(item -> test(item, query, list))
                .toList();
    }

    private static boolean test(Item item, String query, List<String> readConfigList) {
        if ("is:enabled".equals(query))
            return readConfigList.contains(String.valueOf(Item.getRawId(item)));
        return test(item, query);
    }

    private static boolean test(Item item, String query) {
        return item.getTranslationKey().toLowerCase().contains(query.toLowerCase()) && item != Items.AIR;
    }
}
