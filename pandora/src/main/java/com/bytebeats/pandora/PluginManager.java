package com.bytebeats.pandora;

import com.bytebeats.pandora.annotation.Extension;
import com.bytebeats.pandora.util.StringUtils;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-11-12 14:35
 */
public class PluginManager<T> {
    private static final ConcurrentHashMap<Class<?>, PluginManager> pluginManagerMap = new ConcurrentHashMap<>();
    private Class<T> type;

    private PluginClassLoader pluginClassLoader;

    private PluginManager(Class<T> type){
        this.type = type;
    }

    public static <T> PluginManager<T> load(Class<T> type){
        if(!type.isAnnotationPresent(Extension.class)){
            throw new IllegalArgumentException("type:" + type.getName() +
                    ") is not a extension, because WITHOUT @Extension Annotation!");
        }

        PluginManager mgr =  pluginManagerMap.get(type);
        if(mgr==null){
            pluginManagerMap.putIfAbsent(type, new PluginManager(type));
            mgr =  pluginManagerMap.get(type);
        }

        return mgr;
    }

    public T getPlugin(String name){
        if (StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("Extension name == null");
        }

        return null;
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

    }

    private synchronized PluginClassLoader doInit(String basePath){
        PluginClassLoader pluginClassLoader = new PluginClassLoader(basePath);
        return pluginClassLoader;
    }
}
