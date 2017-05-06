package cherry;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginFactory {

    Plugin getPlugin(String name);

    PluginDefinition getPluginDefinition(String name);

    List<String> getPluginNames();

    boolean hasPlugin(String name);

    void close();
}
