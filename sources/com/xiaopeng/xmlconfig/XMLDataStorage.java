package com.xiaopeng.xmlconfig;

import android.content.Context;
import android.os.SystemProperties;
import com.xiaopeng.commonfunc.Constant;
import com.xiaopeng.libconfig.ipc.IpcConfig;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/* loaded from: classes5.dex */
public class XMLDataStorage {
    public static final String CLASS_NAME = "XMLDataStorage";
    private static final String DEFAULT_XML = "base_xiaopeng_xmlconfig.xml";
    private static final String SUFFIX_XML_FILE = "_xiaopeng_xmlconfig.xml";
    private static XMLDataStorage mInstance = null;
    private Context mContext;
    private DocumentBuilder mDOMParser;
    private Document mDocument;
    private XPath mXPath;
    private boolean mWasCompletedParsing = false;
    private ConcurrentHashMap<String, String> mAttrCache = new ConcurrentHashMap<>();

    public static synchronized XMLDataStorage instance() {
        XMLDataStorage xMLDataStorage;
        synchronized (XMLDataStorage.class) {
            if (mInstance == null) {
                mInstance = new XMLDataStorage();
            }
            xMLDataStorage = mInstance;
        }
        return xMLDataStorage;
    }

    private XMLDataStorage() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            this.mDOMParser = factory.newDocumentBuilder();
            this.mXPath = XPathFactory.newInstance().newXPath();
        } catch (ParserConfigurationException e) {
            XmlUtil.log_e(e);
        }
    }

    public boolean parseXML(Context context) {
        this.mContext = context;
        String packageName = context.getPackageName();
        if (packageName.equals(Constant.PACKAGE_NAME_FACTORYTEST)) {
            XmlUtil.TAG = "FactoryTest";
        } else if (packageName.equals("com.xiaopeng.devtools")) {
            XmlUtil.TAG = "DevTools";
        } else if (packageName.equals(IpcConfig.App.APP_CAR_DIAGNOSIS)) {
            XmlUtil.TAG = "CarDiagnosis";
        } else {
            XmlUtil.log_i(CLASS_NAME, "parsingXML", "UnKnown PackageName");
            return false;
        }
        synchronized (XMLDataStorage.class) {
            if (!instance().wasCompletedParsing()) {
                String modelXML = SystemProperties.get("ro.xpeng.xml.model", "unknown");
                String modelXML2 = modelXML.trim().replace(Constant.SPACE_STRING, "").toLowerCase(Locale.ENGLISH) + SUFFIX_XML_FILE;
                XmlUtil.log_d(CLASS_NAME, "parseXML", "modelXML=" + modelXML2);
                if (!instance().parseXmlFile(context, modelXML2)) {
                    XmlUtil.log_e(CLASS_NAME, "parseXML", "model file not found : " + modelXML2);
                    instance().parseXmlFile(context, DEFAULT_XML);
                    XmlUtil.log_e(CLASS_NAME, "parseXML", "Default file loaded => base_xiaopeng_xmlconfig.xml");
                }
                XmlUtil.log_i(CLASS_NAME, "parsingXML", "data parsing completed.");
            } else {
                XmlUtil.log_i(CLASS_NAME, "parsingXML", "data parsing was completed.");
            }
        }
        return true;
    }

    private boolean parseXmlFile(Context context, String xml) {
        InputStream inputstream = null;
        InputStream baseInputstream = null;
        try {
            try {
                try {
                    inputstream = new FileInputStream(new File("/system/etc/" + xml));
                    baseInputstream = new FileInputStream(new File("/system/etc/" + DEFAULT_XML));
                    XmlUtil.log_d(CLASS_NAME, "parseXmlFile", "parseXmlFile Is Started xml :" + xml);
                    Document document = this.mDOMParser.parse(inputstream);
                    XmlUtil.log_d(CLASS_NAME, "parseXmlFile", "Decode base file: base_xiaopeng_xmlconfig.xml");
                    Document baseDocument = this.mDOMParser.parse(baseInputstream);
                    this.mDocument = redefinitionXml(baseDocument, document);
                    XmlUtil.log_d(CLASS_NAME, "parseXmlFile", "parseXmlFile Is Completed");
                    this.mWasCompletedParsing = true;
                    inputstream.close();
                    baseInputstream.close();
                } catch (IOException e) {
                    XmlUtil.log_e(e);
                }
            } catch (Exception e2) {
                this.mWasCompletedParsing = false;
                XmlUtil.log_e(e2);
                if (inputstream != null) {
                    inputstream.close();
                }
                if (baseInputstream != null) {
                    baseInputstream.close();
                }
            }
            return this.mWasCompletedParsing;
        } catch (Throwable th) {
            if (inputstream != null) {
                try {
                    inputstream.close();
                } catch (IOException e3) {
                    XmlUtil.log_e(e3);
                    throw th;
                }
            }
            if (baseInputstream != null) {
                baseInputstream.close();
            }
            throw th;
        }
    }

    private Document redefinitionXml(Document base, Document sub) {
        redefinitionById(base, sub);
        return base;
    }

    public boolean wasCompletedParsing() {
        return this.mWasCompletedParsing;
    }

    public Element getElementById(String id) {
        return this.mDocument.getElementById(id);
    }

    public Element getElementByName(String nodeName) {
        return (Element) xpath("//" + nodeName, XPathConstants.NODE);
    }

    public Element getElementByAttribute(String attribute, String attributeValue) {
        return (Element) xpath("//*[@" + attribute + "='" + attributeValue + "']", XPathConstants.NODE);
    }

    public Element[] getElementSetByName(String nodeName) {
        NodeList nodeList = (NodeList) xpath("//" + nodeName, XPathConstants.NODESET);
        if (nodeList != null) {
            return makeElementArray(nodeList);
        }
        return null;
    }

    public Element[] getChildElementSet(String parentNodeName) {
        NodeList nodeList = (NodeList) xpath("//" + parentNodeName + "/*", XPathConstants.NODESET);
        if (nodeList != null) {
            return makeElementArray(nodeList);
        }
        return null;
    }

    public String getAttributeValueById(String id, String resultAttr) {
        String cacheId = id + "$" + resultAttr;
        String attr = this.mAttrCache.get(cacheId);
        if (attr == null) {
            String value = this.mDocument.getElementById(id).getAttribute(resultAttr);
            this.mAttrCache.putIfAbsent(cacheId, value);
            return value;
        }
        return attr;
    }

    public String getAttributeValueByTag(String tag, String resultAttr) {
        return ((Element) this.mDocument.getElementsByTagName(tag).item(0)).getAttribute(resultAttr);
    }

    public String getAttributeValueByAttribute(String finaAttr, String findAttrValue, String resultAttr) {
        Element element = getElementByAttribute(finaAttr, findAttrValue);
        if (element != null) {
            return element.getAttribute(resultAttr);
        }
        return null;
    }

    public String[] getAttributeNameSet(Element element) {
        String[] attributeSet = null;
        if (element.hasAttributes()) {
            NamedNodeMap map = element.getAttributes();
            attributeSet = new String[map.getLength()];
            for (int i = 0; i < map.getLength(); i++) {
                attributeSet[i] = map.item(i).getNodeName();
            }
        }
        return attributeSet;
    }

    public Document getDocument() {
        return this.mDocument;
    }

    private Object xpath(String expression, QName returnType) {
        return xpath(this.mDocument, expression, returnType);
    }

    private Object xpath(Document document, String expression, QName returnType) {
        try {
            Object result = this.mXPath.compile(expression).evaluate(document, returnType);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Element[] makeElementArray(NodeList nodeList) {
        Element[] elementSet = new Element[nodeList.getLength()];
        for (int i = 0; i < nodeList.getLength(); i++) {
            elementSet[i] = (Element) nodeList.item(i);
        }
        return elementSet;
    }

    private void redefinitionById(Document baseDocument, Document subDocument) {
        redefinitionById(baseDocument, subDocument.getDocumentElement());
    }

    private void redefinitionById(Document baseDocument, Element redefNode) {
        String id;
        if (redefNode.hasAttributes() && (id = redefNode.getAttribute("id")) != null && !id.isEmpty()) {
            XmlUtil.log_d(CLASS_NAME, "redefinitionById", "id=" + id);
            String[] attributes = getAttributeNameSet(redefNode);
            if (attributes != null) {
                for (String attr : attributes) {
                    if (!attr.equals("id")) {
                        Element element = baseDocument.getElementById(id);
                        if (element != null) {
                            element.setAttribute(attr, redefNode.getAttribute(attr));
                        } else {
                            XmlUtil.log_d(CLASS_NAME, "redefinitionById", "Element \"" + id + "\" does not exist in base document.");
                        }
                    }
                }
            }
        }
        if (redefNode.hasChildNodes()) {
            NodeList childNodes = redefNode.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeType() == 1) {
                    redefinitionById(baseDocument, (Element) childNodes.item(i));
                }
            }
        }
    }

    private void swapNode(Document targetDocument, Node baseNode, Node subNode) {
        baseNode.getParentNode().replaceChild(cloneNode(targetDocument, (Element) subNode), baseNode);
    }

    private Node cloneNode(Document document, Element cloneTarget) {
        String[] attributes;
        Element newNode = document.createElement(cloneTarget.getNodeName());
        if (cloneTarget.hasAttributes() && (attributes = getAttributeNameSet(cloneTarget)) != null) {
            for (String attr : attributes) {
                newNode.setAttribute(attr, cloneTarget.getAttribute(attr));
            }
        }
        NodeList list = cloneTarget.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == 1) {
                newNode.appendChild(cloneNode(document, (Element) list.item(i)));
            }
        }
        return newNode;
    }
}
