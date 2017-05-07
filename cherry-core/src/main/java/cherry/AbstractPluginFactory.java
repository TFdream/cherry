package cherry;

import cherry.config.AppConfig;
import cherry.config.PluginDefinition;
import cherry.exception.PluginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class AbstractPluginFactory implements PluginFactory {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PluginClassLoader classLoader;

    private Map<String, PluginDefinition> pluginNameMap = new HashMap<>();    //key为plugin-name

    private ConcurrentHashMap<String, Object> pluginInstanceMap = new ConcurrentHashMap<>();

    @Override
    public Plugin getPlugin(String name) throws PluginException {
        return null;
    }

    @Override
    public <T extends Plugin> T getPlugin(Class<T> type) throws PluginException {
        return null;
    }

    @Override
    public PluginDefinition getPluginDefinition(String name) {
        return null;
    }

    @Override
    public List<String> getPluginNames() {
        return null;
    }

    @Override
    public boolean hasPlugin(String name) {
        return false;
    }

    @Override
    public void close() {

    }
}
