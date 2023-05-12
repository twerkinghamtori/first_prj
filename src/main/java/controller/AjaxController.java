package controller;

import java.util.List;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.users.SparseUserDatabase;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.BoardDetailView;
import model.BoardMybatisDao;
import model.CommRecommendMybatisDao;
import model.Comment;
import model.CommentMybatisDao;
import model.Member;
import model.MemberMybatisDao;
import model.MessengerMybatisDao;
import model.RecommendMybatisDao;

@WebServlet(urlPatterns = {"/ajax/*"},
initParams = {@WebInitParam(name="view", value="/view/")}
		)
public class AjaxController extends MskimRequestMapping{
	private RecommendMybatisDao rDao = new RecommendMybatisDao();
	private CommRecommendMybatisDao crDao = new CommRecommendMybatisDao ();
	private MessengerMybatisDao msgdao = new MessengerMybatisDao();
	private BoardMybatisDao bDao = new BoardMybatisDao();
	private CommentMybatisDao cDao = new CommentMybatisDao();
	private MemberMybatisDao mbDao = new MemberMybatisDao();
	
	
	@RequestMapping("unreadMsg")
	public String unreadMsg(HttpServletRequest request, HttpServletResponse response) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		int notReadCnt = msgdao.notReadCnt(nickname);
		
		request.setAttribute("unreadMsg", notReadCnt);
		return "ajax/unreadMsg";
	}
	
	@RequestMapping("replyRecommForm")
	public String replyRecommForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		int no = Integer.parseInt(request.getParameter("no"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		Comment c = cDao.selectOne(no, seq);
		List<String> list = crDao.list(no, seq);
		int isDup = 0;
		for(String s : list) {
			if(s.equals(login))  isDup = 1;
		}
		
		request.setAttribute("c", c);
		request.setAttribute("isDup", isDup);
		
		return "ajax/replyRecommForm";
	}
	
	@RequestMapping("replyRecomm")
	public String replyRecomm(HttpServletRequest request, HttpServletResponse response) {
		int no = Integer.parseInt(request.getParameter("no"));
		String login = (String)request.getSession().getAttribute("login");
		int seq = Integer.parseInt(request.getParameter("seq"));
		
		String url = "/first_prj/board/detail?no="+no;
		if(!crDao.insert(no, seq, login)) {
			request.setAttribute("msg", "이미 추천 하셨습니다.");
			request.setAttribute("url", url);
			return "alert";
		}
		if(!cDao.plusRecomm(no, seq)) {
			request.setAttribute("msg", "오류");
			request.setAttribute("url", url);
			return "alert";
		}
		return "redirect:" + url;
	}
	
	@RequestMapping("recommForm")
	public String recommForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		int no = Integer.parseInt(request.getParameter("no"));
		BoardDetailView b = bDao.selectOne(no);
		List<String> list = rDao.list(no);
		int isDup = 0;
		for(String s : list) {
			if(s.equals(login))  isDup = 1;
		}
		
		request.setAttribute("b", b);
		request.setAttribute("isDup", isDup);
		
		return "ajax/recommForm";
	}
	
	@RequestMapping("recomm")
	public String recomm(HttpServletRequest request, HttpServletResponse response) {
		
		int no = Integer.parseInt(request.getParameter("no"));
		String login = (String)request.getSession().getAttribute("login");
		
		String url = "/first_prj/board/detail?no="+no;
		if(!rDao.insert(no, login)) {
			request.setAttribute("msg", "이미 추천 하셨습니다.");
			request.setAttribute("url", url);
			return "alert";
		}
		if(!bDao.plusRecomm(no)) {
			request.setAttribute("msg", "오류");
			request.setAttribute("url", url);
			return "alert";
		}
		return "redirect:" + url;
	}
	
	@RequestMapping("passChk")
	public String passChk(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("pass");
		String password2 = request.getParameter("pass2");
		boolean b = true;
		String emptyChk = null;
		String emptyChk2 = null;
		if(password2==null || password2.equals("")){
			emptyChk="emptyChk";
		 } else{
			 emptyChk="";
		 }
		if(password.equals(password2)) {
			b=true;
		} else {
			b=false;			 
		} 
		request.setAttribute("b", b);
		request.setAttribute("emptyChk", emptyChk);
		request.setAttribute("emptyChk2", emptyChk2);
		return "ajax/passChk";
	}
	
	@RequestMapping("corrPassChk")
	public String corrPassChk(HttpServletRequest request, HttpServletResponse response) {
		String password = request.getParameter("pass");
		String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,16}$";
		boolean b = true;
		String emptyChk = null;
		if(password==null || password.equals("")){
			emptyChk="emptyChk";
		} else {
			 emptyChk="";
		}
		if(password.matches(pattern)) {
			b=true;
		} else {
			b=false;
		}
		request.setAttribute("emptyChk", emptyChk);
		request.setAttribute("b", b);
		return "ajax/corrPassChk";
	}
	
	@RequestMapping("nickchk")
	public String nickchk(HttpServletRequest request, HttpServletResponse response) {
		String nickname = request.getParameter("nickname");
		Member mem = mbDao.selectOneNick(nickname);
		boolean able = true;
		String emptyChk = null;
		if(nickname==null || nickname.equals("")) {
			emptyChk = "emptyChk";
		}
		if(mem==null) {
			able=true;
		} else {
			able=false;
		}
		request.setAttribute("able", able);
		request.setAttribute("emptyChk", emptyChk);
		return "ajax/nickchk";
	}
	
	@RequestMapping("basicForm")
	public String basicForm(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		String email = request.getParameter("emailaddress");
		String picture = "";
		if(login.equals("admin") && email.equals("@")) {
			Member adminM = mbDao.selectOneEmail("admin");
			picture = adminM.getPicture();
		} else {
			Member mem = mbDao.selectOneEmail(email);			
			picture = mem.getPicture();
		}		
		request.setAttribute("picture", picture);
		return "ajax/basicForm";
	}
	
	@RequestMapping("nickchkUpdate")
	public String nickchkUpdate(HttpServletRequest request, HttpServletResponse response) {
		String nickname = request.getParameter("nickname");
		String myNickname = (String)request.getSession().getAttribute("nickname");
		String emailaddress = request.getParameter("emailaddress");
		Member mem = mbDao.selectOneNick(nickname);
		Member member =mbDao.selectOneEmail(emailaddress);
		boolean b = false;
		String emptyChk = null;
		if(myNickname.equals("운영자")) {
			myNickname = member.getNickname();
		}
		if(nickname==null || nickname.equals("")) {
			emptyChk = "emptyChk";
		}
		if(nickname.equals(myNickname)) {
			request.setAttribute("myNick", "myNick");
		} else if(mem==null) {
			b=true;
			request.setAttribute("b", b);
		} else {
			b=false;
			request.setAttribute("b", b);
		}
		request.setAttribute("emptyChk", emptyChk);
		return "ajax/nickchkUpdate";
	}
}
