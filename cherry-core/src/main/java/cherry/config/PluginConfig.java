package cherry.config;

import cherry.PluginContext;
import java.util.List;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginConfig {

    String getPluginName();

    PluginContext getPluginContext();

    String getInitParameter(String name);

    Set<String> getInitParameterNames();
}
