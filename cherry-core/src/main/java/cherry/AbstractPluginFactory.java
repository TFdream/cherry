package cherry;

import cherry.config.*;
import cherry.exception.PluginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public abstract class AbstractPluginFactory implements PluginFactory {

    protected final Map<String, PluginDefinition> pluginNameMap = new HashMap<>();    //key为plugin-name

    protected final ConcurrentHashMap<String, Plugin> pluginInstanceMap = new ConcurrentHashMap<>();

    @Override
    public Plugin getPlugin(String name) throws PluginException {

        Plugin plugin = pluginInstanceMap.get(name);
        if(plugin==null){
            PluginDefinition pd = getPluginDefinition(name);
            if(pd==null){
                throw new PluginException("not found plugin:"+name+" definition");
            }
            plugin = getPlugin(name, pd);
        }
        return plugin;
    }

    @Override
    public <T extends Plugin> T getPlugin(Class<T> type) throws PluginException {
        String name = type.getName();
        Plugin plugin = pluginInstanceMap.get(name);
        if(plugin==null){
            PluginDefinition pd = findCandidatePluginDefinition(type);
            if(pd==null){
                throw new PluginException("not found plugin:"+type.getName()+" definition");
            }
            plugin = getPlugin(name, pd);
        }
        return (T) plugin;
    }

    @Override
    public PluginDefinition getPluginDefinition(String name) {
        return pluginNameMap.get(name);
    }

    @Override
    public List<String> getPluginNames() {
        return new ArrayList<>(pluginNameMap.keySet());
    }

    @Override
    public boolean hasPlugin(String name) {
        return getPluginDefinition(name)!=null;
    }

    private Plugin getPlugin(String name, PluginDefinition pd) {
        Plugin plugin = pluginInstanceMap.get(name);
        if(plugin==null){
            synchronized (this){
                plugin = createPluginInstance(pd);
                invokePluginInitMethod(plugin, new DefaultPluginConfig(pd, getPluginContext()));
                Plugin old = pluginInstanceMap.putIfAbsent(name, plugin);
                if(old!=null){
                    plugin = old;
                }
            }
        }
        return plugin;
    }

    private PluginDefinition findCandidatePluginDefinition(Class<? extends Plugin> type) {

        List<PluginDefinition> pds = getAppConfig().getPlugins();
        if(pds!=null && pds.size()>0) {
            for (PluginDefinition pd : pds) {
                try {
                    Class clazz = loadPluginClass(pd.getClazz());
                    if(type.isAssignableFrom(clazz)){
                        return pd;
                    }
                } catch (ClassNotFoundException e) {
                    throw new PluginException("not found plugin class:"+pd.getClazz(), e);
                }
            }
        }
        return null;
    }

    private Plugin createPluginInstance(PluginDefinition pd) throws PluginException {
        try {
            Class<?> clazz = loadPluginClass(pd.getClazz());
            return (Plugin) clazz.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PluginException("not found plugin class:"+pd.getClazz(), e);
        } catch (InstantiationException e) {
            throw new PluginException("construct plugin instance error", e);
        } catch (IllegalAccessException e) {
            throw new PluginException("construct plugin instance error", e);
        }
    }

    private void invokePluginInitMethod(Plugin plugin, PluginConfig config) throws PluginException {
        try {
            //执行初始化方法
            plugin.init(config);
        } catch (Exception e) {
            throw new PluginException("invoke plugin init error", e);
        }
    }

    protected abstract AppConfig getAppConfig();

    protected abstract Class<?> loadPluginClass(String clazz) throws ClassNotFoundException;

    protected abstract PluginContext getPluginContext();
}
