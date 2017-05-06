package cherry.config;

import cherry.context.PluginContext;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class DefaultPluginConfig implements PluginConfig {

    @Override
    public String getPluginName() {
        return null;
    }

    @Override
    public PluginContext getPluginContext() {
        return null;
    }

    @Override
    public String getInitParameter(String name) {
        return null;
    }

    @Override
    public List<String> getInitParameterNames() {
        return null;
    }
}
