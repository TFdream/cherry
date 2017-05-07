package cherry;

import cherry.config.PluginDefinition;
import cherry.exception.PluginException;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginFactory {

    Plugin getPlugin(String name) throws PluginException;

    <T extends Plugin> T getPlugin(Class<T> type) throws PluginException;

    PluginDefinition getPluginDefinition(String name);

    List<String> getPluginNames();

    boolean hasPlugin(String name);

    void close();
}
