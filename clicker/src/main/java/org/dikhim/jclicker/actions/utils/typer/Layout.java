package org.dikhim.jclicker.actions.utils.typer;

import org.dikhim.jclicker.jsengine.clickauto.objects.KeyboardObject;

import java.util.List;
import java.util.Map;

public interface Layout {
    void type(KeyboardObject keyboardObject, String text);
    
    String getLayout();
    
    String getDescription();

    Map<String, List<String>> getLayoutMap();

    String getKeyFor(String character);

    int getIndexFor(String key, String character);

    void put(String... ch);
}
