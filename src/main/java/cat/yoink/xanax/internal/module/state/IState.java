package cat.yoink.xanax.internal.module.state;

/**
 * @author yoink
 */
public interface IState
{
    default void onEnable() {}

    default void onDisable() {}
}
