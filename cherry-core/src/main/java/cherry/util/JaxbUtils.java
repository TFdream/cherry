package cherry.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 */
public class JaxbUtils {

    public final static String CHARSET_NAME = "UTF-8";

    public static String marshal(Object obj) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.setProperty(Marshaller.JAXB_ENCODING, CHARSET_NAME);

        StringWriter writer = new StringWriter();
        try{
            jaxbMarshaller.marshal(obj, writer);
            return writer.toString();
        } finally {
            IoUtils.closeQuietly(writer);
        }
    }

    public static <T> T unmarshal(String xml, Class<T> cls) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = null;
        try{
            reader = new StringReader(xml);
            return (T) jaxbUnmarshaller.unmarshal(reader);
        } finally {
            IoUtils.closeQuietly(reader);
        }
    }

    public static <T> T unmarshal(File file, Class<T> cls) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (T) jaxbUnmarshaller.unmarshal(file);
    }

    public static <T> T unmarshal(InputStream in, Class<T> cls) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(cls);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (T) jaxbUnmarshaller.unmarshal(in);
    }
}
