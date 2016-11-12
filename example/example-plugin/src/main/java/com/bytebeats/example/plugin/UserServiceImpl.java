package com.bytebeats.example.plugin;

import com.bytebeats.switcher.example.api.IUserService;
import com.bytebeats.switcher.example.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 16:14
 */
public class UserServiceImpl implements IUserService {

    @Override
    public List<User> getUsers() {
        List<User> list = new ArrayList<>();
        list.add(new User("ricky", "12345"));
        list.add(new User("kobe", "aaa"));
        list.add(new User("jordan", "root"));
        return list;
    }

    @Override
    public int update(User user) {
        System.out.println("update user = [" + user + "]");
        user.setPassword("hello");
        return 1;
    }
}
