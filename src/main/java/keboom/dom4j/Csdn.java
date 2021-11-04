package keboom.dom4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Csdn {

  /**
   * 可公用
   * 向xml的Document对象的根节点下追加子节点
   * @param doc xml Document对象
   * @param beanList 实体集合
   * @param pojo 实体Clss类
   */
  public static void addElementForRootNode(Document doc, List beanList, Class pojo) throws IllegalAccessException {

    //获取实体字段数组
    Field[] declaredFields = pojo.getDeclaredFields();
    //获取根节点
    Element ele = doc.getRootElement();
    //遍历数据
    for (Object o : beanList) {
      //建立子节点
      Element pojoNode = ele.addElement(pojo.getSimpleName());
      //遍历实体字段属性
      for (Field declaredField : declaredFields) {
        //开启暴力使用
        declaredField.setAccessible(true);
        //给xml节点属性赋值实体节点属性
        pojoNode.addAttribute(declaredField.getName(), declaredField.get(o)==null?"":declaredField.get(o).toString());
      }
    }
  }


  /**
   * 可公用
   * List<Objct>解析为xml 的Document对象
   * 通过数据集合与实体字段数组双重遍历,填充xml的节点与节点属性
   * 节点名称为实体类名,节点属性名称为实体字段名称
   *
   * 注意:该方法暂时只支持实体字段为基本类型或自动拆装箱的包装类
   *
   * @param beanList 数据集合
   * @param pojo 实体Class
   * @param rootNode 根节点名称
   * @return
   * @throws IllegalAccessException
   */
  public static Document fromBeanToXml(List beanList, Class pojo,String rootNode) throws IllegalAccessException {
    //创建文档
    Document doc = DocumentHelper.createDocument();
    //创建root节点
    doc.addElement(rootNode);
    //追加数据
    addElementForRootNode(doc,beanList,pojo);
    return  doc;

  }


  /**
   * 可公用
   * xml解析为对象
   * @param rootElt 根节点
   * @param pojo 实体Class
   * @return
   * @throws Exception
   */
  public static <T> T  fromXmlToBean(Element rootElt, Class<T> pojo) throws Exception
  {
    // 首先得到pojo所定义的字段
    Field[] fields = pojo.getDeclaredFields();
    // 根据传入的Class动态生成pojo对象
    T obj = pojo.newInstance();
    for (Field field : fields)
    {
      // 设置字段可访问（必须，否则报错）
      field.setAccessible(true);
      // 得到字段的属性名
      String name = field.getName();
      // 这一段的作用是如果字段在Element中不存在会抛出异常，如果出异常，则跳过。
      try
      {
        rootElt.attributeValue(name);
      }
      catch (Exception ex)
      {
        continue;
      }
      if (rootElt.attributeValue(name) != null && !"".equals(rootElt.attributeValue(name)))
      {
        // 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
        if (field.getType().equals(Long.class) || field.getType().equals(long.class))
        {
          field.set(obj, Long.parseLong(rootElt.attributeValue(name)));
        }
        else if (field.getType().equals(String.class))
        {
          field.set(obj, rootElt.attributeValue(name));
        }
        else if (field.getType().equals(Double.class) || field.getType().equals(double.class))
        {
          field.set(obj, Double.parseDouble(rootElt.attributeValue(name)));
        }
        else if (field.getType().equals(Integer.class) || field.getType().equals(int.class))
        {
          field.set(obj, Integer.parseInt(rootElt.attributeValue(name)));
        }
        else if (field.getType().equals(java.util.Date.class))
        {
          field.set(obj, Date.parse(rootElt.attributeValue(name)));
        }
        else
        {
          continue;
        }
      }
    }
    return obj;
  }


  public static void main(String[] args) throws Exception {
//    BlogBean bean1 = new BlogBean(1, "ke");
//    ArrayList<BlogBean> list = new ArrayList<BlogBean>();
//    list.add(bean1);
//    Document xml = Csdn.fromBeanToXml( list,BlogBean.class, "xmlx");
//    System.out.println(xml.asXML());


    String xmlStr = "<BlogBean id=\"1\" name=\"ke\"/>";
    //创建文档
    Document doc = DocumentHelper.createDocument();
    //创建root节点
    Element root = doc.addElement("BlogBean");
    BlogBean blogBean = Csdn.fromXmlToBean(root, BlogBean.class);
    System.out.println(blogBean);
  }
}
