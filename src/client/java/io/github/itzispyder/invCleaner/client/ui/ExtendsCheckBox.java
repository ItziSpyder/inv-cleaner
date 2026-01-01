package io.github.itzispyder.invCleaner.client.ui;

import io.github.itzispyder.improperui.render.elements.CheckBox;

public class ExtendsCheckBox extends CheckBox {

    private final Runnable onUpdate;

    public ExtendsCheckBox(Runnable onUpdate) {
        this.onUpdate = onUpdate;
    }

    @Override
    public void setActive(boolean active) {
        super.setActive(active);
        onUpdate.run();
    }
}
