package cherry;

import cherry.config.*;
import cherry.exception.PluginException;
import cherry.util.StringUtils;
import cherry.xml.ConfigParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class DefaultPluginFactory implements PluginFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PluginClassLoader classLoader;
    private AppConfig config;

    private Map<String, PluginDefinition> pluginNameMap = new HashMap<>();    //key为plugin-name

    private ConcurrentHashMap<String, Object> pluginInstanceMap = new ConcurrentHashMap<>();

    public DefaultPluginFactory(String path){
        if(StringUtils.isEmpty(path)){
            throw new IllegalArgumentException("path can not be empty!");
        }
        if(path.startsWith("classpath:")){
            path = path.substring(10);
        }
        this.config = new ConfigParser().parse(path);
        init(this.config);
    }
    public DefaultPluginFactory(File file){
        this.config = new ConfigParser().parse(file);
        init(this.config);
    }

    @Override
    public Plugin getPlugin(String name) throws PluginException{

        PluginDefinition pd = pluginNameMap.get(name);
        if(pd==null){
            throw new PluginException("not found plugin:"+name+" definition");
        }
        return (Plugin) getPlugin(name, pd);
    }

    @Override
    public <T extends Plugin> T getPlugin(Class<T> type) throws PluginException {

        PluginDefinition pd = findCandidatePluginDefinition(type);
        if(pd==null){
            throw new PluginException("not found plugin:"+type.getName()+" definition");
        }
        return (T) getPlugin(type.getName(), pd);
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
        return pluginNameMap.containsKey(name);
    }

    @Override
    public void close() {

    }

    private Object getPlugin(String name, PluginDefinition pd){
        Object plugin = pluginInstanceMap.get(name);
        if(plugin==null){
            synchronized (this){
                logger.info("create plugin instance name:{}, class:{}", name, pd.getClazz());
                plugin = createPluginInstance(pd);
                Object old = pluginInstanceMap.putIfAbsent(name, plugin);
                if(old!=null){
                    plugin = old;
                }
            }
        }
        return plugin;
    }

    private Object createPluginInstance(PluginDefinition pd) throws PluginException{
        try {
            Class<?> clazz = classLoader.loadClass(pd.getClazz());
            Object plugin = clazz.newInstance();

            //执行初始化方法
            Method method = clazz.getMethod("init", PluginConfig.class);

            PluginConfig config = new DefaultPluginConfig(pd);
            method.invoke(plugin, config);

            return plugin;
        } catch (ClassNotFoundException e) {
            throw new PluginException("not found plugin class:"+pd.getClazz(), e);
        } catch (InstantiationException e) {
            throw new PluginException("construct plugin instance error", e);
        } catch (IllegalAccessException e) {
            throw new PluginException("construct plugin instance error", e);
        } catch (NoSuchMethodException e) {
            throw new PluginException("invoke plugin init method error", e);
        } catch (InvocationTargetException e) {
            throw new PluginException("invoke plugin init method error", e);
        }
    }

    private PluginDefinition findCandidatePluginDefinition(Class<? extends Plugin> type) {

        List<PluginDefinition> pds = config.getPlugins();
        if(pds!=null && pds.size()>0) {
            for (PluginDefinition pd : pds) {
                try {
                    Class clazz = classLoader.loadClass(pd.getClazz());
                    if(type.isAssignableFrom(clazz)){
                        return pd;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void init(AppConfig config) {

        //加载libs
        List<Library> libs = config.getLibs();
        if(libs!=null && libs.size()>0){
            for (Library lib : libs){
                addExternalJar(lib.getDir());
            }
        }

        List<PluginDefinition> pds = config.getPlugins();
        if(pds!=null && pds.size()>0){
            for (PluginDefinition pd : pds) {
                if(pluginNameMap.containsKey(pd.getName())){
                    throw new PluginException("Plugin name:"+pd.getName()+" already exists");
                }
                pluginNameMap.put(pd.getName(), pd);
            }
        }
    }

    private void addExternalJar(String basePath){
        if (StringUtils.isEmpty(basePath)) {
            throw new IllegalArgumentException("basePath can not be empty!");
        }
        File dir = new File(basePath);
        if(!dir.exists()){
            throw new IllegalArgumentException("basePath not exists:"+basePath);
        }
        if(!dir.isDirectory()){
            throw new IllegalArgumentException("basePath must be a directory:"+basePath);
        }
        if(classLoader==null){
            classLoader = new PluginClassLoader(basePath);
        } else {
            classLoader.addToClassLoader(basePath, null, false);
        }
    }

}
