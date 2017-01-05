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
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class webmlRead {

    public static void main(String argv[]) throws ConnectTimeoutException {


        try {


            //File folder = new File("I:\\preview\\shared\\content\\english\\magazines\\ensign\\2016\\13189_000");
            File folder = new File("I:\\preview\\shared\\content\\english\\conference\\PD60001311");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    //System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    //System.out.println("Directory " + listOfFiles[i].getName());
                }
            }

            for (File v : listOfFiles) {
                File fPath = v;

                File fXmlFile = new File("" + fPath);
            //if(){}


            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());



            NodeList nList = doc.getElementsByTagName("block");
            NodeList aList = doc.getElementsByTagName("a");
            NodeList imgList = doc.getElementsByTagName("img");
            NodeList imgListShot = doc.getElementsByTagName("image");
            NodeList title = doc.getElementsByTagName("ldswebml");
            NodeList noteMarker = doc.getElementsByTagName("sup");
                NodeList vid = doc.getElementsByTagName("video");
                NodeList xref = doc.getElementsByTagName("span");


            ArrayList<String> paraID = new ArrayList<String>();
            ArrayList<String> paraContent = new ArrayList<String>();
            ArrayList<String> noteMarkerRef = new ArrayList<String>();
            ArrayList<String> footnoteRef = new ArrayList<String>();
            ArrayList<String> linkHref = new ArrayList<String>();
            ArrayList<String> imgLink = new ArrayList<String>();
            ArrayList<String> imgHeadshot = new ArrayList<String>();
            ArrayList<String> titleList = new ArrayList<String>();
                ArrayList<String> video = new ArrayList<String>();
                ArrayList<String> mediaID = new ArrayList<String>();
                ArrayList<String> xRefList = new ArrayList<String>();

                for (int temp = 0; temp < xref.getLength(); temp++) {

                    Node aNode = xref.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String isXRef = iElement.getTextContent();

                        xRefList.add(isXRef);
                    }
                }


            for (int temp = 0; temp < imgList.getLength(); temp++) {

                Node aNode = imgList.item(temp);

                if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element iElement = (Element) aNode;

                    String imgSrc = iElement.getAttribute("src");

                        imgLink.add(imgSrc);
                }
            }

                for (int temp = 0; temp < imgList.getLength(); temp++) {

                    Node aNode = imgList.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String imgID = iElement.getAttribute("mediaID");

                        mediaID.add(imgID);
                    }
                }

                for (int temp = 0; temp < vid.getLength(); temp++) {

                    Node aNode = vid.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String vidSrc = iElement.getAttribute("src");

                        video.add(vidSrc);
                    }
                }



            for (int temp = 0; temp < noteMarker.getLength(); temp++) {

                Node aNode = noteMarker.item(temp);



                if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element iElement = (Element) aNode;

                    String marker = iElement.getAttribute("noteRef");

                    noteMarkerRef.add(marker);
                }
            }

                for (int temp = 0; temp < imgListShot.getLength(); temp++) {

                    Node bNode = imgListShot.item(temp);

                if (bNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element iElement = (Element) bNode;
                    String imgSrc = iElement.getAttribute("uri");

                        imgHeadshot.add(imgSrc);



                }
            }

            for (int temp = 0; temp < aList.getLength(); temp++) {

                Node aNode = aList.item(temp);

                if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element aElement = (Element) aNode;
                    String aHref = aElement.getAttribute("href");
                    if(aHref.contains("/")){
                        linkHref.add(aHref);
                    }


                }
            }

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                        //System.out.println(eElement);
                    String blockType = eElement.getAttribute("type");
                    String footType = eElement.getAttribute("ref");
                    String blockText = eElement.getTextContent();
                    if (blockType.contains("para")) {
                        paraID.add(blockType);
                        paraContent.add(blockText);
                    }
                    if(blockType.contains("footnote")){
                        footnoteRef.add(footType);
                    }

                }
            }

            for (int temp = 0; temp < title.getLength(); temp++) {

                Node aNode = title.item(temp);

                if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element iElement = (Element) aNode;
                    String uri = iElement.getAttribute("uri");
                    //System.out.println(imgSrc);
                    titleList.add(uri);
                    //System.out.println(uri);


                }
            }
            for (String p : paraContent){
            //System.out.println(p+ "\n");
            }
            //System.out.println(paraID.size());
            //System.out.println(footnoteRef);

            for (String h : linkHref){
                //System.out.println(h);
                if(!h.contains("http://")) {
                    h = "http://preview.lds.org" + h + "?lang=eng";
                    //System.out.println(h);
                }
               // System.out.println(h);
                    String href = h;
                //if(h.contains("store.lds.org")){

                //}else{

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(5000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        //System.out.println("Request URL ... " + url);

                        boolean redirect = false;
                        int redirectCount = 0;

                        // normally, 3xx is redirect
                        int status = conn.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK) {
                            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                    || status == HttpURLConnection.HTTP_MOVED_PERM
                                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                                redirect = true;

                        }

                       // System.out.println("Response Code ... " + status);

                        if (redirect) {
                            for (int x = 1; x > 5; x++){
                                if (x > 5){}
                                else{

                            }
                            // get redirect url from "location" header field
                            String newUrl = conn.getHeaderField("Location");

                            // get the cookie if need, for login
                            String cookies = conn.getHeaderField("Set-Cookie");

                            // open the new connnection again
                            conn = (HttpURLConnection) new URL(newUrl).openConnection();
                           // conn.setRequestProperty("Cookie", cookies);
                            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                            conn.addRequestProperty("User-Agent", "Mozilla");
                            conn.addRequestProperty("Referer", "google.com");
                            int statusRedirect = conn.getResponseCode();


                            //System.out.println("Redirect to URL : " + newUrl);
                            //System.out.println("Response Code ... " + statusRedirect);

                            URI redirectUri = new URI(newUrl);
                            String redirectUriPath = redirectUri.getPath();
                        }}



                    } catch (Exception e){
                            e.printStackTrace();
                        }//}


            }



                for (String h : video){
                    //System.out.println("is video" + h);
                    h = "http://mediasrv.lds.org/media-services/lookup/" + h;
                        //System.out.println(h);

                   // System.out.println(h);
                    String href = h;

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(5000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        //System.out.println("Request URL ... " + url);

                        boolean redirect = false;

                        // normally, 3xx is redirect
                        int status = conn.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK) {
                            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                    || status == HttpURLConnection.HTTP_MOVED_PERM
                                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                                redirect = true;
                        }
                        if (conn.getResponseCode() == 404) {
                            //System.out.println("Request URL ... " + url);
                            //System.out.println("Response Code ... " + status);
                        }

                        if (redirect) {

                            // get redirect url from "location" header field
                            String newUrl = conn.getHeaderField("Location");

                            // get the cookie if need, for login
                            String cookies = conn.getHeaderField("Set-Cookie");

                            // open the new connnection again
                            conn = (HttpURLConnection) new URL(newUrl).openConnection();
                            // conn.setRequestProperty("Cookie", cookies);
                            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                            conn.addRequestProperty("User-Agent", "Mozilla");
                            conn.addRequestProperty("Referer", "google.com");
                            int statusRedirect = conn.getResponseCode();


                            //System.out.println("Redirect to URL : " + newUrl);
                           // System.out.println("Response Code ... " + statusRedirect);

                            URI redirectUri = new URI(newUrl);
                            String redirectUriPath = redirectUri.getPath();
                        }



                    } catch (Exception e){
                        e.printStackTrace();
                    }


                }


            for(String inst : titleList) {
                    String delims = "/";
                    String[] tokens = inst.split(delims);
                    int tokenCount = tokens.length;
                    //String baseUri = inst.replace(tokens[tokenCount-1] , "");
                //System.out.println(baseUri);

                for (String img : imgLink) {
                    //String imgHref= "http://preview.lds.org/bc/content/shared/content/images/gospel-library/manual/" + img;
                    String imgHref= "http://preview.lds.org/bc/content/shared/content/images/magazines/liahona/2016/09/" + img;

                    String href = imgHref;

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(5000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        //System.out.println("Request IMGURL ... " + url);

                        boolean redirect = false;

                        // normally, 3xx is redirect
                        int status = conn.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK) {
                            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                    || status == HttpURLConnection.HTTP_MOVED_PERM
                                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                                redirect = true;
                        }

                        //System.out.println("Response Code ... " + status);

                        if (redirect) {

                            // get redirect url from "location" header field
                            String newUrl = conn.getHeaderField("Location");

                            // get the cookie if need, for login
                            String cookies = conn.getHeaderField("Set-Cookie");

                            // open the new connnection again
                            conn = (HttpURLConnection) new URL(newUrl).openConnection();
                            // conn.setRequestProperty("Cookie", cookies);
                            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                            conn.addRequestProperty("User-Agent", "Mozilla");
                            conn.addRequestProperty("Referer", "google.com");
                            int statusRedirect = conn.getResponseCode();


                           // System.out.println("Redirect to URL : " + newUrl);
                            //System.out.println("Response Code ... " + statusRedirect);

                            URI redirectUri = new URI(newUrl);
                            String redirectUriPath = redirectUri.getPath();
                        }



                    } catch (Exception e){
                        e.printStackTrace();
                    }}

                for (String d : mediaID) {
                    int duplicateID = Collections.frequency(mediaID, d);
                    if (duplicateID > 1) {
                        //System.out.println("Duplicate ID at MediaID: " + d + " on artcicle " + fPath);
                    }
                }

                for (String img : mediaID) {
                    //String imgHref= "http://edge.ldscdn.org/mobile/images/" + img + "/";
                    String imgHref= "https://publish-stage.ldschurch.org/content_automation/ws/v1/getImage?mediaId=" + img;

                    String href = imgHref;

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(5000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        //System.out.println("Request URL ... " + url);

                        boolean redirect = false;

                        try {

                            int status = conn.getResponseCode();
                            if (status != HttpURLConnection.HTTP_OK) {
                                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                        || status == HttpURLConnection.HTTP_MOVED_PERM
                                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                                    redirect = true;
                            }
                            //System.out.println(href);
                            System.out.println("Response Code ... " + status);

                        } catch (java.net.SocketTimeoutException e){
                            System.out.println("Socket timed out: " + href);

                        }

                        //System.out.println("Response Code ... " + status);
                        if (conn.getResponseCode() == 404) {
                            //System.out.println("Request URL ... " + url);
                            //System.out.println("Response Code ... " + status);
                            System.out.println(img);
                        }

                        if (redirect) {

                            // get redirect url from "location" header field
                            String newUrl = conn.getHeaderField("Location");

                            // get the cookie if need, for login
                            String cookies = conn.getHeaderField("Set-Cookie");

                            // open the new connnection again
                            conn = (HttpURLConnection) new URL(newUrl).openConnection();
                            // conn.setRequestProperty("Cookie", cookies);
                            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                            conn.addRequestProperty("User-Agent", "Mozilla");
                            conn.addRequestProperty("Referer", "google.com");
                            int statusRedirect = conn.getResponseCode();


                            //System.out.println("Redirect to URL : " + newUrl);
                            //System.out.println("Response Code ... " + statusRedirect);

                            URI redirectUri = new URI(newUrl);
                            String redirectUriPath = redirectUri.getPath();
                        }



                    } catch (Exception e){
                        //e.printStackTrace();
                    }}


                for (String hShot : imgHeadshot) {
                    String hShotRef = "http://preview.lds.org/bc/content/shared/content/images/leaders/" + hShot;
                    String href = hShotRef ;

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(5000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        //System.out.println("Request URL ... " + url);

                        boolean redirect = false;

                        // normally, 3xx is redirect
                        int status = conn.getResponseCode();
                        if (status != HttpURLConnection.HTTP_OK) {
                            if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                    || status == HttpURLConnection.HTTP_MOVED_PERM
                                    || status == HttpURLConnection.HTTP_SEE_OTHER)
                                redirect = true;
                        }

                        //System.out.println("Response Code ... " + status);

                        if (redirect) {

                            // get redirect url from "location" header field
                            String newUrl = conn.getHeaderField("Location");

                            // get the cookie if need, for login
                            String cookies = conn.getHeaderField("Set-Cookie");

                            // open the new connnection again
                            conn = (HttpURLConnection) new URL(newUrl).openConnection();
                            // conn.setRequestProperty("Cookie", cookies);
                            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                            conn.addRequestProperty("User-Agent", "Mozilla");
                            conn.addRequestProperty("Referer", "google.com");
                            int statusRedirect = conn.getResponseCode();


                            //System.out.println("Redirect to URL : " + newUrl);
                            //System.out.println("Response Code ... " + statusRedirect);

                            URI redirectUri = new URI(newUrl);
                            String redirectUriPath = redirectUri.getPath();
                        }



                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
                }
            if (noteMarkerRef.size() == footnoteRef.size()){
                //System.out.println("Note marker and Footnote count matches");
            }else{System.out.println("Note marker and Footnote count DO NOT match");}

                System.out.println(xRefList);
        }}

        catch (Exception e) {
            e.printStackTrace();
        }
    }

}