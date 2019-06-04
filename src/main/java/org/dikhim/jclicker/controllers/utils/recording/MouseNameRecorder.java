package org.dikhim.jclicker.controllers.utils.recording;

import org.dikhim.jclicker.eventmanager.listener.MouseReleaseListener;

import java.util.function.Consumer;

/**
 * 'LEFT RIGHT MIDDLE '
 */
public class MouseNameRecorder extends SimpleMouseRecorder {
    public MouseNameRecorder(Consumer<String> onRecorded) {
        super(onRecorded);
    }

    @Override
    public void onStart() {
        super.onStart();
        addListener("recording.mouse.name", (MouseReleaseListener) event -> {
            if (isRecording()) putString(event.getButton() + " ");
        });
    }
}
