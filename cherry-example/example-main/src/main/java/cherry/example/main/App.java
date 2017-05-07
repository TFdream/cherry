package cherry.example.main;

import cherry.Plugin;
import cherry.DefaultPluginFactory;
import cherry.PluginFactory;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        PluginFactory pluginManager = new DefaultPluginFactory("plugins.xml");

        Plugin plugin = pluginManager.getPlugin("helloService");
        System.out.println(plugin);
    }
}
