package com.bytebeats.example.plugin;

import com.bytebeats.pandora.example.api.IHelloService;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 16:13
 */
public class HelloServiceImpl implements IHelloService {

    @Override
    public String hello(String msg) {
        System.out.println("hello [" + msg + "]");
        return "hello [" + msg + "]";
    }
}
