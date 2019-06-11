package org.dikhim.jclicker.eventmanager.listener;

import javafx.beans.property.StringProperty;
import org.dikhim.jclicker.eventmanager.event.KeyPressEvent;
import org.dikhim.jclicker.eventmanager.event.KeyReleaseEvent;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class ShortcutReleaseListener extends SimpleListener implements KeyListener {
    private Set<String> pressedKeys = new TreeSet<>();
    private Set<String> shortcutKeys = new TreeSet<>();
    private Runnable onFire;

    public ShortcutReleaseListener(String id, String keys, Runnable onFire) {
        super(id);
        this.onFire = onFire;

        shortcutKeys.addAll(Arrays.asList(keys.split(" ")));
    }

    public ShortcutReleaseListener(String id, Runnable onFire, StringProperty keysProperty) {
        super(id);
        this.onFire = onFire;

        shortcutKeys.addAll(Arrays.asList(keysProperty.getValue().split(" ")));

        keysProperty.addListener((observable, oldValue, newValue) -> {
            shortcutKeys.clear();
            shortcutKeys.addAll(Arrays.asList(newValue.split(" ")));
        });
    }

    @Override
    public void keyPressed(KeyPressEvent event) {
        pressedKeys.add(event.getKey());
    }

    @Override
    public void keyReleased(KeyReleaseEvent event) {
        check();
        pressedKeys.remove(event.getKey());
    }

    private void check() {
        if (pressedKeys.equals(shortcutKeys)) {
            onFire.run();
        }
    }
}