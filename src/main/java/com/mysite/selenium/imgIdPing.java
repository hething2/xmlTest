package com.mysite.selenium;

/**
 * Created by hethington on 8/1/2016.
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
import java.util.Arrays;
import java.util.Collections;

//import org.apache.http.conn.ConnectTimeoutException;

public class imgIdPing {

    public static void main(String argv[]) {


        try {


            File folder = new File("I:\\preview\\shared\\content\\english\\curriculum\\09410\\09410");
            //File folder = new File("I:\\preview\\shared\\content\\spanish\\curriculum\\PD60001923\\PD60001923");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    //System.out.println("File " + listOfFiles[i].getName());
                } else if (listOfFiles[i].isDirectory()) {
                    //System.out.println("Directory " + listOfFiles[i].getName());
                }
            }
            ArrayList<String> mediaIDList = new ArrayList<String>();
            for (File v : listOfFiles) {
                File fPath = v;

                File fXmlFile = new File("" + fPath);


                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);

                doc.getDocumentElement().normalize();

                //System.out.println("Root element :" + doc.getDocumentElement().getNodeName());


                NodeList imgList = doc.getElementsByTagName("img");

                ArrayList<String> mediaID = new ArrayList<String>();

                for (int temp = 0; temp < imgList.getLength(); temp++) {

                    Node aNode = imgList.item(temp);

                    if (aNode.getNodeType() == Node.ELEMENT_NODE) {

                        Element iElement = (Element) aNode;

                        String imgID = iElement.getAttribute("mediaID");

                        mediaID.add(imgID);
                    }
                }
                for (String d : mediaID) {
                    int duplicateID = Collections.frequency(mediaID, d);
                    if (duplicateID > 1) {
                        //System.out.println("Duplicate ID at MediaID: " + d + " on artcicle " + fPath);
                    } else {
                        mediaIDList.add(d);
                    }
                }

                for (String img : mediaID) {

//Change which server to ping here, edge is mobile server, publish-stage is content central server/ telescope


// edge server will respond with 403 access denied if the file exists on server, 404 if it does not.

// Content central will often timeout if the image is large, you can just click the link and let it run longer.
                    //String imgHref= "https://tech.lds.org/mobile/jackal/image/" + img + "/";
                    //String imgHref= "http://edge.ldscdn.org/mobile/images/" + img + "/";
                    String imgHref= "https://publish-test.ldschurch.org/content_automation/ws/v1/getImage?mediaId=" + img;

                    String href = imgHref;

                    try {
                        String url = href;

                        URL obj = new URL(url);
                        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                        conn.setReadTimeout(10000);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla");
                        conn.addRequestProperty("Referer", "google.com");

                        System.out.println("Request URL ... " + url);

                        boolean redirect = false;

                        // normally, 3xx is redirect
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

                        if (conn.getResponseCode() == 404) {
                            //System.out.println("Request URL ... " + url);
                            //System.out.println("Response Code ... " + status);
                            //System.out.println(img);
                        }



                    } catch (Exception e){
                        //e.printStackTrace();
                    }}
            }
            Arrays.asList(mediaIDList);
            System.out.println(mediaIDList.toString());
            System.out.println(mediaIDList.size());

        }

    catch (Exception e) {
        e.printStackTrace();
    }
}

}