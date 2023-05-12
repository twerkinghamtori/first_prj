package controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Draw;
import model.DrawMybatisDao;
import model.Event;
import model.EventMybatisDao;

@WebServlet(urlPatterns = {"/draw/*"},
	initParams = {@WebInitParam(name="view", value="/view/")})
public class DrawController extends MskimRequestMapping {
	private DrawMybatisDao dao = new DrawMybatisDao();
	private EventMybatisDao edao = new EventMybatisDao();

	@RequestMapping("draw")
	public String draw(HttpServletRequest request, HttpServletResponse response) {
		String email = (String)request.getSession().getAttribute("login");
		if(email==null || email.equals("")) {
			request.setAttribute("msg", "로그인하세요.");
			request.setAttribute("url", "/first_prj/member/loginForm");
			return "alert";
		} else if(email.equals("admin")) {
			request.setAttribute("msg", "관리자는 응모가 불가합니다.");
			request.setAttribute("url", "/first_prj/event/eventForm");
			return "alert";
		}
		int no = Integer.parseInt(request.getParameter("no"));	
		Draw draw = dao.selectOne(no);
		Date enddate = edao.selectLatest().getEnddate();
		Date startdate = edao.selectLatest().getStartdate();
		Date now = new Date();
		if(edao.selectLatest().getWinner() != null) {
			request.setAttribute("msg","이미 종료된 이벤트입니다. 다음 이벤트를 기대해주세요!");
			request.setAttribute("url", "/first_prj/event/eventForm");
			return "alert";
		} else if(now.getTime() > enddate.getTime()) {
			request.setAttribute("msg","이미 종료된 이벤트입니다. 다음 이벤트를 기대해주세요!");
			request.setAttribute("url", "/first_prj/event/eventForm");
			return "alert";
		} else if(now.getTime() < startdate.getTime()) {
			request.setAttribute("msg","이벤트 응모기간이 아닙니다. 응모기간을 확인해주세요!");
			request.setAttribute("url", "/first_prj/event/eventForm");
			return "alert";
		} else {
			if(dao.insert(email, no)) {
				request.setAttribute("msg","슈레이스 이벤트에 응모해주셔서 감사합니다. 좋은 결과 있으시길 바랍니다.");
				request.setAttribute("url", "/first_prj/event/eventForm");
				return "alert";			
			} else {
				request.setAttribute("msg","이미 응모되었습니다.");
				request.setAttribute("url", "/first_prj/event/eventForm");
				return "alert";		
			}
		} 
	}
}
