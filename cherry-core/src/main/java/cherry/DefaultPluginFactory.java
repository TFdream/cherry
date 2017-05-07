package cherry;

import cherry.config.AppConfig;
import cherry.config.PluginDefinition;
import cherry.util.StringUtils;
import cherry.xml.ConfigParser;

import java.io.File;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 14:35
 */
public class DefaultPluginFactory implements PluginFactory {

    private PluginClassLoader classLoader;
    private AppConfig config;

    public DefaultPluginFactory(String path){
        this.config = new ConfigParser().parse(path);
    }
    public DefaultPluginFactory(File file){
        this.config = new ConfigParser().parse(file);
    }

    @Override
    public Plugin getPlugin(String name) {
        return null;
    }

    @Override
    public PluginDefinition getPluginDefinition(String name) {
        return null;
    }

    @Override
    public List<String> getPluginNames() {
        return null;
    }

    @Override
    public boolean hasPlugin(String name) {
        return false;
    }

    @Override
    public void close() {

    }

    void addExternalJar(String basePath){
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
        classLoader = new PluginClassLoader(basePath);
    }

}
