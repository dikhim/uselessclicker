package org.dikhim.jclicker.eventmanager.event;

public class MouseWheelUpEvent implements MouseWheelEvent {
    private final int amount;
    private final int x;
    private final int y;
    private final long time;

    public MouseWheelUpEvent(int amount, int x, int y, long time) {
        this.amount = amount;
        this.x = x;
        this.y = y;
        this.time = time;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "MouseWheelUpEvent{" +
                "amount=" + amount +
                ", x=" + x +
                ", y=" + y +
                ", time=" + time +
                '}';
    }
}
