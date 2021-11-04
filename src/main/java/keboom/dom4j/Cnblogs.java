package keboom.dom4j;

import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Cnblogs {

  public static void main(String[] args) {
    String strXML = "<Notification xmlns=\"http://mns.aliyuncs.com/doc/v1/\"> <TopicOwner>1692545896541241</TopicOwner> <TopicName>MyTopic</TopicName> <Subscriber>1692545896541241</Subscriber> <SubscriptionName>bing-test3</SubscriptionName> <MessageId>C39FB8C345BBFBA8-1-1687F6FAADD-200000015</MessageId> <MessageMD5>CAA1E9F5E9F854ACD8297B100BF8CCF9</MessageMD5> <Message>{\"jobId\":\"2384a4d89b1d4f1e869559e2ff8c9fad\",\"requestId\":\"639D1D03-1557-4AD7-9AD7-691F02834516\",\"Type\":\"Transcode\",\"state\":\"Success\",\"type\":\"Transcode\",\"State\":\"Success\",\"JobId\":\"2384a4d89b1d4f1e869559e2ff8c9fad\",\"RequestId\":\"639D1D03-1557-4AD7-9AD7-691F02834516\"}</Message> <PublishTime>1548326251229</PublishTime> </Notification>";

    Document doc = null;
    try {
      doc = DocumentHelper.parseText(strXML);
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    Element root = doc.getRootElement();// 指向根节点
    System.out.println(root.getName()+"---root");

    Iterator it = root.elementIterator();
    while (it.hasNext()) {
      Element element = (Element) it.next();// 一个Item节点
      System.out.println(element.getName() + " : " + element.getTextTrim());
    }
  }

}
