package cherry.example.api;

import cherry.Plugin;
import cherry.example.api.model.User;
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
