package cherry.exception;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class PluginException extends RuntimeException {

    public PluginException() {
        super();
    }

    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginException(Throwable cause) {
        super(cause);
    }

}
