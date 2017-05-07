package cherry.config;

import cherry.PluginContext;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginConfig {

    String getPluginName();

    PluginContext getPluginContext();

    String getInitParameter(String name);

    List<String> getInitParameterNames();
}
