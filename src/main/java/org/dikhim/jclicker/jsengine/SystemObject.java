package org.dikhim.jclicker.jsengine;

import org.dikhim.jclicker.events.KeyEventsManager;
import org.dikhim.jclicker.events.ShortcutEqualsHandler;
import org.dikhim.jclicker.util.output.Out;

public class SystemObject {
	private JSEngine engine;
	public SystemObject(JSEngine engine) {
		this.engine = engine;
	}
	/**
	 * Register shortcut for call function
	 * 
	 * @param function name
	 * @param shortcut string
	 */
	
	public void registerShortcut( String shortcut, String function) {
		ShortcutEqualsHandler handler = new ShortcutEqualsHandler("script." + function,
				shortcut, () -> {
					engine.addTask(() -> {
						engine.invokeFunction(function);
					});
				});
		KeyEventsManager.getInstance().addPressListener(handler);
	}
	
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
	
	public void print(String s) {
		Out.print(s);
	}
	public void println(String s) {
		Out.println(s);
	}
	
}
