package cherry.config;

import cherry.PluginContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class DefaultPluginConfig implements PluginConfig {
    private String pluginName;
    private Map<String, String> params;

    public DefaultPluginConfig(PluginDefinition pd) {
        this.pluginName = pd.getName();
        this.params = getInitParams(pd.getParams());
    }

    @Override
    public String getPluginName() {
        return pluginName;
    }

    @Override
    public PluginContext getPluginContext() {
        return null;
    }

    @Override
    public String getInitParameter(String name) {
        return params.get(name);
    }

    @Override
    public Set<String> getInitParameterNames() {
        return params.keySet();
    }

    private Map<String,String> getInitParams(List<Param> list) {
        Map<String, String> params = new HashMap<>(8);
        if(list!=null && list.size()>0){
            for (Param param : list){
                params.put(param.getName(), param.getValue());
            }
        }
        return params;
    }

}
