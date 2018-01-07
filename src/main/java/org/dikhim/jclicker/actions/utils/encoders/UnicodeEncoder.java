package org.dikhim.jclicker.actions.utils.encoders;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.dikhim.jclicker.actions.events.*;
import org.dikhim.jclicker.actions.utils.KeyCodes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.dikhim.jclicker.actions.utils.encoders.ActionType.*;


@SuppressWarnings("Duplicates")
public class UnicodeEncoder extends AbstractActionEncoder {


    private static BidiMap<ActionType, Character> actionCodes = new DualHashBidiMap<>();

    {
        actionCodes.put(KEYBOARD_PRESS, 'k');
        actionCodes.put(KEYBOARD_RELEASE, 'K');

        actionCodes.put(MOUSE_MOVE, 'R');
        actionCodes.put(MOUSE_MOVE_AT, 'A');

        actionCodes.put(MOUSE_PRESS_LEFT, 'l');
        actionCodes.put(MOUSE_PRESS_RIGHT, 'r');
        actionCodes.put(MOUSE_PRESS_MIDDLE, 'm');

        actionCodes.put(MOUSE_RELEASE_LEFT, 'L');
        actionCodes.put(MOUSE_RELEASE_RIGHT, 'R');
        actionCodes.put(MOUSE_RELEASE_MIDDLE, 'M');

        actionCodes.put(MOUSE_WHEEL_UP, 'W');
        actionCodes.put(MOUSE_WHEEL_DOWN, 'w');

        actionCodes.put(DELAY_MILISECONDS, 'D');
        actionCodes.put(DELAY_SECONDS, 'S');
    }

    public static BidiMap<ActionType, Character> getActionCodes() {
        return actionCodes;
    }

    @Override
    public String encode(List<Event> eventList) {
        List<Event> filteredEventList = filter(eventList);
        if (isRelative()) {
            return encodeRelative(filteredEventList);
        } else {
            return encodeAbsolute(filteredEventList);
        }
    }

    private String encodeAbsolute(List<Event> eventList) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < eventList.size(); i++) {
            Event e = eventList.get(i);

            switch (e.getType()) {
                case KEYBOARD:
                    appendKeyboardCode(sb, e);
                    break;
                case MOUSE_BUTTON:
                    appendMouseButtonCode(sb, e);
                    break;
                case MOUSE_WHEEL:
                    appendWheelCode(sb, e);
                    break;
                case MOUSE_MOVE:
                    appendMoveAtCode(sb, e);
                    break;
            }

            if (isIncludesDelays() && i > 0) {
                int delay = (int) (e.getTime() - eventList.get(i - 1).getTime());
                appendDelay(sb, delay);
            }
        }
        return sb.toString();
    }

    private String encodeRelative(List<Event> eventList) {
        // TODO
        return "";
    }

    @Override
    protected String encode(int i) {
        return Character.toString((char) (i + getSHIFT()));
    }

    private void appendKeyboardCode(StringBuilder sb, Event event) {
        KeyboardEvent keyboardEvent = (KeyboardEvent) event;
        if (keyboardEvent.getAction().equals("PRESS")) {
            sb.append(actionCodes.get(KEYBOARD_PRESS));
        } else {
            sb.append(actionCodes.get(KEYBOARD_RELEASE));
        }
        sb.append(encode(KeyCodes.getUslessCodeByName(keyboardEvent.getKey())));
    }

    private void appendMouseButtonCode(StringBuilder sb, Event event) {
        MouseButtonEvent mouseButtonEvent = (MouseButtonEvent) event;

        switch (mouseButtonEvent.getButton()) {
            case "LEFT":
                switch (mouseButtonEvent.getAction()) {
                    case "PRESS":
                        sb.append(actionCodes.get(MOUSE_PRESS_LEFT));
                        break;
                    case "RELEASE":
                        sb.append(actionCodes.get(MOUSE_RELEASE_LEFT));
                        break;
                }
                break;
            case "RIGHT":
                switch (mouseButtonEvent.getAction()) {
                    case "PRESS":
                        sb.append(actionCodes.get(MOUSE_PRESS_RIGHT));
                        break;
                    case "RELEASE":
                        sb.append(actionCodes.get(MOUSE_RELEASE_RIGHT));
                        break;
                }
                break;
            case "MIDDLE":
                switch (mouseButtonEvent.getAction()) {
                    case "PRESS":
                        sb.append(actionCodes.get(MOUSE_PRESS_MIDDLE));
                        break;
                    case "RELEASE":
                        sb.append(actionCodes.get(MOUSE_RELEASE_MIDDLE));
                        break;
                }
                break;
        }
    }

    private void appendWheelCode(StringBuilder sb, Event event) {
        MouseWheelEvent mouseWheelEvent = (MouseWheelEvent) event;
        switch (mouseWheelEvent.getDirection()) {
            case "DOWN":
                sb.append(actionCodes.get(MOUSE_WHEEL_DOWN));
                break;
            case "UP":
                sb.append(actionCodes.get(MOUSE_WHEEL_UP));
                break;
        }
    }

    private void appendMoveAtCode(StringBuilder sb, Event event) {
        MouseMoveEvent mouseMoveEvent = (MouseMoveEvent) event;
        sb.append(actionCodes.get(MOUSE_MOVE_AT));
        sb.append(encode(mouseMoveEvent.getX()));
        sb.append(encode(mouseMoveEvent.getY()));
    }

    private void appendDelay(StringBuilder sb, int delay) {
        if (delay <= 5000) {
            sb.append(actionCodes.get(DELAY_MILISECONDS));
            sb.append(encode(delay));
        } else {
            int milliseconds = delay % 1000;
            int seconds = delay / 1000;
            if (seconds > 3600) seconds = 3600;
            sb.append(actionCodes.get(DELAY_SECONDS));
            sb.append(encode(seconds));
            sb.append(actionCodes.get(DELAY_MILISECONDS));
            sb.append(encode(milliseconds));
        }
    }

}
