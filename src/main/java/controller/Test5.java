package controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test5 {

	public static void main(String[] args) throws IOException {
        String url = "https://www.luck-d.com/agit/news/707/";
        Document doc = Jsoup.connect(url).get();

        // 페이지 제목 추출
        String pageTitle = doc.select("h1.page_title").text();
        System.out.println("페이지 제목: " + pageTitle);

        // 게시물 내용 추출
        Element postContent = doc.select(".post_contents_layer").first();
        System.out.println("게시물 내용: " + postContent.text());
        // 이미지 src 속성 추출
        Elements images = postContent.select("img");
        for (Element img : images) {
            String imgUrl = img.attr("src");
            System.out.println("이미지 URL: " + imgUrl);
        }
    }

}
