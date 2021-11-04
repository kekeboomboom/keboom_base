package keboom.JAXBDemo;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlObjectConverter {

  public static String objectToXmlStr(Object o) {
    if (o == null) {
      return null;
    }
    try {
      Marshaller jaxbMarshaller= JAXBContext.newInstance(o.getClass()).createMarshaller();
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      StringWriter sw = new StringWriter();
      jaxbMarshaller.marshal(o, sw);
      return sw.toString();
    } catch (JAXBException e) {
//      log.error("object convert to xml failed!");
    }
    return null;
  }

  public static <T> T xmlStrToObject(String xmlStr,Class<T> clazz) {
//    if (StringUtils.isEmpty(xmlStr)){
//      return null;
//    }
    try {
      Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(clazz).createUnmarshaller();
      Object o = jaxbUnmarshaller.unmarshal(new StringReader(xmlStr));
      if (clazz.isInstance(o)){
        return clazz.cast(o);
      }
    } catch (JAXBException e) {
//      log.error("xml convert to object failed!");
    }
    return null;
  }
}
