package org.dikhim.jclicker.controllers.utils.recording;

import org.dikhim.jclicker.eventmanager.event.MouseWheelDownEvent;
import org.dikhim.jclicker.eventmanager.event.MouseWheelUpEvent;
import org.dikhim.jclicker.eventmanager.listener.SimpleMouseWheelListener;
import org.dikhim.jclicker.jsengine.clickauto.generators.CodeGenerator;
import org.dikhim.jclicker.jsengine.clickauto.generators.MouseCodeGenerator;

import java.util.function.Consumer;

/**
 * mouse.wheelAt('DOWN',3,562,823);
 */
public class WheelAtRecorder extends SimpleMouseRecorder implements LupeRequired {
    public WheelAtRecorder(Consumer<String> onRecorded) {
        super(onRecorded);
    }

    private CodeGenerator codeGenerator = new MouseCodeGenerator();

    @Override
    public void onStart() {
        super.onStart();
        addListener(new SimpleMouseWheelListener("recording.mouse.wheelAt") {
            @Override
            public void wheeledUp(MouseWheelUpEvent event) {
                if (!isRecording()) return;
                putString(codeGenerator.forMethod("wheelUpAt", event.getAmount(), event.getX(), event.getY()));
            }

            @Override
            public void wheeledDown(MouseWheelDownEvent event) {
                if (!isRecording()) return;
                putString(codeGenerator.forMethod("wheelDownAt", event.getAmount(), event.getX(), event.getY()));
            }
        });
    }
}
