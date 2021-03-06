package org.dikhim.jclicker.configuration;


import org.dikhim.jclicker.configuration.recording.Recording;
import org.dikhim.jclicker.configuration.hotkeys.HotKeys;
import org.dikhim.jclicker.configuration.localization.Localization;
import org.dikhim.jclicker.configuration.property.SimpleConfigElement;
import org.dikhim.jclicker.configuration.servers.Servers;
import org.dikhim.jclicker.configuration.storage.Storage;

import java.util.prefs.Preferences;

public class Configuration extends SimpleConfigElement {
    private final HotKeys hotKeys;
    private final Servers servers;
    private final Localization localization;
    private final Recording recording;
    private final Storage storage;

    public Configuration() {
        super("UselessClickerConfig", Preferences.userRoot());

        hotKeys = new HotKeys("hotKeys", getPreferences());
        servers = new Servers("servers", getPreferences());
        localization = new Localization("localization", getPreferences());
        recording = new Recording("recording", getPreferences());
        storage = new Storage();
    }

    @Override
    public void save() {
        hotKeys.save();
        servers.save();
        localization.save();
        recording.save();
    }

    @Override
    public void resetToDefault() {
        hotKeys.resetToDefault();
        servers.resetToDefault();
        localization.resetToDefault();
        recording.resetToDefault();
    }

    @Override
    public void resetToSaved() {
        hotKeys.resetToSaved();
        servers.resetToSaved();
        localization.resetToSaved();
        recording.resetToSaved();
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

    public Recording recording() {
        return recording;
    }

    public Storage storage() {
        return storage;
    }
}
