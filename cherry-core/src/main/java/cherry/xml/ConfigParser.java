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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class ConfigParser {

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

        List<?> list = document.selectNodes( "//configuration/property" );
        if(list!=null && list.size()>0){
            List<PropertiesPlaceholder> placeholders = new ArrayList<>(list.size());
            for (Iterator<?> iter = list.iterator(); iter.hasNext(); ) {
                Element e = (Element) iter.next();

                PropertiesPlaceholder p = new PropertiesPlaceholder();
                p.setName(e.attributeValue("name"));
                p.setValue(e.attributeValue("value"));

                placeholders.add(p);
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

                Param param = parseParam(e);

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
                    Param param = parseParam(foo);

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

    private Param parseParam(Element foo){
        Param param = new Param();
        param.setName(foo.elementText("param-name"));
        param.setValue(foo.elementText("param-value"));
        return param;
    }

    private Document getDocument(InputStream in) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(in);
        return document;
    }
}
