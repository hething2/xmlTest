package com.mysite.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;


public class mobileGetVideoID {
    private WebDriver driver;
    private String loginUrl;
    private String login;
    private String pass;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String next;
    private String prev;
    private String manualTocPath;
    private String gcToc;
    private String liahonaToc;
    private String url;
    private String toc;
    private final String USER_AGENT = "Mozilla/5.0";
    private String tocPath;
    private String idRedirect;
    private String currUrl;
    private String urlBroadcast;

    @Before
    //config
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        login = "hethington";
        pass = "Newton89!*";
        loginUrl = "https://preview-int.lds.org/";
        next = ".//*[@class='next']/a";
        prev = ".//*[@class='prev']/a";
        url = "https://preview-int.lds.org/manual/come-follow-me-for-individuals-and-families-2017-test?lang=";
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
        String langUriClean = driver.getCurrentUrl().substring(driver.getCurrentUrl().lastIndexOf('=') + 1);
        //System.out.println(langUriClean);
       // System.setOut(new PrintStream(new FileOutputStream("C:\\Users\\hethington\\Desktop\\testLogs\\"+idStr+"_"+langUriClean+".txt")));
        //WebElement title = driver.findElement(By.tagName("title"));

        tocPath = " ";
        manualTocPath = ".//*[@id='primary']//td[position()=2]//a";
        gcToc = ".//*[@class='talk']//a";
        liahonaToc = "//td[1]/a";

        currUrl = driver.getCurrentUrl();
        //System.out.println(currUrl);

        if(currUrl.contains("manual")){
            System.out.println("Is a manual");
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
        ArrayList<String> linklist = new ArrayList<String>();
        //System.out.println(linklist);

        int pageCount = toc.size();
        for (WebElement l : toc) {
            linklist.add(l.getAttribute("href"));

        }

        if(currUrl.contains("broadcasts")){


        }else{
            int i = 0;
            for(String l : linklist) {
                driver.get(l);
                ArrayList<WebElement> artID = (ArrayList<WebElement>) driver.findElements(By.id("article-id"));
                ArrayList<String> artIDList = new ArrayList<String>();

                for (WebElement a : artID) {
                    artIDList.add(a.getAttribute("textContent"));
                    //System.out.println(a.getAttribute("id"));

                }
                System.out.println(artIDList);

                ArrayList<WebElement> vidLink = (ArrayList<WebElement>) driver.findElements(By.xpath(".//*[@id='primary']//source"));
               // System.out.println(vidLink);
                ArrayList<String> vidLinkList = new ArrayList<String>();


                for (WebElement v : vidLink) {
                    //if(!vidLinkList.contains(vidLink)){
                    vidLinkList.add(v.getAttribute("src"));

                }


System.out.println(vidLinkList);
                System.out.println(vidLinkList.size());
                System.out.println(vidLinkList);

                if(vidLinkList.size() < 1) {

                }else{
                    for (String v : vidLinkList) {
                        //System.out.println(vidLinkText);
                        URI vid = new URI(v);
                        String vidPath = vid.getPath();
                        String idVid = vidPath.substring(vidPath.lastIndexOf('/') + 1);

                        //System.out.println(artIDList);
                        System.out.println("idVid " + idVid);

                        //System.out.println("");

                        String vidUrl = "http://mediasrv.lds.org/media-services/lookup/" + idVid;

                        //for (String u : vidLinkList) {
                            //System.out.println(u);
                            try {
                            String url = vidUrl;

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


                                System.out.println("Redirect to URL : " + newUrl);
                                System.out.println("Response Code ... " + statusRedirect);

                                URI redirectUri = new URI(newUrl);
                                String redirectUriPath = redirectUri.getPath();
                                idRedirect= " ";
                                if(newUrl.contains("-copy.")){
                                    String idRedirect = redirectUriPath.substring(redirectUriPath.lastIndexOf('-') - 3);
                                    String idRedirectClean = idRedirect.replace("-copy.mp4","");
                                    //System.out.println(idRedirect);
                                    System.out.println(idRedirectClean);
                                    String langCheck = "?lang=" + idRedirectClean;
                                    if(l.contains(langCheck)){System.out.println("videos Match source lang");}
                                    else {System.out.println("Videos NOT Matching");}

                                }else{
                                String idRedirect = redirectUriPath.substring(redirectUriPath.lastIndexOf('-') + 1);
                                    String idRedirectClean = idRedirect.replace(".mp4","");
                                System.out.println(idRedirectClean);

                                    String langCheck = "?lang=" + idRedirectClean;
                                    if(l.contains(langCheck)){System.out.println("videos Match source lang");}
                                    else {System.out.println("Videos NOT Matching");}

                            }}



                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                                //print result
                                //System.out.println(response.toString());
                            }





                    }



                WebDriverWait wait = new WebDriverWait(driver, 0);
                if (tocPath == liahonaToc) {

                } else {
                    //WebElement waitNavBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='platform-canvas-nav']")));
                    //WebElement waitSideBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='secondary']")));
                }

                if (i < pageCount - 1) {
                    //WebElement waitNext = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(next)));
                    //if (!waitNext.getAttribute("href").contains(linklist.get(i + 1))) {
                        //System.out.println("Next button has incorrect link");
                    //}
                }
                if (i != 0) {
                    //WebElement waitPrev = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prev)));
                    //if (!waitPrev.getAttribute("href").contains(linklist.get(i - 1))) {
                        //System.out.println("Next button has incorrect link");
                    }
                }

                i++;}
    }


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
