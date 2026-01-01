package io.github.itzispyder.invCleaner.client.data;

import io.github.itzispyder.improperui.ImproperUIAPI;
import io.github.itzispyder.improperui.config.ConfigReader;
import io.github.itzispyder.invCleaner.InvCleaner;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private static final ConfigReader CONFIG = ImproperUIAPI.getConfigReader(InvCleaner.modId, InvCleaner.configFile);

    public static List<String> readList() {
        return CONFIG.readList("itemList", new ArrayList<>());
    }

    public static void writeList(List<String> list) {
        CONFIG.writeList("itemList", list);
    }

    public static boolean listHas(Item item) {
        return readList().contains(String.valueOf(Item.getRawId(item)));
    }

    public static void addUnique(Item item) {
        List<String> list = readList();
        String id = String.valueOf(Item.getRawId(item));
        if (list.contains(id))
            return;

        list.add(id);
        writeList(list);
    }

    public static void addUnique(List<Item> items) {
        List<String> list = readList();
        items.stream()
                .map(item -> String.valueOf(Item.getRawId(item)))
                .filter(id -> !list.contains(id))
                .forEach(list::add);
        writeList(list);
    }

    public static void remove(Item item) {
        List<String> list = readList();
        String id = String.valueOf(Item.getRawId(item));

        if (list.remove(id))
            writeList(list);
    }

    public static void remove(List<Item> items) {
        List<String> list = readList();
        boolean success = items.stream()
                .map(item -> String.valueOf(Item.getRawId(item)))
                .allMatch(list::remove);

        if (success)
            writeList(list);
    }

    public static void invertList(List<Item> items) {
        List<String> list = readList();
        items.stream()
                .map(item -> String.valueOf(Item.getRawId(item)))
                .forEach(id -> {
                    if (list.contains(id))
                        list.remove(id);
                    else
                        list.add(id);
                });
        writeList(list);
    }

    public static boolean isDetailed() {
        return CONFIG.readBool("showDetails", true);
    }

    public static void setDetailed(boolean showDetails) {
        CONFIG.write("showDetails", showDetails);
    }
}
