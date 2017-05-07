package cherry.config;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class PluginDefinition {
    private String name;
    private String clazz;
    private List<Param> params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
    }
}
