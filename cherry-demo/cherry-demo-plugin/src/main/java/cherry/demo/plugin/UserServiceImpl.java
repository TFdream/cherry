package cherry.demo.plugin;

import cherry.config.PluginConfig;
import cherry.demo.api.UserService;
import cherry.demo.api.model.User;
import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class UserServiceImpl implements UserService {

    private PluginConfig config;

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
        this.config = config;
        System.out.println("UserServiceImpl init...");
    }

    @Override
    public void destroy() {
        System.out.println("UserServiceImpl destroy...");
    }
}
