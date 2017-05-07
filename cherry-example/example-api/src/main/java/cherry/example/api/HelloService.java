package cherry.example.api;

import cherry.Plugin;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 15:30
 */
public interface HelloService extends Plugin {

    String echo(String msg);

    void hello(String msg);
}
