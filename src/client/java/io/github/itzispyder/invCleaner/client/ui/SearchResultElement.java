package io.github.itzispyder.invCleaner.client.ui;

import io.github.itzispyder.improperui.config.ConfigKey;
import io.github.itzispyder.improperui.config.PropertyCache;
import io.github.itzispyder.improperui.render.KeyHolderElement;
import io.github.itzispyder.improperui.util.render.RenderUtils;
import io.github.itzispyder.invCleaner.client.data.Config;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Item;

public class SearchResultElement extends KeyHolderElement {

    private final Item item;
    private final ExtendsCheckBox checkbox;
    private final boolean detailed;

    public SearchResultElement(Item item, boolean detailed) {
        if (detailed)
            this.queueProperty("size: 100% 20");
        else
            this.queueProperty("size: 33 20");
        this.queueProperty("border-radius: 2");
        this.queueProperty("background-color: #60000000");
        this.queueProperty("margin-bottom: 1");
        this.queueProperty("margin-right: 1");

        this.item = item;
        this.detailed = detailed;
        this.checkbox = new ExtendsCheckBox(this::onCheckBoxUpdate) {{
            this.queueProperty("size: 7");
            this.queueProperty("center: vertical");
            this.queueProperty("x: 5");
        }};
        this.addChild(checkbox);
    }

    public void onCheckBoxUpdate() {
        this.onSaveKey(null, null);
    }

    @Override
    public void onRender(DrawContext context, int mx, int my, float delta) {
        int x = getPosX();
        int y = getPosY();
        super.onRender(context, mx, my, delta);
        RenderUtils.drawItem(context, item.getDefaultStack(), x + 15, y + 2);

        if (detailed)
            RenderUtils.drawText(context, item.getName().getString(), x + 35, y + height * 2 / 5, 0.7F, false);
    }

    @Override
    public void onLoadKey(PropertyCache propertyCache, ConfigKey configKey) {
        checkbox.setActive(Config.listHas(item));
    }

    @Override
    public void onSaveKey(PropertyCache propertyCache, ConfigKey configKey) {
        if (checkbox.isActive())
            Config.addUnique(item);
        else
            Config.remove(item);
    }

    @Override
    public ConfigKey getConfigKey() {
        return null;
    }
}
