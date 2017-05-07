package cherry;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface PluginContextListener {

    void contextInitialized(PluginContext event);

    void contextDestroyed(PluginContext event);
}
