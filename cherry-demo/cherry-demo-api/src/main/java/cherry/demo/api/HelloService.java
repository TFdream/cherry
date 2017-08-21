package cherry.demo.api;

import cherry.Plugin;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public interface HelloService extends Plugin {

    String echo(String msg);

    void hello(String msg);
}
