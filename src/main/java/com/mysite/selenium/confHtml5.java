package com.mysite.selenium;

import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import com.sun.jndi.toolkit.url.Uri;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.css.Counter;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;





public class confHtml5 {
  public WebDriver driver;
  public String loginUrl;
  public String login;
  public String pass;
  public boolean acceptNextAlert = true;
  public StringBuffer verificationErrors = new StringBuffer();
  public String next;
  public String prev;
  public String manualTocPath;
  public String gcToc;
  public String liahonaToc;
  public String url;
  public String toc;
  public final String USER_AGENT = "Mozilla/5.0";
  public String tocPath;
  public String currUrl;
  public String urlBroadcast;
  DesiredCapabilities capabilities = DesiredCapabilities.firefox();
  //capabilities.setCapability("marionette", true);
  //WebDriver driver = new FirefoxDriver(capabilities);

  @Before
  //config
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
    login = "hethington";
    pass = "Newton89!*";
    loginUrl = "https://preview.lds.org/";
    next = ".//*[@class='next']/a";
    prev = ".//*[@class='prev']/a";
    url = "https://preview.lds.org/general-conference/2016/04?lang=";
    urlBroadcast = "https://preview.lds.org/broadcasts/article/evening-with-a-general-authority/2016/02/the-opportunities-and-responsibilities-of-ces-teachers-in-the-21st-century?lang=eng&_r=2";



  }

  public void login() throws Exception{
    driver.get(loginUrl);
    driver.findElement(By.id("IDToken1")).clear();
    driver.findElement(By.id("IDToken1")).sendKeys(login);
    driver.findElement(By.id("IDToken2")).clear();
    driver.findElement(By.id("IDToken2")).sendKeys(pass);
    driver.findElement(By.name("Login.Submit")).click();
    driver.get(url + "eng");
  }



  @Test
  //Test will collect all articles(href) in TOC, and check for "next" TOC btn. Will also collect # of notemarkers and a footnotes and compare amount of them.
  public void testSele() throws Exception {
    login();
    URI uri = new URI(driver.getCurrentUrl());
    String path = uri.getPath();
    String idStr = path.substring(path.lastIndexOf('/') + 1);
    //int id = Integer.parseInt(idStr);
    //System.setOut(new PrintStream(new FileOutputStream("C:\\Users\\hethington\\Desktop\\testLogs\\"+idStr+".txt")));
    tocPath = " ";
    manualTocPath = ".//*[@id='primary']//td[position()=2]//a";
    gcToc = "//html/body/div[*]/section/div[*]/div[*]/div[*]/a";
    liahonaToc = "//td[1]/a";

    currUrl = driver.getCurrentUrl();
    //System.out.println(currUrl);

    if(currUrl.contains("manual")){
      System.out.println("Is a manaual");
      tocPath = manualTocPath;

    }
    if(currUrl.contains("general-conference")){
      System.out.println("Is General conference");
      tocPath = gcToc;
    }
    if(currUrl.contains("liahona")){
      System.out.println("Is Liahona Magazine");
      tocPath = liahonaToc;
    }
    if(currUrl.contains("friend")){
      System.out.println("Is friend Magazine");
      tocPath = liahonaToc;
    }
    if(currUrl.contains("ensign")){
      System.out.println("Is ensign Magazine");
      tocPath = liahonaToc;
    }
    if(currUrl.contains("new-era")){
      System.out.println("Is New Era Magazine");
      tocPath = liahonaToc;
    }

    ArrayList<WebElement> toc = (ArrayList<WebElement>) driver.findElements(By.xpath(tocPath));
    //System.out.println(toc);
    ArrayList<String> linklist = new ArrayList<String>();
    //System.out.println(linklist);


    for (WebElement l : toc) {
      //String stringlinklinklist = (l.getAttribute("href"));
      //System.out.println(stringlinklinklist);
      System.out.println(l.getAttribute("href"));
      if(l.getAttribute("href").contains("media")|l.getAttribute("href").contains("section")){

      }else {

        linklist.add(l.getAttribute("href"));
        System.out.println(linklist);
      }
    }

    if(currUrl.contains("broadcasts")){


    }else{
      int i = 0;
      for(String l : linklist) {
        driver.get(l);
        ArrayList<WebElement> artID = (ArrayList<WebElement>) driver.findElements(By.xpath("html/head/title"));
        ArrayList<String> artIDList = new ArrayList<String>();

        for (WebElement a : artID) {
          artIDList.add(a.getAttribute("textContent"));
          //System.out.println(a.getAttribute("id"));

        }
        int pageCount = linklist.size();
        System.out.println(pageCount);
        int artCount;
        artCount = artIDList.size();

        System.out.println("Checking article: " + artIDList+ " ");
        //System.out.print("@" + l);


        List<WebElement> nodeID = driver.findElements(By.xpath("//p[starts-with(@id, 'p')] | //h2[starts-with(@id, 'p')] | //h3[starts-with(@id, 'p')] | //h4[starts-with(@id, 'p')]"));
        ArrayList<String> nodeIDList = new ArrayList<String>();

        if (artCount > 1) {
          System.out.println("Article " + artIDList + " has subarticles.");
        } else {

          for (WebElement n : nodeID) {
            nodeIDList.add(n.getAttribute("id"));
          }
          for (String d : nodeIDList) {
            int duplicateID = Collections.frequency(nodeIDList, d);
            if (duplicateID > 1) {
              System.out.println("Duplicate ID at: " + d);
            }
          }
        }

        int IDCount = nodeID.size();

        ArrayList<WebElement> noteMarker = (ArrayList<WebElement>) driver.findElements(By.className("note-ref"));
        ArrayList<String> noteMarkerList = new ArrayList<String>();

        for (WebElement nm : noteMarker) {
          noteMarkerList.add(nm.getText());
        }
        for (String nt : noteMarkerList) {
          int duplicateNote = Collections.frequency(noteMarkerList, nt);
          if (duplicateNote > 1) {
            System.out.println("NoteMarker is duplicated: " + nt);
          }
          //else{System.out.println("no dupe notemarkers found.");}
        }

        ArrayList<WebElement> footNote = (ArrayList<WebElement>) driver.findElements(By.xpath("//ol/footer/ol/li"));
        ArrayList<String> footNoteList = new ArrayList<String>();

        for (WebElement nf : footNote) {
          String footnoteText = nf.getAttribute("data-marker");
          if(footnoteText.contains("．")) {
            String newNFT = footnoteText.replace("．", "");
          }else{ String newNFT = footnoteText.replace(".","");
            footNoteList.add(newNFT);
          }}
        //System.out.println(footNoteList);
        for (String nft : footNoteList) {
          if(nft.contains("．")) {
            String newNFT = nft.replace("．", "");}else{ String newNFT = nft.replace(".","");
            //System.out.println(newNFT);
            int duplicateNote = Collections.frequency(footNoteList, newNFT);

            if (duplicateNote > 1) {
              System.out.println("Footnote is duplicated: " + newNFT);
            }
            //else{System.out.println("no dupe footnotes found.");}
          }}
        //System.out.println();
        //ArrayList<WebElement> scripLink = (ArrayList<WebElement>) driver.findElements(By.xpath(".//*[@id='primary']//a[@class='scriptureRef']"));

        ArrayList<WebElement> imgLink = (ArrayList<WebElement>) driver.findElements(By.xpath("/html/body/div[3]/section/div[2]/div/img"));
        ArrayList<String> imgLinkList = new ArrayList<String>();

        for (WebElement j : imgLink) {
          imgLinkList.add(j.getAttribute("src"));
        }

        for (String u : imgLinkList) {

          if (u == null || u.contains(l) || u.contains("@")) {

          }else{
            String img = u;
            if (u.contains("image/gif")) {
              System.out.println("content contains a Gif " + u);
            } else {
              //System.out.println(u);
              try {
                String url = u;

                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
                conn.setReadTimeout(5000);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                conn.addRequestProperty("User-Agent", "Mozilla");
                conn.addRequestProperty("Referer", "google.com");

                System.out.println("Request URL ... " + url);

                boolean redirect = false;

                // normally, 3xx is redirect
                int status = conn.getResponseCode();
                if (status > 399 && status < 512){
                  System.out.println("Link not responsive, Response Code : " + status + " @ " + url);}
                if (status != HttpURLConnection.HTTP_OK) {
                  if (status == HttpURLConnection.HTTP_MOVED_TEMP
                          || status == HttpURLConnection.HTTP_MOVED_PERM
                          || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
                }

                System.out.println("Response Code ... " + status);

                if (redirect) {

                  // get redirect url from "location" header field
                  String newUrl = conn.getHeaderField("Location");

                  // get the cookie if need, for login
                  String cookies = conn.getHeaderField("Set-Cookie");

                  // open the new connnection again
                  conn = (HttpURLConnection) new URL(newUrl).openConnection();
                  //conn.setRequestProperty("Cookie", cookies);
                  conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                  conn.addRequestProperty("User-Agent", "Mozilla");
                  conn.addRequestProperty("Referer", "google.com");
                  int statusRedirect = conn.getResponseCode();
                  if (statusRedirect > 399 && status < 512){
                    System.out.println("Link not responsive, Response Code : " + statusRedirect + " @ " + url);}


                  //System.out.println("Redirect to URL : " + newUrl);
                  System.out.println("Response Code ... " + statusRedirect);

                }



              } catch (Exception e) {
                e.printStackTrace();
              }

            }}}


        ArrayList<WebElement> aLink = (ArrayList<WebElement>) driver.findElements(By.xpath(".//a[@class='scripture-ref']"));
        ArrayList<String> aLinkList = new ArrayList<String>();
        ArrayList<String> scripRefList = new ArrayList<String>();

        for (WebElement a : aLink) {
          aLinkList.add(a.getAttribute("href"));

          System.out.println(a.getAttribute("href"));

        }

        for (String u : aLinkList) {
          if (u == null || u.contains("@")) {

          }else{
            //System.out.println(u);
            try {
              String url =u;

              URL obj = new URL(url);
              HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
              conn.setReadTimeout(5000);
              conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
              conn.addRequestProperty("User-Agent", "Mozilla");
              conn.addRequestProperty("Referer", "google.com");


              System.out.println("Request URL ... " + url);

              boolean redirect = false;

              // normally, 3xx is redirect
              int status = conn.getResponseCode();
              //if (status > 399 && status < 512){
              //System.out.println("Link not responsive, Response Code : " + status + " @ " + url);}
              if (status == HttpURLConnection.HTTP_UNAVAILABLE){System.out.println("http unavailable");}

              if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                  redirect = true;
              }

              System.out.println("Response Code ... " + status);

              if (redirect) {
                if (url.contains("http")){
                  String url2 = url.replace("http://", "https://");
                  // get redirect url from "location" header field
                  String newUrl = conn.getHeaderField("Location");
                  // get the cookie if need, for login
                  String cookies = conn.getHeaderField("Set-Cookie");
                  // open the new connnection again
                  conn = (HttpURLConnection) new URL(newUrl).openConnection();
                  //conn.setRequestProperty("Cookie", cookies);
                  conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                  conn.addRequestProperty("User-Agent", "Mozilla");
                  conn.addRequestProperty("Referer", "google.com");
                  int statusRedirect = conn.getResponseCode();
                  if (statusRedirect >= 399 && status <= 512){
                    System.out.println("Link not responsive, Response Code : " + statusRedirect + " @ " + url2);}
                  //System.out.println("Redirect to URL : " + newUrl);
                  // System.out.println("Response Code ... " + statusRedirect);
                }else {
                  // get redirect url from "location" header field
                  String newUrl = conn.getHeaderField("Location");
                  // get the cookie if need, for login
                  String cookies = conn.getHeaderField("Set-Cookie");
                  // open the new connnection again
                  conn = (HttpURLConnection) new URL(newUrl).openConnection();
                  //conn.setRequestProperty("Cookie", cookies);
                  conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                  conn.addRequestProperty("User-Agent", "Mozilla");
                  conn.addRequestProperty("Referer", "google.com");
                  int statusRedirect = conn.getResponseCode();
                  if (statusRedirect >= 399||status <= 512) {
                    System.out.println("Link not responsive, Response Code : " + statusRedirect + " @ " + url);
                  }
                  System.out.println("Redirect to URL : " + newUrl);
                  System.out.println("Response Code ... " + statusRedirect);
                }
              }
            } catch (Exception e) {
              //e.printStackTrace();
              System.out.println( u +" Host cannot be reached, you have a broken link there, good sir.");

            }
          }}


        for(WebElement sc : aLink){
          String linkTextPrint = sc.getAttribute("href");
          //System.out.println(linkTextPrint);
          if (linkTextPrint == null || linkTextPrint.contains(l) || linkTextPrint.contains("@")) {

          }else{
            if(linkTextPrint.contains("/scriptures/") ){scripRefList.add(sc.getAttribute("href"));

            }}}

        //System.out.println(bookList);


        int markerCount = noteMarker.size();
        int noteCount = footNote.size();

        //System.out.println(markerCount);
        //System.out.println(noteCount);
        //System.out.println(IDCount);
        //System.out.println(nodeIDList);
        //System.out.println("Is a ScripRef" + scripRefList);

        if (markerCount == noteCount) {
          System.out.println("Markers and footnotes # Match");
        } else {
          System.out.println("Markers and footnotes # NOT Matched");
          if (markerCount > noteCount) {
            System.out.println("Missing footnote");
          }
          if (noteCount > markerCount) {
            System.out.println("Missing footnote Marker");
          }
        }

        for(WebElement n : noteMarker){
          //System.out.println(n.getText());
        }

        for(WebElement f : footNote){
          String footNoteText = f.getAttribute("textContent");
          //path.substring(path.lastIndexOf('/') + 1);
          //
          //footNoteText.substring(footNoteText)
          //System.out.println(footNoteText);
        }

        //System.out.println(noteMarkerList);
        //System.out.println(footNoteList);

        if(footNoteList.containsAll(noteMarkerList)){System.out.println("Footnotes and markers match");

        }else{
          System.out.println("notes do not match");
          ArrayList<String> foot = new ArrayList<String>();
          ArrayList<String> note = new ArrayList<String>();
          for (String r : noteMarkerList){
            note.add(r);}
          for (String r : footNoteList){
            foot.add(r);}
          foot.removeAll(note);
          System.out.println(foot);

        }


        if(noteMarkerList.containsAll(footNoteList)){System.out.println("Footnotes and markers match");

        }else{
          System.out.println("notes do not match");
          ArrayList<String> foot = new ArrayList<String>();
          ArrayList<String> note = new ArrayList<String>();
          for (String r : noteMarkerList){
            note.add(r);}
          for (String r : footNoteList){
            foot.add(r);}

          note.removeAll(foot);

          System.out.println(note);

        }

        WebDriverWait wait = new WebDriverWait(driver, 1);
        if (tocPath == liahonaToc) {

        } else {
          //WebElement waitNavBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='platform-canvas-nav']")));
          //WebElement waitSideBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='secondary']")));
        }

        i++;}}
    driver.quit();}




  @After
  public void tearDown() throws Exception {
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
