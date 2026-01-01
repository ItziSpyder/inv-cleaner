package io.github.itzispyder.invCleaner.client.ui;

import io.github.itzispyder.improperui.render.elements.CheckBox;
import io.github.itzispyder.improperui.script.CallbackHandler;
import io.github.itzispyder.improperui.script.CallbackListener;
import io.github.itzispyder.improperui.script.events.KeyEvent;
import io.github.itzispyder.improperui.script.events.MouseEvent;
import io.github.itzispyder.invCleaner.client.data.Config;
import io.github.itzispyder.invCleaner.client.data.InvUtils;
import io.github.itzispyder.invCleaner.client.data.ItemRegistry;
import net.minecraft.item.Item;

import java.util.List;

public class GUICallbacks implements CallbackListener {

    private final GUI parent;

    public GUICallbacks(GUI parent) {
        this.parent = parent;
    }

    @CallbackHandler
    public void onQueryUpdate(KeyEvent e) {
        if (!e.input.isDown())
            return;
        parent.updateResults(parent.getCurrentQuery());
    }

    @CallbackHandler
    public void onClearQuery(MouseEvent e) {
        if (!e.input.isDown())
            return;
        parent.clearQuery();
    }

    @CallbackHandler
    public void onResultsEnable(MouseEvent e) {
        if (!e.input.isDown())
            return;
        Config.addUnique(ItemRegistry.collect(parent.getCurrentQuery()));
        parent.updateResults(parent.getCurrentQuery());
    }

    @CallbackHandler
    public void onResultsDisable(MouseEvent e) {
        if (!e.input.isDown())
            return;
        Config.remove(ItemRegistry.collect(parent.getCurrentQuery()));
        parent.updateResults(parent.getCurrentQuery());
    }

    @CallbackHandler
    public void onResultsInvert(MouseEvent e) {
        if (!e.input.isDown())
            return;
        Config.invertList(ItemRegistry.collect(parent.getCurrentQuery()));
        parent.updateResults(parent.getCurrentQuery());
    }

    @CallbackHandler
    public void onActionDiscard(MouseEvent e) {
        if (!e.input.isDown())
            return;
        List<String> list = Config.readList();
        InvUtils.forEachSlot(stack -> {
            return list.contains(String.valueOf(Item.getRawId(stack.getItem())));
        }, slot -> {
            InvUtils.dropSlot(slot, true);
        });
    }

    @CallbackHandler
    public void onActionKeep(MouseEvent e) {
        if (!e.input.isDown())
            return;
        List<String> list = Config.readList();
        InvUtils.forEachSlot(stack -> {
            return !list.contains(String.valueOf(Item.getRawId(stack.getItem())));
        }, slot -> {
            InvUtils.dropSlot(slot, true);
        });
    }

    @CallbackHandler
    public void onShowDetailsToggle(MouseEvent e) {
        if (!e.input.isDown())
            return;
        if (e.target instanceof CheckBox check)
            parent.setDetailed(!check.isActive());
    }

    @CallbackHandler
    public void onShowToggled(MouseEvent e) {
        if (!e.input.isDown())
            return;
        parent.getSearchBar().setQuery("is:enabled");
        parent.updateResults(parent.getCurrentQuery());
    }
}
