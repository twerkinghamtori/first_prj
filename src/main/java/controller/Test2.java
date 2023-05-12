package controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test2 {

	public static void main(String[] args) throws IOException {
    String url = "https://www.luck-d.com/home/";

    Document doc = Jsoup.connect(url).get();

    Elements releaseCards = doc.select("div.release_card");
    for (Element releaseCard : releaseCards) {
        String thumbnailUrl = releaseCard.select("div.product_thumb > img").attr("src");
        String productName = releaseCard.select("li.release_product_name > span.text").text();
        String releaseTime = releaseCard.select("li.release_time > span.text").text();
        String additionalInfo = releaseCard.select("li span.text span.additional_info").text();
        String detailLink = releaseCard.select("div.release_btn_layer > a").attr("href");
        
        String result = "";
        for (int i = 0; i < detailLink.length(); i++) {
            char c = detailLink.charAt(i);
            if (Character.isDigit(c)) {
                result += c;
            }
        }
        System.out.println(result);
        
        System.out.println("썸네일 URL: " + thumbnailUrl);
        System.out.println("상품이름: " + productName);
        System.out.println("발매시간: " + releaseTime);
        System.out.println("발매장소: " + additionalInfo);
        System.out.println("상세보기 Link: " + result);
        System.out.println();
    }
}

}
