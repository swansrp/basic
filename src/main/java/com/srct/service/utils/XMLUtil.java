/**  
 * Project Name:SpringBootCommon  
 * File Name:XMLUtil.java  
 * Package Name:com.srct.service.utils  
 * Date:May 4, 2018 5:54:10 PM  
 * Copyright (c) 2018, ruopeng.sha All Rights Reserved.  
 *  
*/
package com.srct.service.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * ClassName:XMLUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: May 4, 2018 5:54:10 PM <br/>
 * 
 * @author ruopeng.sha
 * @version
 * @since JDK 1.8
 * @see
 */
public class XMLUtil {

    private static final Logger logger = LogManager.getLogger(XMLUtil.class);

    private static final XmlMapper xmlMapper = new XmlMapper();

    private XMLUtil() {
    }

    /**
     * 输出全部属性 如果xml中存在，对象中没有，则自动忽略该属性 失败返回null
     * 
     * @param xmlContent
     * @param clazz
     * @return
     */
    public static <T> T toNormalObject(String xmlContent, Class<T> clazz) {
        return xmlToObject(xmlContent, clazz);
    }

    /**
     * 输出全部属性 如果xml中存在，对象中没有，则自动忽略该属性 失败返回null
     * 
     * @param inputStream
     * @param clazz
     * @return
     */
    public static <T> T toNormalObject(byte[] bytes, Class<T> clazz) {
        return xmlToObject(bytes, clazz);
    }

    /**
     * 输出全部属性 失败返回""
     * 
     * @param object
     * @return
     */
    public synchronized static byte[] toNormalXml(Object object) {
        return objectToXml(Include.ALWAYS, object);
    }

    private static <T> T xmlToObject(byte[] bytes, Class<T> clazz) {
        try {
            return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(bytes, clazz);
        } catch (Exception e) {
            logger.info("XmlToObject failed:", e);
        }
        return null;
    }

    private static <T> T xmlToObject(String xmlContent, Class<T> clazz) {
        try {
            return xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES).readValue(xmlContent, clazz);
        } catch (Exception e) {
            logger.info("XmlToObject failed:", e);
        }
        return null;
    }

    private static <T> byte[] objectToXml(Include include, T object) {
        try {
            return xmlMapper.setSerializationInclusion(include).writerWithDefaultPrettyPrinter()
                    .writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.info("ObjToXml failed:", e);
        }
        return null;
    }

    /**
     * @param xmlInputStream
     * @param cls
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T xmlToBean(InputStream xmlInputStream, Class<T> cls) throws IOException {
        T obj = xmlMapper.readValue(xmlInputStream, cls);
        return obj;
    }

    // dom Get Element Functions
    /**
     * xml string to org.w3c.dom.Document.
     * 
     * @param xml
     *            xml string
     * @return org.dom4j.Document Object
     */
    public static Document strToW3cDoc(String xml) {
        DocumentBuilder builder;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException e) {
        } catch (SAXException e) {
        } catch (IOException e) {
        }
        return null;
    }

    /**
     * Get org.w3c.dom.Document unique node text value
     * 
     * @param doc
     *            org.w3c.dom.Document Object
     * @param nodeName
     *            Node name(unique node)
     * @return
     */
    public static String getNodeText(org.w3c.dom.Document doc, String nodeName) {
        return getNodeText(doc, nodeName, 0);
    }

    /**
     * Get org.w3c.dom.Document specific Node Text Value
     * 
     * @param doc
     *            org.w3c.dom.Document Object
     * @param nodeName
     *            Node Name
     * @param index
     *            Array index
     * @return
     */
    public static String getNodeText(org.w3c.dom.Document doc, String nodeName, int index) {
        NodeList node = doc.getElementsByTagName(nodeName);
        if (node.getLength() > index && node.item(index) != null) {
            return node.item(index).getTextContent();
        }
        return null;
    }

    /**
     * Get Specific org.w3c.dom.Node's Child Node Text Value
     * 
     * @param node
     *            org.w3c.dom.Node
     * @param nodeName
     *            Node Name
     * @return
     */
    public static String getNodeText(org.w3c.dom.Node node, String nodeName) {
        org.w3c.dom.Node childNode = getChildNode(node, nodeName);
        if (childNode != null) {
            return childNode.getTextContent();
        }
        return null;
    }

    /**
     * Get Specific org.w3c.dom.Node's ClildNode
     * 
     * @param node
     *            org.w3c.dom.Node
     * @param nodeName
     *            Node Name
     * @return
     */
    public static org.w3c.dom.Node getChildNode(org.w3c.dom.Node node, String nodeName) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeName().equals(nodeName)) {
                return list.item(i);
            }
        }
        return null;
    }
}
