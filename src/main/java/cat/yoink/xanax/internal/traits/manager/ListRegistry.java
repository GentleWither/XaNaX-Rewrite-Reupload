package cat.yoink.xanax.internal.traits.manager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yoink
 */
public abstract class ListRegistry<E>
{
    private final List<E> registry = new ArrayList<>();

    public final void add(E e)
    {
        registry.add(e);
    }

    @SafeVarargs
    public final void addAll(E... e)
    {
        registry.addAll(Arrays.asList(e));
    }

    public final void remove(E e)
    {
        registry.remove(e);
    }

    @SafeVarargs
    public final void removeAll(E... e)
    {
        registry.removeAll(Arrays.asList(e));
    }

    public final E get(int i)
    {
        return registry.get(i);
    }

    public final List<E> getRegistry()
    {
        return registry;
    }
}
