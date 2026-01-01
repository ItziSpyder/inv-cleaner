package io.github.itzispyder.invCleaner.client.ui;

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
}
