package controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test4 {

	public static void main(String[] args) throws Exception {
        String url = "https://www.luck-d.com/agit/"; // 추출하려는 웹 페이지 주소
        Document doc = Jsoup.connect(url).get(); // Jsoup을 이용하여 웹 페이지를 가져옴
        Elements elements = doc.select("div.card_cell[data-board]"); // data-board 속성이 있는 div 태그 선택
        for (Element element : elements) {
            String dataBoard = element.attr("data-board"); // data-board 속성 값 추출
            String imgSrc = element.select("img").first().attr("src"); // 첫 번째 img 태그의 src 속성 값 추출
            System.out.println("data-board: " + dataBoard);
            System.out.println("img src: " + imgSrc);
        }
    }

}
