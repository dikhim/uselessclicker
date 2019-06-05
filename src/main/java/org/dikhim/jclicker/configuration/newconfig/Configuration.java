package org.dikhim.jclicker.configuration.newconfig;

import org.dikhim.jclicker.configuration.newconfig.hotkeys.HotKeys;
import org.dikhim.jclicker.configuration.newconfig.localization.Localization;
import org.dikhim.jclicker.configuration.newconfig.property.SimpleConfigElement;
import org.dikhim.jclicker.configuration.newconfig.servers.Servers;
import org.dikhim.jclicker.configuration.newconfig.storage.Storage;

import java.util.prefs.Preferences;

public class Configuration extends SimpleConfigElement {
    private final HotKeys hotKeys;
    private final Servers servers;
    private final Localization localization;
    private final Storage storage;

    public Configuration() {
        super("UselessClickerConfig", Preferences.userRoot());

        hotKeys = new HotKeys("hotKeys", getPreferences());
        servers = new Servers("servers", getPreferences());
        localization = new Localization("localization", getPreferences());
        storage = new Storage();
    }

    @Override
    public void save() {
        hotKeys.save();
    }

    @Override
    public void resetToDefault() {
        hotKeys.resetToDefault();
    }

    @Override
    public void resetToSaved() {
        hotKeys.resetToSaved();
    }

    public HotKeys hotKeys() {
        return hotKeys;
    }
    
    public Servers servers() {
        return servers;
    }
    
    public Localization localization() {
        return localization;
    }
    
    public Storage storage(){
        return storage;}
}
