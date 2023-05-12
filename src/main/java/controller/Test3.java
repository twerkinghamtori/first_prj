package controller;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test3 {
	  public static void main(String[] args) throws IOException {
        String url = "https://www.luck-d.com/release/product/5200/"; // 크롤링하고자 하는 웹페이지의 URL
        Document doc = Jsoup.connect(url).get(); // Jsoup을 이용하여 HTML 페이지 파싱

        // 브랜드 정보 추출
        Element brand = doc.selectFirst("span.text.cursor-pointer");
        System.out.println("Brand: " + brand.text());

        // 제품 정보 추출
        Element productInfo = doc.selectFirst("div.product_info");

        Element pageTitle = productInfo.selectFirst("h1.page_title");
        System.out.println("Page Title: " + pageTitle.text());

        Element subTitle = productInfo.selectFirst("h2.sub_title");
        System.out.println("Sub Title: " + subTitle.text());

        Element productCode = productInfo.selectFirst("li:has(span.title:contains(제품 코드))  span.text");
        if(productCode != null) 
        	System.out.println("Product Code: " + productCode.text());

        Element productColor = productInfo.selectFirst("li:has(span.title:contains(제품 색상))  span.text");
        if(productColor != null) 
        	System.out.println("Product Color: " + productColor.text());

        Element releaseDate = productInfo.selectFirst("li:has(span.title:contains(발매일))  span.text");
        if(releaseDate != null)
        	System.out.println("Release Date: " + releaseDate.text());

        Element price = productInfo.selectFirst("li:has(span.title:contains(발매가))  span.text");
        if(price != null)
        	System.out.println("Price: " + price.text());

        Element productDesc = productInfo.selectFirst("li:has(span.title:contains(제품 설명)) div");
        if(productDesc != null)
        	System.out.println("Product Description: " + productDesc.text());
        
        Element productimage= doc.selectFirst(".product_detail");
        Elements images = productimage.select("img");
        for(Element image : images) {
        	String src = image.attr("src");
        	System.out.println(src);
        }
	  }
}
