package cherry.demo.api;

import cherry.Plugin;
import cherry.demo.api.model.User;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface UserService extends Plugin {

    User getUser(String name);

    List<User> getUsers();

    int insert(User user);
}
