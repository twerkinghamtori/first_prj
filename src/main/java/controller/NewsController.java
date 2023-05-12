package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.NewsCard;
import model.NewsDetail;

@WebServlet(urlPatterns = {"/news/*"},
initParams = {@WebInitParam(name="view", value="/view/")}
		)
public class NewsController extends MskimRequestMapping{
	
	@RequestMapping("list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String url = "https://www.luck-d.com/agit/"; // 추출하려는 웹 페이지 주소
		
        Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		} // Jsoup을 이용하여 웹 페이지를 가져옴
		
        Elements elements = doc.select("div.card_cell[data-board]"); // data-board 속성이 있는 div 태그 선택
        List<NewsCard> list = new ArrayList<>();
        
        for (Element element : elements) {
        	NewsCard nc = new NewsCard();
            String dataBoard = element.attr("data-board"); // data-board 속성 값 추출
            nc.setNo(dataBoard);
            String imgSrc = element.select("img").first().attr("src"); // 첫 번째 img 태그의 src 속성 값 추출
            nc.setSrc(imgSrc);
            String alt = element.select("img").first().attr("alt"); // 첫 번째 img 태그의 src 속성 값 추출
            nc.setAlt(alt);
            
            list.add(nc);
        }
        
        request.setAttribute("list", list);
        
		return "news/list";
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, HttpServletResponse response) {
		String no = request.getParameter("no");
		String url = "https://www.luck-d.com/agit/news/"+no+"/";
        Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		NewsDetail nd = new NewsDetail();
		List<String> imgList = new ArrayList<>();
        // 페이지 제목 추출
        String pageTitle = doc.select("h1.page_title").text();
        nd.setTitle(pageTitle);
        // 게시물 내용 추출
        Element postContent = doc.select(".post_contents_layer").first();
        nd.setContent(postContent.text());
        // 이미지 src 속성 추출
        Elements images = postContent.select("img");
        for (Element img : images) {
            String imgUrl = img.attr("src");
            imgList.add(imgUrl);
        }
		
        request.setAttribute("nd", nd);
        request.setAttribute("imgList", imgList);
        request.setAttribute("imgCnt", imgList.size());
        
		return "news/detail";
	}
}
