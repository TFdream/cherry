package cherry.example.plugin;

import cherry.config.PluginConfig;
import cherry.example.api.UserService;
import cherry.example.api.model.User;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class UserServiceImpl implements UserService {

    @Override
    public User getUser(String name) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public void init(PluginConfig config) {

    }

    @Override
    public void destroy() {

    }
}
