package org.dikhim.jclicker.actions.utils.typer;

import org.dikhim.jclicker.jsengine.clickauto.objects.KeyboardObject;

public class Typers {
    public static Typer create(KeyboardObject keyboardObject, String layoutName) throws Exception {
        if ("us".equals(layoutName)) {
            return new UsTyper(keyboardObject);
        } else if ("gb".equals(layoutName)) {
            return new GbTyper(keyboardObject);
        } else if ("ru".equals(layoutName)) {
            return new RuTyper(keyboardObject);
        } else if ("ua".equals(layoutName)) {
            return new UaTyper(keyboardObject);
        }
        throw new Exception(String.format("Layout '%s' isn't supported", layoutName));
    }
}
