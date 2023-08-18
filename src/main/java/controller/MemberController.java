package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MSLogin;
import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Board;
import model.Member;
import model.MemberMybatisDao;
import model.MessengerMybatisDao;

@WebServlet(urlPatterns = {"/member/*"},
						initParams = {@WebInitParam(name="view", value="/view/")})
public class MemberController extends MskimRequestMapping{
	private MemberMybatisDao dao = new MemberMybatisDao();
	private MessengerMybatisDao mdao = new MessengerMybatisDao();
	
	public String loginCheck(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException { // @MSLogin annotation에 있는 거랑 이름 똑같아야함.
		request.setCharacterEncoding("UTF-8");
		String email = request.getParameter("email");
		String login = (String)request.getSession().getAttribute("login");
		if(login==null || login.equals("")) {
			request.setAttribute("msg", "로그인하세요.");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else if(!login.equals("admin") && !email.equals(login)) {
			request.setAttribute("msg" , "본인만 접근 가능합니다.");
			request.setAttribute("url", "/first_prj/index");
			return "alert";
		}
		return null;
	}
	
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
	
	@RequestMapping("joinForm")
	public String joinForm(HttpServletRequest request, HttpServletResponse response) {
		boolean able=true;
		request.setAttribute("able", able);
		return "member/joinForm";
	}
	
	@RequestMapping("join")
	public String join(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Member mem = new Member();
		String emailaddress = request.getParameter("email1")+"@"+request.getParameter("email2");
		System.out.println(request.getParameter("email1"));
		System.out.println(request.getParameter("email2"));
		System.out.println(emailaddress);
		mem.setEmailaddress(emailaddress);
		mem.setPassword(request.getParameter("pass"));
		mem.setPicture(request.getParameter("picture"));
		mem.setNickname(request.getParameter("nickname"));
		if(dao.insert(mem)) {
			request.setAttribute("msg", "회원가입 성공");
			request.setAttribute("url", "loginForm");
			return "alert";
		} else {
			request.setAttribute("msg", "회원가입 실패");
			request.setAttribute("url", "joinForm");
			return "alert";
		}				
	}
	
	//가입시 이메일 인증
	@RequestMapping("emailForm")
	public String emailForm(HttpServletRequest request, HttpServletResponse respnose) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		//가입되어있는지 확인
		String email = request.getParameter("email");
		boolean able = false;		
		if(dao.selectOneEmail(email)!=null) {
				request.setAttribute("msg", "이미 가입되어있는 이메일 입니다.");
				return "self_close";
		} else {
				//비밀번호 찾을 때
				String inputedEmail = request.getParameter("email");
				//인증번호 랜덤 생성
			    String randomkey = authCodeMaker();
				// 발신자 정보
				String sender = "zxc2289@naver.com";
				String password = "";
				
				// 메일 받을 주소
				String recipient = inputedEmail;
				System.out.println("inputedEmail : " + inputedEmail);
				Properties prop = new Properties();
				   try {
					   FileInputStream fis = new FileInputStream("D:\\java_gdu_workspace\\first_prj\\mail.properties"); //파일의 내용(mail.properties)을 읽기 위한 스트림
					   prop.load(fis);
					   prop.put("mail.smtp.user", sender);
					   System.out.println(prop);
				   } catch(IOException e) {
					   e.printStackTrace();
				   }
				Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(sender, password);
					}
				});
				MimeMessage msg = new MimeMessage(session);
					
