package cherry.demo.plugin;

import cherry.PluginContextEvent;
import cherry.PluginContextListener;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ExampleContextListener implements PluginContextListener {

    @Override
    public void contextInitialized(PluginContextEvent event) {
        System.out.println("context Initialized");
    }

    @Override
    public void contextDestroyed(PluginContextEvent event) {
        System.out.println("context Destroyed");
    }
}
