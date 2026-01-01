package io.github.itzispyder.invCleaner;

import io.github.itzispyder.improperui.ImproperUIAPI;
import net.fabricmc.api.ModInitializer;

import java.util.Arrays;

public class InvCleaner implements ModInitializer {

    public static final String modId = "inv-cleaner";
    public static final String configFile = "config.properties";
    public static final String improperuiPath = "assets/inv-cleaner/improperui/";
    public static final String[] improperuiFiles = {
        "widget.ui"
    };

    @Override
    public void onInitialize() {
        ImproperUIAPI.init(modId, InvCleaner.class, Arrays.stream(improperuiFiles)
                .map(s -> improperuiPath + s)
                .toArray(String[]::new));
    }

}
