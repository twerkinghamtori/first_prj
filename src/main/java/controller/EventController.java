package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Draw;
import model.DrawMybatisDao;
import model.Event;
import model.EventMybatisDao;
import model.Member;
import model.MemberMybatisDao;

@WebServlet(urlPatterns = {"/event/*"},
	initParams = {@WebInitParam(name="view", value="/view/")})
public class EventController extends MskimRequestMapping{
	private EventMybatisDao dao = new EventMybatisDao();
	private DrawMybatisDao ddao = new DrawMybatisDao();
	private MemberMybatisDao mdao = new MemberMybatisDao();
	
	public String loginAdminCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String login = (String)request.getSession().getAttribute("login");
		if(login==null || login.equals("")) {
			request.setAttribute("msg", "로그인하세요.");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if(!login.equals("admin")) {
			request.setAttribute("msg" , "관리자만 접근 가능합니다.");
			request.setAttribute("url", "/first_prj/index");
			return "alert";
		}
		return null;
	}

	@RequestMapping("eventForm")
	public String eventForm(HttpServletRequest request, HttpServletResponse response) {
		Event event = dao.selectLatest();
		request.setAttribute("event",event);
		return "event/eventForm";
	}
	
	@MSLogin("loginAdminCheck")
	@RequestMapping("event")
	public String event(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startdate=null;
		Date enddate=null;
		Calendar calendar = null;
		try {
			startdate = format.parse(request.getParameter("startdate"));
			enddate = format.parse(request.getParameter("enddate"));
			calendar = Calendar.getInstance();
			calendar.setTime(enddate);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String product = request.getParameter("prodName");
		String picture = request.getParameter("picture");
		
		Event event = new Event();
		
		event.setProduct(product);
		event.setStartdate(startdate);
		event.setEnddate(calendar.getTime());
		event.setPicture(picture);
		if(dao.countNo() >= 1) {
			request.setAttribute("msg", "기존 이벤트를 종료한 후 새로운 이벤트를 등록해주세요.");
			request.setAttribute("url", "eventForm");
			return "alert";
		} else {
			if(dao.insert(event)) {
				request.setAttribute("msg", "이벤트 등록 완료");
				request.setAttribute("url", "eventForm");
				return "alert";
			} else {
				request.setAttribute("msg", "이벤트 등록 실패");
				request.setAttribute("url", "eventForm");
				return "alert";
			}
		}	
	}
	
	@MSLogin("loginAdminCheck")
	@RequestMapping("picture")
	public String picture(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		   String path = request.getServletContext().getRealPath("/") + "/upload/event"; 
		   String fname = null;
		   File f = new File(path);
		   if(!f.exists()) f.mkdirs(); 
		   MultipartRequest multi=null;
		   try {
			   multi = new MultipartRequest(request, path, 10*1024*1024, "UTF-8");
		   } catch (IOException e) {
			   e.printStackTrace();
		   } //업로드(최대 10 MB)
		   fname = multi.getFilesystemName("picture"); 
		   request.setAttribute("fname", fname);
		   return "event/picture";
	}
	
	@MSLogin("loginAdminCheck")
	@RequestMapping("end")
	public String end(HttpServletRequest request, HttpServletResponse response) {
		Event event = dao.selectLatest();
		int latestEvent = event.getNo();
		Draw draw = ddao.selectWinner(latestEvent);
		request.setAttribute("draw", draw);
		Member mem = mdao.selectOneEmail(draw.getEmailaddress());
		request.setAttribute("mem", mem);
		request.setAttribute("event",event);		
		//테이블에서 해당 이벤트 삭제
		int no = Integer.parseInt(request.getParameter("no"));
		String winner = mem.getNickname();
		dao.update(winner,no);
		ddao.delete();
		return "event/eventForm";
	}
	
}
