package cherry;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class DefaultPluginContext implements PluginContext {

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(64);

    @Override
    public Object getAttribute(String attr) {
        return map.get(attr);
    }

    @Override
    public Set<String> getAttributeNames() {
        return map.keySet();
    }

    @Override
    public void setAttribute(String attr, Object value) {
        map.put(attr, value);
    }

    @Override
    public void removeAttribute(String attr) {
        map.remove(attr);
    }
}
