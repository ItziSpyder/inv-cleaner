package io.github.itzispyder.invCleaner.client.ui;

import io.github.itzispyder.improperui.config.Paths;
import io.github.itzispyder.improperui.render.Element;
import io.github.itzispyder.improperui.render.ImproperUIPanel;
import io.github.itzispyder.improperui.render.constants.Visibility;
import io.github.itzispyder.improperui.render.elements.TextBox;
import io.github.itzispyder.improperui.script.ScriptParser;
import io.github.itzispyder.invCleaner.InvCleaner;
import io.github.itzispyder.invCleaner.client.data.Config;
import io.github.itzispyder.invCleaner.client.data.ItemRegistry;

import java.io.File;

public class GUI extends ImproperUIPanel {

    private final Element results;
    private final Element clear;
    public final TextBox search;
    private final Element root;
    private boolean showDetails;

    public GUI() {
        super(ScriptParser.parseFile(
                new File(Paths.getScripts(InvCleaner.modId), InvCleaner.improperuiFiles[0])));

        this.registerCallback(new GUICallbacks(this));
        this.search = (TextBox) this.collectFirstById("search");
        this.clear = this.collectFirstById("clear");
        this.root = this.collectFirstById("anchor");
        this.results = this.collectFirstById("results");
        this.updateResults(search.getQuery());
        this.setDetailed(Config.isDetailed());
    }

    public Element getRoot() {
        return root;
    }

    public void setDetailed(boolean detailed) {
        if (detailed)
            results.callProperty("grid-columns: 1");
        else
            results.callProperty("grid-columns: 3");

        showDetails = detailed;
        Config.setDetailed(showDetails);
        updateResults(getCurrentQuery());
    }

    public String getCurrentQuery() {
        return search.getQuery();
    }

    public void clearQuery() {
        search.setQuery("");
        updateResults("");
    }

    public void updateResults(String query) {
        this.clear.visibility = query.isEmpty() ? Visibility.INVISIBLE : Visibility.VISIBLE;
        this.results.clearChildren();
        ItemRegistry.forEachDisplayed(query, item -> {
            SearchResultElement result = new SearchResultElement(item, showDetails);
            result.onLoadKey(null, null);
            this.results.addChild(result);
        });
        this.results.style();
    }
}
