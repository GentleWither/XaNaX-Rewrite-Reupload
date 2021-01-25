package cat.yoink.xanax.internal.traits.interfaces;

/**
 * @author yoink
 */
public interface Toggleable
{
    void toggle();

    void setState(boolean state);

    boolean getState();
}
