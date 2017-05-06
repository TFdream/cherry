package cherry;

import cherry.config.PluginConfig;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface Plugin {

    void init(PluginConfig config);

    Object doBusiness(Object... params);

    void destroy();
}
