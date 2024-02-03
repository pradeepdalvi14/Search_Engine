package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Crawler {
    HashSet<String> urlSet;
    int MAX_DEPTH = 2;
    Crawler(){
        urlSet = new HashSet<String>();
    }
    public void getPageTextAndLinks(String url, int depth){
        if(urlSet.contains(url)){
            return;
        }
        if(depth>MAX_DEPTH){
            return;
        }
//        if(urlSet.add(url)){
//            System.out.println(url);
        //}
        depth++;
        try {
            //jsoup will convert html file to java object

            Document document = Jsoup.connect(url).timeout(5000).get();
            //indexer work starts here
            Indexer indexer = new Indexer(document, url);



            System.out.println(document.title());
            Elements availableLinkOnPage = document.select("a[href]");
            for(Element currentLink: availableLinkOnPage){
                getPageTextAndLinks(currentLink.attr("abs:href"),depth );
            }
        }
        catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {

      Crawler crawler = new Crawler();
      crawler.getPageTextAndLinks("https://www.javatpoint.com",1);
    }
}