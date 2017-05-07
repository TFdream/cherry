package cherry.xml;

import cherry.config.*;
import cherry.util.IoUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ConfigParser {

    private static final String PLACEHOLDER_PREFIX = "${";
    private static final String PLACEHOLDER_SUFFIX = "}";


    public AppConfig parse(File file) {
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            return parse(in);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("", e);
        } finally {
            IoUtils.closeQuietly(in);
        }
    }

    public AppConfig parse(String path) {
        InputStream in = null;
        try{
            in = ConfigParser.class.getClassLoader().getResourceAsStream(path);
            return parse(in);
        } finally {
            IoUtils.closeQuietly(in);
        }
    }

    public AppConfig parse(InputStream in) {
        AppConfig config = null;
        try {
            config = parseConfig(getDocument(in));
        } catch (Exception e) {
            throw new RuntimeException("解析xml出错", e);
        }
        return config;
    }

    private AppConfig parseConfig(Document document) {

        AppConfig config = new AppConfig();

        Node node = document.selectSingleNode( "//configuration/context-name" );
        config.setContextName(node.getText());

        //全局变量
        Map<String, String> props = new HashMap<>();
        List<?> list = document.selectNodes( "//configuration/property" );
        if(list!=null && list.size()>0){
            List<PropertiesPlaceholder> placeholders = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                PropertiesPlaceholder pp = new PropertiesPlaceholder();
                pp.setName(e.attributeValue("name"));
                pp.setValue(e.attributeValue("value"));

                placeholders.add(pp);
                props.put(pp.getName(), pp.getValue());
            }
            config.setPlaceholders(placeholders);
        }

        //lib
        list = document.selectNodes( "//configuration/lib" );
        if(list!=null && list.size()>0){
            List<Library> libs = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                Library lib = new Library();
                lib.setDir(e.attributeValue("dir"));
                lib.setRegex(e.attributeValue("regex"));

                libs.add(lib);
            }
            config.setLibs(libs);
        }

        //context-param
        list = document.selectNodes( "//configuration/context-param" );
        if(list!=null && list.size()>0){
            List<Param> contextParams = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                Param param = parseInitParam(e, props);

                contextParams.add(param);
            }
            config.setContextParams(contextParams);
        }

        //plugin
        list = document.selectNodes( "//configuration/plugin" );
        if(list!=null && list.size()>0){
            List<PluginDefinition> plugins = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                PluginDefinition pd = new PluginDefinition();
                pd.setName(e.elementText("plugin-name"));
                pd.setClazz(e.elementText("plugin-class"));

                List<Param> initParams = new ArrayList<>(4);
                Iterator <?> it = e.elementIterator("init-param");
                while (it!=null && it.hasNext()){

                    Element foo = (Element) it.next();
                    Param param = parseInitParam(foo, props);

                    initParams.add(param);
                }
                pd.setParams(initParams);
                plugins.add(pd);
            }
            config.setPlugins(plugins);
        }

        //listener
        list = document.selectNodes( "//configuration/listener" );
        if(list!=null && list.size()>0){
            List<ListenerDefinition> listeners = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                ListenerDefinition ld = new ListenerDefinition();
                ld.setClazz(e.elementText("listener-class"));

                listeners.add(ld);
            }
            config.setListeners(listeners);
        }

        return config;
    }

    private Param parseInitParam(Element foo, Map<String, String> props){
        Param param = new Param();
        param.setName(foo.elementText("param-name"));

        String value = foo.elementText("param-value");
        if(value!=null && value.startsWith(PLACEHOLDER_PREFIX) && value.endsWith(PLACEHOLDER_SUFFIX)){
            String key = value.substring(2, value.length()-1);
            if(!props.containsKey(key)){
                throw new IllegalArgumentException("unknown param:"+param.getName()+" value:"+value);
            }
            value = props.get(key);
        }
        param.setValue(value);
        return param;
    }

    private Document getDocument(InputStream in) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        return document;
    }
}
