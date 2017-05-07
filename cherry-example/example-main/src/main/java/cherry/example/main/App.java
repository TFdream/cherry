package cherry.example.main;

import cherry.DefaultPluginFactory;
import cherry.Plugin;
import cherry.PluginFactory;
import cherry.example.api.UserService;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        PluginFactory pluginFactory = new DefaultPluginFactory("classpath:plugins.xml");

        Plugin helloService = pluginFactory.getPlugin("helloService");
        System.out.println(helloService);

        Plugin userService = pluginFactory.getPlugin(UserService.class);
        System.out.println(userService);
    }
}
