package cherry;

import cherry.config.PluginDefinition;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginFactory {

    Plugin getPlugin(String name);

    Plugin getPlugin(Class<? extends Plugin> type);

    PluginDefinition getPluginDefinition(String name);

    List<String> getPluginNames();

    boolean hasPlugin(String name);

    void close();
}
