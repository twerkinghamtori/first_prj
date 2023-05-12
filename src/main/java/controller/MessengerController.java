package controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Member;
import model.MemberMybatisDao;
import model.Messenger;
import model.MessengerMybatisDao;

@WebServlet(urlPatterns = {"/messenger/*"},
initParams = {@WebInitParam(name="view", value="/view/")})
public class MessengerController extends MskimRequestMapping {
	private MessengerMybatisDao dao = new MessengerMybatisDao();
	private MemberMybatisDao mdao = new MemberMybatisDao();
	
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException { // @MSLogin annotation에 있는 거랑 이름 똑같아야함.
		request.setCharacterEncoding("UTF-8");
		String nicknameS = (String)request.getSession().getAttribute("nickname");
		if(nicknameS==null || nicknameS.equals("")) {
			request.setAttribute("msg", "로그인하세요.");
			request.setAttribute("url", "/first_prj/member/loginForm");
			return "alert";
		} 
		return null;
	}
	
	@MSLogin("loginCheck")
	@RequestMapping("msgForm")
	public String msgForm(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String nickname = (String)request.getSession().getAttribute("nickname");
		String receiver = request.getParameter("receiver");
		Messenger msg = new Messenger();		
		List<Messenger> msgs = dao.selectMsgs(receiver,nickname); //서로 주고 받은 레코드 시간 순 정렬(regdate asc)
		List<String> senders = dao.selectSenders(nickname); //receiver가 나이고 sender가 나인 모든 레코드(regdate desc)
		int notReadCnt = 0;		
			
		if(receiver==null) { //맨 처음 페이지. 왼쪽 쪽지함만 보임
			senders = dao.selectSenders(nickname);
			notReadCnt = dao.notReadCnt(nickname);
			
			Map<String, Map<String, Object>> senderInfoMap = new HashMap<>(); //쪽지함 정보
			for(String sender : senders) {
				senderInfoMap.put(sender, new HashMap<>()); //보낸사람 닉네임(key)
				Map<String, Object> senderInfo = senderInfoMap.get(sender); 
				senderInfo.put("cnt", dao.notReadCntSep(nickname,sender)); //읽지 않은 메세지
				senderInfo.put("pic", mdao.selectOneNick(sender).getPicture()); //사진
			}			
			
			request.setAttribute("senders", senders);
			request.setAttribute("notReadCnt", notReadCnt);
			request.setAttribute("senderInfoMap",senderInfoMap);
			return "messenger/noMsgForm";
		} else {
//			if(receiver.equals("운영자")) {
//				request.setAttribute("msg", "운영자에게는 쪽지를 보낼 수 없습니다.");
//				request.setAttribute("url", "msgForm");
//				return "alert";
//			}
			dao.read(nickname,receiver); //isRead update
			
			msgs = dao.selectMsgs(receiver,nickname); //주고받은 메세지 정보
			senders = dao.selectSenders(nickname); //쪽지함
			
			Map<String, Map<String, Object>> senderInfoMap = new HashMap<>(); //쪽지함 정보
			for(String sender : senders) {
				senderInfoMap.put(sender, new HashMap<>()); //보낸사람 닉네임(key)
				Map<String, Object> senderInfo = senderInfoMap.get(sender); 
				senderInfo.put("cnt", dao.notReadCntSep(nickname,sender)); //읽지 않은 메세지
				senderInfo.put("pic", mdao.selectOneNick(sender).getPicture()); //사진
			}
			
			String myPic = mdao.selectOneNick(nickname).getPicture(); //채팅방 내 내 사진
			String yourPic = mdao.selectOneNick(receiver).getPicture(); //채팅방 내에서 상대방 사진
			
			request.setAttribute("nickname",nickname);
			request.setAttribute("receiver",receiver);
			request.setAttribute("senders", senders);		
			request.setAttribute("msgs",msgs);
			request.setAttribute("notReadCnt", notReadCnt);	
			request.setAttribute("senderInfoMap",senderInfoMap);
			request.setAttribute("myPic", myPic);
			request.setAttribute("yourPic", yourPic);
			return "messenger/msgForm";
		}
	}

	@MSLogin("loginCheck")
	@RequestMapping("msg")
	public String msg(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//메세지 보내는 부분
		String nickname = (String)request.getSession().getAttribute("nickname");
		String receiver = request.getParameter("receiver");
		if(receiver.equals("운영자")) {
		request.setAttribute("msg", "운영자에게는 쪽지를 보낼 수 없습니다.");
		request.setAttribute("url", "msgForm");
		return "alert";
	    }
		String content = request.getParameter("content");
		Messenger messenger = new Messenger();
		messenger.setSender(nickname);
		messenger.setReceiver(receiver);
		messenger.setContent(content);	
		dao.insert(messenger);	
		
		List<Messenger> msgs = dao.selectMsgs(receiver,nickname);
		List<String> senders = dao.selectSenders(nickname);
		int notReadCnt = dao.notReadCnt(nickname);
		
		String myPic = mdao.selectOneNick(nickname).getPicture();
		String yourPic = mdao.selectOneNick(receiver).getPicture();
		
		Map<String, Map<String, Object>> senderInfoMap = new HashMap<>();
		for(String sender : senders) {
			senderInfoMap.put(sender, new HashMap<>());
			Map<String, Object> senderInfo = senderInfoMap.get(sender);
			senderInfo.put("cnt", dao.notReadCntSep(nickname,sender));
			senderInfo.put("pic", mdao.selectOneNick(sender).getPicture());
		}
		
		request.setAttribute("nickname",nickname);
		request.setAttribute("receiver",receiver);
		request.setAttribute("senders", senders);
		request.setAttribute("msgs",msgs);
		request.setAttribute("notReadCnt", notReadCnt);		
		request.setAttribute("myPic", myPic);
		request.setAttribute("yourPic", yourPic);
		request.setAttribute("senderInfoMap",senderInfoMap);
		return "send";
	}
	
	@MSLogin("loginCheck")
	@RequestMapping("out")
	public String out(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String nickname = (String)request.getSession().getAttribute("nickname");
		String receiver = request.getParameter("receiver");
		dao.delete(receiver, nickname);
		return "redirect:msgForm";
//		request.setAttribute("receiver", receiver);
//		request.setAttribute("nickname",nickname);
//		return "send";
	}
	
	@RequestMapping("search")
	public String search(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String nickname = request.getParameter("nickname");
		Member mem = mdao.selectOneNick(nickname);
		if(nickname.equals("운영자")) {
			request.setAttribute("msg", "운영자에게는 쪽지를 보낼 수 없습니다.");
			request.setAttribute("url", "msgForm");
			return "alert";
		} else if(mem==null) {
			request.setAttribute("msg", "존재하지 않는 닉네임 입니다.");
			request.setAttribute("url", "msgForm");
			return "alert";
		} 
		request.setAttribute("receiver", nickname);
		request.setAttribute("nickname",nickname);
		return "send";
	}
}
