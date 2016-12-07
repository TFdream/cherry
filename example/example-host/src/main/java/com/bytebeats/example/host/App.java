package com.bytebeats.example.host;

import com.bytebeats.pandora.PluginManager;
import com.bytebeats.pandora.example.api.IHelloService;
import com.bytebeats.pandora.example.api.IUserService;
import com.bytebeats.pandora.example.domain.User;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        PluginManager<IHelloService> pluginManager = PluginManager.load(IHelloService.class);

        IHelloService helloService = pluginManager.getPlugin("com.bytebeats.example.plugin.HelloServiceImpl");
        helloService.hello("ricky");

        IUserService userService = pluginManager.load(IUserService.class).getPlugin("com.bytebeats.example.plugin.UserServiceImpl");
        List<User> list = userService.getUsers();
        System.out.println("list = [" + list + "]");

        userService.update(new User("test", "test"));
    }
}
