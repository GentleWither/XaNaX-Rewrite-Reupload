package cat.yoink.xanax.internal.util;

/**
 * @author yoink
 */
public final class Timer
{
    private int ticks;
    private int old;

    public Timer() { }

    public Timer(int ticks)
    {
        this.ticks = ticks;
        this.old = ticks;
    }

    public void tick()
    {
        ticks++;
    }

    public boolean hasPassed(int ticks)
    {
        return this.ticks > ticks;
    }

    public int getTicks()
    {
        return ticks;
    }

    public void reset()
    {
        ticks = old;
    }
}
