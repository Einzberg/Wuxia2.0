package com.example.max.wuxia20;

import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class ChapterListExtractor {

    private static HashMap<String, HashMap> baseULRs;
    private static HashMap<String, String> chaptersDictionary;
    private HashMap<String, String> novelUrlDictionary;

    private String spritVesselIndex = "https://www.wuxiaworld.com/novel/battle-through-the-heavens";
    private String againstTheGodsIndex = "https://www.wuxiaworld.com/novel/against-the-gods";

    ChapterListExtractor(){
        setUpDictionary();
    }

    private void setUpDictionary(){
        novelUrlDictionary = new HashMap<String, String>(){
            {
                put("Against The Gods", againstTheGodsIndex);
                put("Battle Through The Heavens", spritVesselIndex);
            }
        };

        chaptersDictionary = new HashMap<>();
        baseULRs = new HashMap<>();
    }


    ArrayList GetArticleFromHtml(String html) {
        Document chapterDocument;
        try {
            chapterDocument = Jsoup.connect(html).get();
            chapterDocument.outputSettings(new Document.OutputSettings().prettyPrint(false));
            //chapterDocument.select("br").append("\\n");
            //chapterDocument.select("p").prepend("\\n\\n");

            Elements articleBodys = chapterDocument.select("p");

            ArrayList paragraphList = new ArrayList();

            for (Element element : articleBodys){
                paragraphList.add(element.text());
            }
            return paragraphList;
            /*
            Elements articleBodys = chapterDocument.select("[itemprop=articleBody]");
            String article = articleBodys.first().text();
            article = article.replaceAll("\\\\n", "\n");
            return article;
            */
        } catch (Exception e){
            Log.d("GetArticleError: ", e.toString());
            return null;
        }
    }

    ArrayList GetChaptersList(String novelName) {
        // Temporarily Make Dummy Chapters

        Document chapterDocument;
        ArrayList chapterList = new ArrayList();
        Elements articleBodies;
        try {
            chapterDocument = Jsoup.connect(novelUrlDictionary.get(novelName)).get();
            articleBodies = chapterDocument.select("span");
        } catch (Exception e) {
            Log.d("GetChaptersError: ", e.toString());
            return null;
        }

        String baseUrl;
        if (novelName.equals("Against The Gods")) {
            baseUrl = "https://www.wuxiaworld.com/novel/against-the-gods/atg-chapter-";
        } else {
            baseUrl = "https://www.wuxiaworld.com/novel/battle-through-the-heavens/btth-chapter-";
        }

        Pattern pattern = Pattern.compile("\\d{1,4}");


        for (Element element : articleBodies){
            if (!element.text().contains("Chapter")) continue;

            Matcher matcher = pattern.matcher(element.text());
            if (matcher.find()){
                if (Integer.valueOf(matcher.group(0)) < 890) continue;
                chapterList.add(element.text());
                chaptersDictionary.put(element.text(), baseUrl + matcher.group(0));
            }

        }

        /*
        Element article = articleBodies.first();

        Elements tags = article.getElementsByTag("a");
        for (Element tagElement : tags) {
            String webpageUrl = tagElement.attr("abs:href");
            chaptersDictionary.put(tagElement.text(), webpageUrl);
            chapterList.add(tagElement.text());
        }
        */

//        ArrayList chapterList = new ArrayList();
//        for (int i = 1081; i < 1200; i++){
//            chapterList.add("Chapter " + Integer.toString(i));
//            chaptersDictionary.put("Chapter " + i, "https://www.wuxiaworld.com/novel/against-the-gods/atg-chapter-" + Integer.toString(i));
//        }
        baseULRs.put(novelName, chaptersDictionary);
        return chapterList;
    }

    String GetUrlFromChapters(String novelName, String chapter){
        HashMap<String, String> chapters = baseULRs.get(novelName);
        return chapters.get(chapter);
    }

    HashMap GetChaptersAndUrls(String novelName){
        return baseULRs.get(novelName);
    }
}
