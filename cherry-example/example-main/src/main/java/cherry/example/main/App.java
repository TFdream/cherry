package cherry.example.main;

import cherry.DefaultPluginFactory;
import cherry.PluginFactory;
import cherry.example.api.HelloService;
import cherry.example.api.UserService;
import cherry.example.api.model.User;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        PluginFactory pluginFactory = new DefaultPluginFactory("classpath:plugins.xml");

        HelloService helloService = (HelloService) pluginFactory.getPlugin("helloService");
        System.out.println(helloService);

        helloService.hello("cherry");
        helloService.echo("cherry");

        UserService userService = pluginFactory.getPlugin(UserService.class);
        System.out.println(userService);

        User user = userService.getUser("cherry");
        System.out.println(user);

        List<User> list = userService.getUsers();
        System.out.println(list);

        pluginFactory.close();
    }
}
