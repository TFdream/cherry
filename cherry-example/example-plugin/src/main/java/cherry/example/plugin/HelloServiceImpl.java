package cherry.example.plugin;

import cherry.example.api.HelloService;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 16:13
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println("hello [" + msg + "]");
        return "hello [" + msg + "]";
    }
}
