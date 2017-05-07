package cherry;

import cherry.config.AppConfig;
import cherry.config.Library;
import cherry.config.PluginDefinition;
import cherry.exception.PluginException;
import cherry.util.StringUtils;
import cherry.xml.ConfigParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 14:35
 */
public class DefaultPluginFactory implements PluginFactory {

    private PluginClassLoader classLoader;
    private AppConfig config;

    private Map<String, PluginDefinition> pluginDefinitionMap = new HashMap<>();

    private ConcurrentHashMap<String, Plugin> instanceMap = new ConcurrentHashMap<>();

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
    public Plugin getPlugin(String name) {

        PluginDefinition pd = pluginDefinitionMap.get(name);
        if(pd==null){
            throw  new PluginException("");
        }

        return null;
    }

    @Override
    public Plugin getPlugin(Class<? extends Plugin> type) {

        return null;
    }

    @Override
    public PluginDefinition getPluginDefinition(String name) {
        return pluginDefinitionMap.get(name);
    }

    @Override
    public List<String> getPluginNames() {
        return new ArrayList<>(pluginDefinitionMap.keySet());
    }

    @Override
    public boolean hasPlugin(String name) {
        return pluginDefinitionMap.containsKey(name);
    }

    @Override
    public void close() {

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
                if(pluginDefinitionMap.containsKey(pd.getName())){
                    throw new PluginException("Plugin "+pd.getName()+" already exists");
                }
                pluginDefinitionMap.put(pd.getName(), pd);
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
