package cherry.example.plugin;

import cherry.config.PluginConfig;
import cherry.example.api.HelloService;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
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
        System.out.println("HelloServiceImpl init...");
    }

    @Override
    public void destroy() {
        System.out.println("HelloServiceImpl destroy...");
    }
}
