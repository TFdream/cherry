package com.bytebeats.switcher.example.api;

import com.bytebeats.switcher.example.domain.User;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 16:09
 */
public interface IUserService {

    List<User> getUsers();

    int update(User user);

}
