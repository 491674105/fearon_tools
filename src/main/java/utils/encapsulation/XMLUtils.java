package utils.encapsulation;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @description:
 * @author: Fearon
 * @create: 2018/7/25 14:28
 **/
public class XMLUtils {
    /**
     * 以XML形式组装参数
     */
    public static String getRequestXml(final Map<String, String> parameters) {
        Set<String> keySet = parameters.keySet();
        String[] map_key = new String[parameters.size()];
        // 进行字典排序
        map_key = keySet.toArray(map_key);
        Arrays.sort(map_key);

        StringBuilder xml_param = new StringBuilder();
        xml_param.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
        xml_param.append("<xml>");
        int i = 0, length = map_key.length;
        String current_key, value;
        for (; i < length; i++) {
            current_key = map_key[i];
            value = parameters.get(current_key);

            if(null != value && value.length() > 0) {
                xml_param.append("<");
                xml_param.append(current_key);
                xml_param.append(">");
                xml_param.append(value);
                xml_param.append("</");
                xml_param.append(current_key);
                xml_param.append(">");
            }
        }
        xml_param.append("</xml>");

        return xml_param.toString();
    }

    /**
     * xml解析
     */
    public static Map<String, String> parseResponseXML(final String res_xml) {
        if (null == res_xml || res_xml.length() == 0) {
            return null;
        }

        String response = res_xml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

        Map<String, String> result = null;

        InputStream reader = null;
        try {
            reader = new ByteArrayInputStream(response.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(reader);
            Element root = doc.getRootElement();
            List children = root.getChildren();// 子集
            List grandson;// 孙集
            Element element;
            String key, value;
            result = new HashMap<>();
            for (Object child : children) {
                element = (Element) child;
                key = element.getName();
                grandson = element.getChildren();
                if (grandson.isEmpty()) {
                    value = element.getTextNormalize();
                } else {
                    value = getChildrenXML(grandson);
                }

                result.put(key, value);
            }
        } catch (JDOMException | IOException e) {
            System.err.println("服务器数据异常！");
        } finally {
            //关闭流
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println("数据流关闭异常！");
                }
            }
        }

        return result;
    }

    /**
     * 解析XML子集
     */
    private static String getChildrenXML(final List children) {
        StringBuilder child_value = new StringBuilder();
        if (!children.isEmpty()) {
            Element element;
            String name, value;
            List grandson;
            for (Object child : children) {
                element = (Element) child;
                name = element.getName();
                value = element.getTextNormalize();
                grandson = element.getChildren();
                child_value.append("<");
                child_value.append(name);
                child_value.append(">");
                if (!grandson.isEmpty()) {
                    child_value.append(getChildrenXML(grandson));
                }
                child_value.append(value);
                child_value.append("</");
                child_value.append(name);
                child_value.append(">");
            }
        }

        return child_value.toString();
    }

    public static void main(String[] args) {
        Map<String, String> source = new HashMap<>();
        source.put("name", "fearon");
        source.put("age", "24");
        source.put("sex", "male");

        System.out.println(getRequestXml(source));
    }
}
