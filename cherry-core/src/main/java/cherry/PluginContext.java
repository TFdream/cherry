package cherry;

import java.util.Set;

/**
 * 上下文对象
 *
 * @author Ricky Fung
 */
public interface PluginContext {

    Object getAttribute(String attr);

    Set<String> getAttributeNames();

    void setAttribute(String attr, Object value);

    void removeAttribute(String attr);
}
