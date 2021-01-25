package cat.yoink.xanax.internal.traits.manager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yoink
 */
public abstract class MapRegistry<K, V>
{
    private final Map<K, V> registry = new HashMap<>();

    public void add(K k, V v)
    {
        registry.put(k, v);
    }

    public void remove(K k)
    {
        registry.remove(k);
    }

    public boolean containsKey(K k)
    {
        return registry.containsKey(k);
    }

    public boolean containsValue(V v)
    {
        return registry.containsValue(v);
    }

    public V getValue(K k)
    {
        return registry.get(k);
    }

    public K getKey(V v)
    {
        return registry.entrySet().stream().filter(entry -> v.equals(entry.getValue())).map(Map.Entry::getKey).findAny().orElse(null);
    }

    public Map<K, V> getRegistry()
    {
        return registry;
    }
}
