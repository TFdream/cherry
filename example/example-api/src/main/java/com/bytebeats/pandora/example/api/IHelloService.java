package com.bytebeats.pandora.example.api;

import com.bytebeats.pandora.annotation.Extension;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 15:30
 */
@Extension(module = "hello", path = "D:\\test", name = "")
public interface IHelloService {

    String hello(String msg);
}
