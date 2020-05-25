package com.lz.xml;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    ArrayList<BookBean> beans;
    private final String TAG = "MainActivity";
    private BookBean bean;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                dom();
                // https://blog.csdn.net/qq_41703795/article/details/105740541 读写sdcard权限参考
                xmlDomParser();
            }
        }).start();
    }
    
    private void dom() {
        beans = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();//获得解析工厂
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();//创建builder对象
            InputStream is = getAssets().open("xml.xml");
            Document document = builder.parse(is);//这一步是把文件添加到内存中 从内存中拿更快
            //接下来跟document玩
            NodeList list = document.getElementsByTagName("book");//获取文件中所有节点名为book的内容，并打算将这些内容存入List中去
            for (int i = 0; i < list.getLength(); i++) {
                //这样就拿到了一个book对象 也就是一个对象的内容，包括五个属性
                BookBean bean = new BookBean();
                Node node = list.item(i);//当i等于0时，获得的是第一个book对象
                //获得属性
                String id = node.getAttributes().getNamedItem("id").getNodeValue();
                Log.i(TAG, "ID:" + id);
                //获取姓名
                String name = node.getAttributes().getNamedItem("name").getNodeValue();
                Log.e("name", name);
                String year = node.getAttributes().getNamedItem("year").getNodeValue();
                String author = node.getAttributes().getNamedItem("author").getNodeValue();
                String price = node.getAttributes().getNamedItem("price").getNodeValue();
                bean.setId(id);
                bean.setAuthor(author);
                bean.setName(name);
                bean.setPrice(price);
                bean.setYear(year);
                beans.add(bean);
            }
            Check();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Check() {
        for (BookBean bookBean : beans) {
            Log.i(TAG, bookBean.toString());
        }
    }
    
    private void xmlDomParser() {
        InputStream inputStream = null;
        try {
            //创建factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
            //创建 DocumentBuilder对象
            DocumentBuilder builder = factory.newDocumentBuilder();//factory可以用Xml类代替
            
            //获取文件
            AssetManager assetManager = getAssets();
            inputStream = assetManager.open("xml.xml");
            
            //创建Document对象，相当于代表xml表的对象
            Document document = builder.parse(inputStream);
            
            //获取根节点
            Element rootElement = document.getDocumentElement();
            
            //获取根节点所有student的节点
            NodeList parentNodeList = rootElement.getElementsByTagName("book");
            
            /**
             *  遍历所有的节点
             *
             */
            beans = new ArrayList<>();
            //先遍历sutdent节点数据
            for (int i = 0; i < parentNodeList.getLength(); i++) {
                
                //student节点
                Element parentelement = (Element) parentNodeList.item(i);
                
                //获取student节点内的所有节点
                NodeList childNodeList = parentelement.getChildNodes();
                
                //获取<Students>标签内的参数
                String id = parentelement.getAttribute("id");
                Log.i(TAG, "ID:" + id);
                bean = new BookBean();
                bean.setId(id);
                
                //遍历student节点内的节点
                for (int j = 0; j < childNodeList.getLength(); j++) {
                    //多标签加 Node.ELEMENT_NODE判断
                    if (childNodeList.item(j).getNodeType() == Node.ELEMENT_NODE) {
                        //依次赋值
                        if ("name".equals(childNodeList.item(j).getNodeName())) {//注意，这里不是：childNodeList.item(j).getFirstChild().getNodeName()
                            
                            String name = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "name =" + name);
                            bean.setName(name);
                            
                        } else if ("author".equals(childNodeList.item(j).getNodeName())) {
                            
                            String sex = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "sex =" + sex);
                            bean.setAuthor(sex);
                            
                        } else if ("year".equals(childNodeList.item(j).getNodeName())) {
                            
                            String age = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "age =" + age);
                            bean.setYear(age);
                            
                        } else if ("price".equals(childNodeList.item(j).getNodeName())) {
                            
                            String email = childNodeList.item(j).getFirstChild().getNodeValue();
                            Log.d(TAG, "email =" + email);
                            bean.setPrice(email);
                            
                        }
                    }
                }
                //添加到list中
                beans.add(bean);
            }
            Check();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
