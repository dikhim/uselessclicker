package org.dikhim.jclicker.controllers.utils.recording;

import org.dikhim.jclicker.actions.utils.EventLogger;
import org.dikhim.jclicker.eventmanager.event.MousePressEvent;
import org.dikhim.jclicker.eventmanager.event.MouseReleaseEvent;
import org.dikhim.jclicker.eventmanager.event.MouseWheelDownEvent;
import org.dikhim.jclicker.eventmanager.event.MouseWheelUpEvent;
import org.dikhim.jclicker.eventmanager.listener.SimpleMouseButtonWheelListener;
import org.dikhim.jclicker.jsengine.clickauto.generators.AnimatedMouseCodeGenerator;
import org.dikhim.jclicker.jsengine.clickauto.generators.CodeGenerator;
import org.dikhim.jclicker.jsengine.clickauto.generators.MouseCodeGenerator;

import java.util.function.Consumer;

public class AnimatedMouseAtRecorder extends SimpleMouseRecorder implements LupeRequired {

    public AnimatedMouseAtRecorder(Consumer<String> onRecorded) {
        super(onRecorded);
    }

    private EventLogger eventLog = new EventLogger(4);

    private CodeGenerator animatedMouseCodeGenerator = new AnimatedMouseCodeGenerator();
    private CodeGenerator mouseCodeGenerator = new MouseCodeGenerator();

    @Override
    public void onStart() {
        super.onStart();

        addListener(new SimpleMouseButtonWheelListener("recording.mouse.buttonWheelAtWithDelays") {
            @Override
            public void buttonPressed(MousePressEvent event) {
                if (!isRecording()) return;
                eventLog.add(event);

                int delay = eventLog.getDelay();

                switch (event.getButton()) {
                    case "LEFT":
                        putString(animatedMouseCodeGenerator.forMethod("pressLeftAt", event.getX(), event.getY(), delay));
                        break;
                    case "RIGHT":
                        putString(animatedMouseCodeGenerator.forMethod("pressRightAt", event.getX(), event.getY(), delay));
                        break;
                    case "MIDDLE":
                        putString(animatedMouseCodeGenerator.forMethod("pressMiddleAt", event.getX(), event.getY(), delay));
                        break;
                }
            }

            @Override
            public void buttonReleased(MouseReleaseEvent event) {
                if (!isRecording()) return;
                eventLog.add(event);

                int delay = eventLog.getDelay();
                switch (event.getButton()) {
                    case "LEFT":
                        putString(animatedMouseCodeGenerator.forMethod("releaseLeftAt", event.getX(), event.getY(), delay));
                        break;
                    case "RIGHT":
                        putString(animatedMouseCodeGenerator.forMethod("releaseRightAt", event.getX(), event.getY(), delay));
                        break;
                    case "MIDDLE":
                        putString(animatedMouseCodeGenerator.forMethod("releaseMiddleAt", event.getX(), event.getY(), delay));
                        break;
                }
            }

            @Override
            public void wheeledUp(MouseWheelUpEvent event) {
                if (!isRecording()) return;
                eventLog.add(event);

                int delay = eventLog.getDelay();
                putString(animatedMouseCodeGenerator.forMethod("wheelUpAt", event.getAmount(), event.getX(), event.getY(), delay));
            }

            @Override
            public void wheeledDown(MouseWheelDownEvent event) {
                if (!isRecording()) return;
                eventLog.add(event);

                int delay = eventLog.getDelay();
                putString(animatedMouseCodeGenerator.forMethod("wheelDownAt", event.getAmount(), event.getX(), event.getY(), delay));
            }
        });
    }

    @Override
    protected void onRecordingStarted() {
        eventLog.clear();
    }
}
