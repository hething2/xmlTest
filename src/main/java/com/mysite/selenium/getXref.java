package com.mysite.selenium;

/**
 * Created by hethington on 3/30/2016.
 */
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

//import org.apache.commons.lang3.ArrayUtils;

public class getXref {

    public static void main(String argv[]) {


        try {


            File folder = new File("I:\\preview\\shared\\content\\english\\curriculum\\12575\\12575");
            //File folder = new File("I:\\preview\\shared\\content\\english\\magazines\\liahona\\2016\\13289_000");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    //System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    //System.out.println("Directory " + listOfFiles[i].getName());
                }
            }

            ArrayList<String> xRefList = new ArrayList<String>();
            ArrayList<String> xRefListHref = new ArrayList<String>();
            ArrayList<String> titleList = new ArrayList<String>();

            for (File v : listOfFiles) {
                File fPath = v;

                File fXmlFile = new File("" + fPath);
                //if(){}


                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());


                NodeList xref = doc.getElementsByTagName("span");
                NodeList aXRef = doc.getElementsByTagName("a");
                NodeList title = doc.getElementsByTagName("ldswebml");



                for (int temp = 0; temp < xref.getLength(); temp++) {

                    Node aNode = xref.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String isXRef = iElement.getAttribute("class");

                        //System.out.println(isXRef);
                        //System.out.println(isXRefHref);
                        if(isXRef.contains("crossRef")){
                            String isXRef1 = iElement.getTextContent();

                        xRefList.add(isXRef1);
                            System.out.println(isXRef1);
                            for (int t = 0; t < title.getLength(); t++) {

                                Node cNode = title.item(t);

                                //if (cNode.getNodeType() == cNode.ELEMENT_NODE) {

                                    Element icElement = (Element) cNode;
                                    //String uri = icElement.getAttribute("href");
                                    //System.out.println(imgSrc);
                                    titleList.add(((Element) cNode).getAttribute("href"));
                                    //System.out.println(titleList);
                                    //System.out.println(v.toString());
                                    String fileID = v.toString().substring(v.toString().lastIndexOf("\\") + 1);
                                    System.out.println(fileID);
                                    System.out.println("");







                            }
                        }
                                String isXRefHref = iElement.getAttribute("fileIDref");
                            //if(isXRef.contains("crossRef") & isXRefHref.contains("")){
                                if(isXRefHref.contains("_")){
                                String isXRef2 = iElement.getTextContent();
                                xRefListHref.add(isXRef2);
                                System.out.println(isXRefHref);

                    }
                    }
                }

                for (int temp = 0; temp < aXRef.getLength(); temp++) {

                    Node aNode = aXRef.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String isXRef = iElement.getAttribute("class");
                        //String isXRefHref = iElement.getAttribute("href");
                        //System.out.println(isXRef);
                        //System.out.println(isXRefHref);
                        if(isXRef.contains("crossRef")){
                            String isXRef1 = iElement.getTextContent();
                            xRefList.add(isXRef1);
                            System.out.println(isXRef1);

                            //for (int t = 0; t < title.getLength(); t++) {



                                if (aNode.getNodeType() == aNode.ELEMENT_NODE) {

                                    Element icElement = (Element) aNode;
                                    String uri = icElement.getAttribute("fileIDref");
                                    //System.out.println(imgSrc);
                                    titleList.add(uri);
                                    System.out.println(uri);

                                    //System.out.println(v.toString());
                                    String fileID = v.toString().substring(v.toString().lastIndexOf("\\") + 1);
                                    System.out.println(fileID);
                                    System.out.println("");


                                }


                        }

                        //if(isXRef.contains("crossRef") & isXRefHref.contains("")){
                        //if(isXRefHref.contains("")){
                          //  String isXRef2 = iElement.getTextContent();
                            //xRefListHref.add(isXRef2);
                            //System.out.println(xRefListHref);
                        //}
                    }
                }

                //for (int temp = 0; temp < title.getLength(); temp++) {

                    //Node aNode = title.item(temp);

                    //if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        //Element iElement = (Element) aNode;
                        //String uri = iElement.getAttribute("uri");
                        //System.out.println(imgSrc);
                        //titleList.add(uri);
                        //System.out.println(uri);


                    //}
                //}
           }

            System.out.println(xRefListHref.size());
            System.out.println(xRefList.size());
            //System.out.println(xRefList);
            //System.out.println(titleList);
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}