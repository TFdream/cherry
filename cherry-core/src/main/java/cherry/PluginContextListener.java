package cherry;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginContextListener {

    void contextInitialized(PluginContextEvent event);

    void contextDestroyed(PluginContextEvent event);
}
