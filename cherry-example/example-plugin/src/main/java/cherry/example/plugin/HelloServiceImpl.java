package cherry.example.plugin;

import cherry.config.PluginConfig;
import cherry.example.api.HelloService;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class HelloServiceImpl implements HelloService {
    private PluginConfig config;

    @Override
    public String echo(String msg) {
        System.out.println("echo [" + msg + "]");
        return "echo [" + msg + "]";
    }

    @Override
    public void hello(String msg) {
        System.out.println("hello "+msg);
        System.out.println("encoding="+config.getInitParameter("encoding"));
    }

    @Override
    public void init(PluginConfig config) {
        this.config = config;
        System.out.println("HelloServiceImpl init...");
    }

    @Override
    public void destroy() {
        System.out.println("HelloServiceImpl destroy...");
    }
}
