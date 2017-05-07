# Cherry [![Build Status](https://travis-ci.org/TiFG/cherry.svg?branch=master)](https://travis-ci.org/TiFG/cherry)
Cherry is a simple and flexible plugins management library written in Java.

## Quick Start

### 1.Plugin interface

1. HelloService.java
```
public interface HelloService extends Plugin {

    String echo(String msg);

    void hello(String msg);
}
```

2.UserService.java
```
public interface UserService extends Plugin {

    User getUser(String name);

    List<User> getUsers();

    int insert(User user);
}
```


### 2.Plugin Implementation

HelloServiceImpl.java
```
package cherry.example.plugin;

public class HelloServiceImpl implements HelloService {

    @Override
    public String echo(String msg) {
        System.out.println("echo [" + msg + "]");
        return "echo [" + msg + "]";
    }

    @Override
    public void hello(String msg) {
        System.out.println("hello "+msg);
    }

    @Override
    public void init(PluginConfig config) {
        System.out.println("HelloServiceImpl init...");
    }

    @Override
    public void destroy() {
        System.out.println("HelloServiceImpl destroy...");
    }
}
```

UserServiceImpl.java
```
package cherry.example.plugin;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        User user = new User();
        user.setName(name);
        user.setPassword("root");
        user.setAge(25);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        for(int i=0; i<5; i++){
            list.add(new User("ricky_"+i, "root", 25+i));
        }
        return list;
    }

    @Override
    public int insert(User user) {
        return 1;
    }

    @Override
    public void init(PluginConfig config) {
        System.out.println("UserServiceImpl init...");
    }

    @Override
    public void destroy() {
        System.out.println("UserServiceImpl destroy...");
    }
}
```

### 3.Declare

plugins.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="false">

    <context-name>cherry-demo</context-name>

    <!--variables-->
    <property name="path" value="D:/logs/" />
    <property name="threshold" value="100" />

    <!--jars-->
    <lib dir="D:/plugins/lib1" regex="" />
    <lib dir="D:/plugins/lib2" regex="" />

    <context-param>
        <param-name>app-version</param-name>
        <param-value>v1.0</param-value>
    </context-param>

    <!--plugins-->
    <plugin>
        <plugin-name>helloService</plugin-name>
        <plugin-class>cherry.example.plugin.HelloServiceImpl</plugin-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
        <init-param>
            <param-name>path</param-name>
            <param-value>${path}</param-value>
        </init-param>
    </plugin>

    <plugin>
        <plugin-name>userService</plugin-name>
        <plugin-class>cherry.example.plugin.UserServiceImpl</plugin-class>
    </plugin>

</configuration>
```

App.java
```
package cherry.example.main;

import cherry.DefaultPluginFactory;
import cherry.PluginFactory;
import cherry.example.api.HelloService;
import cherry.example.api.UserService;
import cherry.example.api.model.User;

import java.io.File;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) {

        PluginFactory pluginFactory = new DefaultPluginFactory("classpath:plugins.xml");
        //PluginFactory pluginFactory = new DefaultPluginFactory(new File("/home/plugins.xml"));

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

        //关闭
        pluginFactory.close();
    }
}

```

完整代码: [cherry-example](https://github.com/TiFG/cherry/tree/master/cherry-example)