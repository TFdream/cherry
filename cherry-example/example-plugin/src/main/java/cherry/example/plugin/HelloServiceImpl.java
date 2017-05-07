package cherry.example.plugin;

import cherry.config.PluginConfig;
import cherry.example.api.HelloService;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 16:13
 */
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

    }

    @Override
    public void destroy() {

    }
}