					// email 전송
				try {
					try {
							msg.setFrom(new InternetAddress(sender,"SHOERACE 인증센터","UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

						// 메일 제목
						msg.setSubject("이메일 인증");
						// 메일 내용
						msg.setText(randomkey);
						Transport.send(msg);
						System.out.println("이메일 전송 : " + randomkey);

					} catch (AddressException e) { 
						e.printStackTrace(); 
					} catch (MessagingException e) { 
						e.printStackTrace(); 
					}
				request.getSession().setAttribute("randomkey", randomkey);
				request.getSession().removeAttribute("fromEmail2");
				return "member/emailForm";
		}
	}
	
	//비밀번호 변경 시 이메일 인증
	@RequestMapping("emailPwForm") 
	public String emailPwForm(HttpServletRequest request, HttpServletResponse respnose) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		//가입되어있는지 확인
		String email = request.getParameter("email");
		String resend = request.getParameter("resend");
		request.setAttribute("email", email);
		boolean able = false;		
		if(email==null || email.equals("")) {
			return "member/emailPwForm";
		} else if(dao.selectOneEmail(email)==null) {
			request.setAttribute("msg", "가입되지 않은 이메일입니다.");
			request.getSession().removeAttribute("firstTry");
			return "self_close";
		} else if(resend != null){ //메일 2번 보내는거 방지
			return "member/emailPwForm";
		} else {
				String inputedEmail = request.getParameter("email");
				//인증번호 랜덤 생성
			    String randomkey = authCodeMaker();
				// 발신자 정보
				String sender = "zxc2289@naver.com";
				String password = "";
				
				// 메일 받을 주소
				String recipient = inputedEmail;
				System.out.println("inputedEmail : " + inputedEmail);
				Properties prop = new Properties();
				   try {
					   FileInputStream fis = new FileInputStream("D:\\java_gdu_workspace\\first_prj\\mail.properties"); //파일의 내용(mail.properties)을 읽기 위한 스트림
					   prop.load(fis);
					   prop.put("mail.smtp.user", sender);
					   System.out.println(prop);
				   } catch(IOException e) {
					   e.printStackTrace();
				   }
				Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(sender, password);
					}
				});
				MimeMessage msg = new MimeMessage(session);
					
					// email 전송
				try {
					try {
							msg.setFrom(new InternetAddress(sender,"SHOERACE 인증센터","UTF-8"));
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
						msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

						// 메일 제목
						msg.setSubject("이메일 인증");
						// 메일 내용
						msg.setText(randomkey);
						Transport.send(msg);
						System.out.println("이메일 전송 : " + randomkey);

					} catch (AddressException e) { 
						e.printStackTrace(); 
					} catch (MessagingException e) { 
						e.printStackTrace(); 
					}
				request.getSession().setAttribute("randomkey", randomkey);
				return "member/emailPwForm";
		} 
			
	}
	
	 //인증번호 생성 함수
	   public String authCodeMaker() {
			String authCode = null;
			
			StringBuffer temp = new StringBuffer();
			Random random = new Random();
			for (int i = 0; i < 10; i++) {
				int rIndex = random.nextInt(3);
				switch (rIndex) {
				case 0:
					// a-z
					temp.append((char) ((int) (random.nextInt(26)) + 97));
					break;
				case 1:
					// A-Z
					temp.append((char) ((int) (random.nextInt(26)) + 65));
					break;
				case 2:
					// 0-9
					temp.append((random.nextInt(10)));
					break;
				}
			}
			
			authCode = temp.toString();
			System.out.println(authCode);
			
			return authCode;
		}
	   
	   @RequestMapping("emailFormchk") //인증번호 확인
		public String emailFormchk(HttpServletRequest request, HttpServletResponse respnose) {
		   String autoNum = request.getParameter("autoNum");
		   String randomkey = (String)request.getSession().getAttribute("randomkey");
		   String pwchg = request.getParameter("pwchg");
		   boolean able = true;
		   if(!autoNum.equals(randomkey)) {
			   request.setAttribute("msg", "인증번호가 틀립니다.");
			   request.getSession().removeAttribute("firstTry");
			   request.setAttribute("url", "emailForm");
			   return "alert";
		   } else {
			   able=true;
			   if(pwchg==null || pwchg.equals("")) {
				   request.setAttribute("able", able);
				   return "member/emailForm";
			   } else {
				   request.setAttribute("able", able);
				   request.setAttribute("pwchg", pwchg);
				   return "member/emailForm";
			   }			   
		   }
	   }
	   
	   @RequestMapping("emailPwchk") //인증번호 확인
		public String emailPwchk(HttpServletRequest request, HttpServletResponse respnose) {
		   String autoNum = request.getParameter("autoNum");
		   String randomkey = (String)request.getSession().getAttribute("randomkey");
		   String email = request.getParameter("email");
		   boolean able = true;
		   if(!autoNum.equals(randomkey)) {
			   request.setAttribute("msg", "인증번호가 틀립니다.");
			   request.setAttribute("url", "emailPwForm?email="+email+"&resend=1");
			   return "alert";
		   } else {			   
			   return "member/pwChgForm";			   
		   }
	   }
	   
	   @RequestMapping("nickChk")
	   public String nickChk(HttpServletRequest request, HttpServletResponse response) {
		   try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		   String nickname = request.getParameter("nickname");
		   Member mem = dao.selectOneNick(nickname);
		   request.setAttribute("mem" , mem);
		   return "member/nickChk";	 
	   }
	   
	   @RequestMapping("picture")
	   public String picture(HttpServletRequest request, HttpServletResponse response) {
		   try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		   String path = request.getServletContext().getRealPath("/") + "/upload/member"; 
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
		   return "member/picture";
	   }
	   
	   @RequestMapping("login")
	   public String login(HttpServletRequest request, HttpServletResponse response) {
		   String email = request.getParameter("id");
		   String pass = request.getParameter("pass");
		   Member mem = dao.selectOneEmail(email);
		   
		   String msg = null;
		   String url = null;
		   if(mem == null) {
			   msg="아이디를 확인하세요";
			   url="loginForm";
		   } else if(!pass.equals(mem.getPassword())) {
			   msg="비밀번호가 틀립니다.";
			   url="loginForm";
		   } else {
			   request.getSession().setAttribute("login", email);
			   String nickname = mem.getNickname();
			   int unreadMsg = mdao.notReadCnt(nickname);
			   request.getSession().setAttribute("nickname", nickname);			   
			   request.getSession().setAttribute("unreadMsg", unreadMsg);
			   msg="반갑습니다. " + mem.getNickname() + "님";
			   url="/first_prj/index";
		   }
		   request.setAttribute("msg", msg);
		   request.setAttribute("url", url);
		   return "alert";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("myPage")
	   public String myPage(HttpServletRequest request, HttpServletResponse response) {
		   String email = request.getParameter("email");
		   Member mem = dao.selectOneEmail(email);
		   String nickname = mem.getNickname();
		   int myBoardCnt = dao.myBoardCnt(nickname);
		   int myCommCnt = dao.myCommCnt(nickname);
		   request.setAttribute("mem", mem);
		   request.setAttribute("myBoardCnt", myBoardCnt);
		   request.setAttribute("myCommCnt", myCommCnt);
		   return "member/myPage";
	   }
	   
	   @RequestMapping("logout")
	   public String logout(HttpServletRequest request, HttpServletResponse response) {
		   request.getSession().invalidate();
		   request.setAttribute("msg", "로그아웃 되었습니다.");
		   request.setAttribute("url", "/first_prj/index");
		   return "alert";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("updateForm")
	   public String updateForm(HttpServletRequest request, HttpServletResponse response) {
		   try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		   String email = request.getParameter("email");
		   if(email==null || email.equals("")) {
			   request.setAttribute("msg","잘못된 접근입니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   }
		   Member mem = dao.selectOneEmail(email);
		   request.setAttribute("mem", mem);
		   return "member/updateForm";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("update")
	   public String update(HttpServletRequest request, HttpServletResponse response) {
		   try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		   String login = (String)request.getSession().getAttribute("login");
		   Member mem = new Member();
		   Member dbMem = dao.selectOneEmail(login);
		   String email1 = request.getParameter("email1");
		   String email2 = request.getParameter("email2");
		   mem.setEmailaddress(email1 + "@" + email2);
		   mem.setNickname(request.getParameter("nickname"));
		   mem.setPicture(request.getParameter("picture"));
		   mem.setPassword(request.getParameter("pass"));
		   Member member = dao.selectOneEmail(email1 + "@" + email2);
		   if(login.equals("admin")) {
		  	 mem.setPassword(dbMem.getPassword());
		  	 if(!mem.getPassword().equals(dbMem.getPassword())) {
				   request.setAttribute("msg", "비밀번호가 틀립니다.");
				   request.setAttribute("url", "updateForm?email="+mem.getEmailaddress());
				   return "alert";
			   } else {
				   if(dao.update(mem)) {
					   request.setAttribute("msg", "회원정보 수정 완료");
					   request.setAttribute("url", "myPage?email="+mem.getEmailaddress());
					   return "alert";
				   } else {
					   request.setAttribute("msg", "회원정보 수정 실패");
					   request.setAttribute("url", "updateForm?email="+mem.getEmailaddress());
					   return "alert";
				   }
			   }
		   } else {
		  	 if(!mem.getPassword().equals(dbMem.getPassword())) {
				   request.setAttribute("msg", "비밀번호가 틀립니다.");
				   request.setAttribute("url", "updateForm?email="+mem.getEmailaddress());
				   return "alert";
			   } else {
				   if(dao.update(mem)) {
					   request.setAttribute("msg", "회원정보 수정 완료");
					   request.setAttribute("url", "myPage?email="+mem.getEmailaddress());
					   return "alert";
				   } else {
					   request.setAttribute("msg", "회원정보 수정 실패");
					   request.setAttribute("url", "updateForm?email="+mem.getEmailaddress());
					   return "alert";
				   }
			   }
		   }		   
	   }
	   
	   @RequestMapping("deleteForm")
	   @MSLogin("loginCheck")
	   public String deleteForm(HttpServletRequest request, HttpServletResponse response) {  
		   String login = (String)request.getSession().getAttribute("login");
		   String email = request.getParameter("email");
		   if(email==null || email.equals("")) {
			   request.setAttribute("msg","잘못된 접근입니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   } else if(email.equals("admin")) {
			   request.setAttribute("msg", "관리자는 탈퇴가 불가능합니다.");
			   request.setAttribute("url", "myPage?email="+email);
			   return "alert";
		   }
		   
		   return "member/deleteForm";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("delete")
	   public String delete(HttpServletRequest request, HttpServletResponse response) {
		   String login = (String)request.getSession().getAttribute("login");		   
		   String pass = request.getParameter("pass");
		   String email = request.getParameter("email");
		   System.out.println(email);
		   Member dbMem = dao.selectOneEmail(email);
		   Member adminMem = dao.selectOneEmail(login);
		   if(email.equals("admin")) {
			   request.setAttribute("msg", "관리자는 탈퇴가 불가능합니다.");
			   request.setAttribute("url", "myPage?email="+email);
			   return "alert";
		   }
		   if(login.equals("admin")) {
			   if(!adminMem.getPassword().equals(pass)) {
				   request.setAttribute("msg", "비밀번호가 다릅니다.");
				   request.setAttribute("url", "deleteForm?email="+email);
				   return "alert";
			   } else {
				   if(dao.delete(email)) {
					   request.setAttribute("msg", "회원탈퇴 완료");
					   request.setAttribute("url", "/first_prj/index");
					   return "alert";
				   } else {
					   request.setAttribute("msg", "회원탈퇴 실패");
					   request.setAttribute("url", "/first_prj/index");
					   return "alert";
				   }
			   }
		   } else {
			   if(!dbMem.getPassword().equals(pass)) {
				   request.setAttribute("msg", "비밀번호가 다릅니다.");
				   request.setAttribute("url", "deleteForm?email="+email);
				   return "alert";
			   } else {
				   if(dao.delete(email)) {
					   request.setAttribute("msg", "회원탈퇴 완료");
					   request.setAttribute("url", "/first_prj/index");
					   return "alert";
				   } else {
					   request.setAttribute("msg", "회원탈퇴 실패");
					   request.setAttribute("url", "/first_prj/index");
					   return "alert";
				   }
			   }
		   }		  
	   }
	   
	   //비밀번호 찾기 loginForm->pwChgForm->
	   @RequestMapping("password1")
	   public String password1(HttpServletRequest request, HttpServletResponse response) {
		   String pass = request.getParameter("pass");	
		   String email = request.getParameter("email");
		   if(dao.updatePass(email,pass)) {
			   request.setAttribute("msg", "비밀번호가 수정되었습니다. 변경된 비밀번호로 다시 로그인하세요.");
			   request.setAttribute("url", "loginForm");
			   return "opener";
		   } else {
			   request.setAttribute("msg", "비밀번호 수정 실패.");
			   request.setAttribute("url", "pwChgForm");
			   return "alert";
		   }
	   }
	   
	   //비밀번호 수정 updateForm->pwChgUpdateForm->
	   @RequestMapping("password2")
	   public String password2(HttpServletRequest request, HttpServletResponse response) {
		   String pass = request.getParameter("pass1");
		   String currentPass = request.getParameter("currentPass");
		   String email = request.getParameter("email");
		   Member dbMem = dao.selectOneEmail(email);
		   if(!dbMem.getPassword().equals(currentPass)) {
			   request.setAttribute("msg", "비밀번호가 틀립니다.");
			   request.setAttribute("url", "pwChgUpdateForm?email="+email);
			   return "alert";
		   } else {
			   if(dao.updatePass(email, pass)) {
				   request.getSession().invalidate();
				   request.setAttribute("msg", "비밀번호가 수정되었습니다. 변경된 비밀번호로 다시 로그인하세요.");
				   request.setAttribute("url", "loginForm");
				   return "opener";
			   } else {
				   request.setAttribute("msg", "비밀번호 수정 실패.");
				   request.setAttribute("url", "pwChgUpdateForm");
				   return "alert";
			   }
		   }
	   }
	   
	   @MSLogin("loginAdminCheck")
	   @RequestMapping("userList")
	   public String userList(HttpServletRequest request, HttpServletResponse response) {
		   try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   request.getSession().setAttribute("pageNum","1");
		   int pageNum = 1;
		   try {
			   pageNum = Integer.parseInt(request.getParameter("pageNum")); 
		   } catch(NumberFormatException e) {}
		   
		   String nickname = request.getParameter("nickname");
		   int memberCount = dao.memberCount(nickname);
		   if(nickname==null || nickname.trim().equals("")) {
			   nickname=null;
		   }
		   int limit=10;
		   int maxPage = (int)((double)memberCount/limit + 0.95);
		   int startPage = ((int)(pageNum/10.0 + 0.9) -1)*10 +1; 
		   int endPage = startPage + 9; 
		   if(endPage > maxPage) endPage = maxPage;
		   
		   List<Member> list = dao.list(pageNum, limit, nickname);
		   
		   int memberNum = 1 + (pageNum -1)*limit;		   
		   
		   request.setAttribute("list", list);
		   request.setAttribute("memberCount", memberCount);
		   request.setAttribute("memberNum", memberNum);
		   request.setAttribute("startPage",startPage);
		   request.setAttribute("endPage",endPage);
		   request.setAttribute("maxPage",maxPage);
		   request.setAttribute("nickname", nickname);
		   request.setAttribute("pageNum",pageNum);
		   return "member/userList";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("myBoardList")
	   public String myBoardList(HttpServletRequest request, HttpServletResponse response) {
		   try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   String nickname = request.getParameter("nickname");
		   String sessionNick = (String)request.getSession().getAttribute("nickname");
		   if(nickname == null || nickname.equals("")) {
			   request.setAttribute("msg","잘못된 접근입니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   } else if(!sessionNick.equals("운영자") && !nickname.equals(sessionNick) ) {
			   request.setAttribute("msg","접근 권한이 없습니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   } 
		   int myBoardCnt = dao.myBoardCnt(nickname);
		   
		   request.getSession().setAttribute("pageNum","1");
		   int pageNum = 1;
		   try {
				 pageNum = Integer.parseInt(request.getParameter("pageNum")); 
		   } catch(NumberFormatException e) {}
		   int limit=10;
		   int maxPage = (int)((double)myBoardCnt/limit + 0.95);
		   int startPage = ((int)(pageNum/10.0 + 0.9) -1)*10 +1; 
		   int endPage = startPage + 9; 
		   if(endPage > maxPage) endPage = maxPage;
		   
		   int myBoardNum = 1 + (pageNum -1)*limit;
		   
		   List<Board> boardList = dao.boardList(pageNum, limit, nickname);
		   
		   request.setAttribute("boardList", boardList);
		   request.setAttribute("myBoardCnt", myBoardCnt);
		   request.setAttribute("startPage",startPage);
		   request.setAttribute("endPage",endPage);
		   request.setAttribute("maxPage",maxPage);
		   request.setAttribute("myBoardNum", myBoardNum);
		   request.setAttribute("pageNum",pageNum);
		   request.setAttribute("nickname", nickname);
		   return "member/myBoardList";
	   }
	   
	   @MSLogin("loginCheck")
	   @RequestMapping("myCommList")
	   public String myCommList(HttpServletRequest request, HttpServletResponse response) {
		   try {
				request.setCharacterEncoding("UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		   String email = request.getParameter("email");
		   String nickname = request.getParameter("nickname");
		   String sessionNick = (String)request.getSession().getAttribute("nickname");
		   if(nickname == null || nickname.equals("")) {
			   request.setAttribute("msg","잘못된 접근입니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   } else if(!sessionNick.equals("운영자") && !nickname.equals(sessionNick) ) {
			   request.setAttribute("msg","접근 권한이 없습니다.");
			   request.setAttribute("url","/first_prj/index");
			   return "alert";
		   }
		   
		   int myCommCnt = dao.myCommCnt(nickname);
		   
		   request.getSession().setAttribute("pageNum","1");
		   int pageNum = 1;
		   try {
			   pageNum = Integer.parseInt(request.getParameter("pageNum")); 
		   } catch(NumberFormatException e) {}
		
		   int limit=10;
		   int maxPage = (int)((double)myCommCnt/limit + 0.95);
		   int startPage = ((int)(pageNum/10.0 + 0.9) -1)*10 +1; 
		   int endPage = startPage + 9; 
		   if(endPage > maxPage) endPage = maxPage;
		   
		   int myCommNum = 1 + (pageNum -1)*limit;
		   
		   List<Board> commList = dao.commList(pageNum, limit, nickname);
		   
		   request.setAttribute("commList", commList);
		   request.setAttribute("myCommCnt", myCommCnt);
		   request.setAttribute("startPage",startPage);
		   request.setAttribute("endPage",endPage);
		   request.setAttribute("maxPage",maxPage);
		   request.setAttribute("myCommNum", myCommNum);
		   request.setAttribute("pageNum",pageNum);
		   request.setAttribute("nickname", nickname);
		   request.setAttribute("email", email);
		   return "member/myCommList";
	   }
}
