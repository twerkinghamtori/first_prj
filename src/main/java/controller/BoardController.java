package controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

import gdu.mskim.MskimRequestMapping;
import gdu.mskim.RequestMapping;
import model.Board;
import model.BoardDetailView;
import model.BoardListView;
import model.BoardMybatisDao;
import model.Comment;
import model.CommentListView;
import model.CommentMybatisDao;
import model.Member;
import model.MemberMybatisDao;

@WebServlet(urlPatterns = {"/board/*"},
initParams = {@WebInitParam(name="view", value="/view/")}
		)
public class BoardController extends MskimRequestMapping{
	private BoardMybatisDao dao = new BoardMybatisDao();
	private CommentMybatisDao cDao = new CommentMybatisDao();
	private MemberMybatisDao mDao = new MemberMybatisDao();
	private static String boardName(String boardType) {
		String boardName = "";
		switch (boardType){
			case "1" : boardName = "자유게시판"; break;
			case "3" : boardName = "후기게시판"; break;
			case "4" : boardName = "공지사항"; break;
		}
		return boardName;
	}
	
	@RequestMapping("commList")
	public String commList(HttpServletRequest request, HttpServletResponse response) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		Member mem = new MemberMybatisDao().selectOneNick(nickname);
		int no = Integer.parseInt(request.getParameter("no"));
		
		String pageNum_ = request.getParameter("pageNum");
		int pageNum = 1;
		
		if(pageNum_ != null && !pageNum_.equals("")) {
			try{
				pageNum = Integer.parseInt(pageNum_);
			}catch (Exception e) {e.printStackTrace();}
		}
		int commCnt = 0;
		commCnt = cDao.commCnt(no);
		
		int limit = 10;
		int maxPage = (int)((double)commCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;
		
		List<CommentListView> commList = cDao.list(no, pageNum, limit);
		
		request.setAttribute("commList", commList);
		request.setAttribute("commCnt", commCnt);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("mem", mem);
		request.setAttribute("today", new Date());
		
		return "board/commList";
	}
	
	@RequestMapping("commDel")
	public String commDel(HttpServletRequest request, HttpServletResponse response) {
		
		int no = Integer.parseInt(request.getParameter("no"));
		int seq = Integer.parseInt(request.getParameter("seq"));
		String nickname = (String)request.getSession().getAttribute("nickname");
		
		Comment comm = cDao.selectOne(no,seq);
		
		String url = "detail?no=" +no+"&hit=f";
		String msg = "잘못된 접근입니다.";
		
		if(!nickname.equals(comm.getNickname()) && !nickname.equals("admin")) {
			msg = "다른회원의 댓글은 삭제 불가능합니다.";
		}else {
			if(cDao.delete(no, seq)) {
				return "redirect:"+url;
			}else {
				msg = "댓글 삭제 오류 발생";
			}
		}
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	@RequestMapping("commReply")
	public String commeReply(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		int no = Integer.parseInt(request.getParameter("no"));
		String url = "detail?no=" + no+"&hit=f";
		
		// 대댓글 객체 생성
		Comment comm = new Comment();
		comm.setNo(no);
		comm.setNickname(request.getParameter("nickname"));
		comm.setContent(request.getParameter("content"));
		comm.setGrp(Integer.parseInt(request.getParameter("grp")));
		comm.setGrpLevel(Integer.parseInt(request.getParameter("grpLevel")));
		comm.setGrpStep(Integer.parseInt(request.getParameter("grpStep")));
		
		// 대댓글 스텝 추가
		cDao.grpStepAdd(comm.getGrp(), comm.getGrpStep());
		
		// grp수정
		int grpLevel = comm.getGrpLevel(); 
		int grpStep = comm.getGrpStep();
		int seq = cDao.maxSeq(no);
		comm.setSeq(++seq);
		comm.setGrpLevel(grpLevel+1);
		comm.setGrpStep(grpStep+1);
		
		// 입력
		if(cDao.insert(comm)) {
			return "redirect:" + url; 
		}
		request.setAttribute("msg", "댓글등록 실패");
		request.setAttribute("url", url);
		return "alert";
	}
	
	@RequestMapping("comment")
	public String comment(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		int no = Integer.parseInt(request.getParameter("no"));
		String url = "detail?no=" + no+"&hit=f";
		Comment comm = new Comment();
		comm.setNo(no);
		comm.setNickname(request.getParameter("nickname"));
		comm.setContent(request.getParameter("content"));
		int seq = cDao.maxSeq(no);
		comm.setSeq(++seq);
		comm.setGrp(seq);
		
		if(cDao.insert(comm)) {
			return "redirect:" + url ; 
		}
		request.setAttribute("msg", "댓글 등록실패");
		request.setAttribute("url", url);
		return "alert";
	}
	
	@RequestMapping("writeForm")
	public String writeForm(HttpServletRequest request, HttpServletResponse response) {
		String boardType = request.getParameter("boardType");
		String login = (String)request.getSession().getAttribute("login");
		if(boardType == null || boardType.equals("")) boardType = "1";
		request.getSession().setAttribute("boardType", boardType);
		if(login == null) {	// 로그인 상태가 아닐 때
			request.setAttribute("msg", "비회원은 게시글 작성이 불가능합니다.");
			request.setAttribute("url", "list?boardType="+boardType);
			return "alert";		
		}
		Member mem = mDao.selectOneEmail(login);
		request.getSession().setAttribute("nickname", mem.getNickname());
		
		if(boardType.equals("4") && !login.equals("admin")){	// 공지사항 글쓰기로 들어왔는데 admin이 아닐 경우 
			request.setAttribute("msg", "일반회원은 공지사항 게시글은 작성이 불가능합니다.");
			request.setAttribute("url", "list?boardType="+boardType);
			return "alert";
		}
		String boardName = boardName(boardType);
		request.setAttribute("boardName", boardName);
		
		return "board/writeForm";
	}
	
	@RequestMapping("write")
	public String write(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String nickname = (String)request.getSession().getAttribute("nickname");
		String boardType = (String)request.getSession().getAttribute("boardType");
		if(boardType == null) boardType = "1";
		
		Board b = new Board();
		b.setTitle(request.getParameter("title"));
		System.out.println(b.getTitle());
		b.setNickname(nickname);
		b.setContent(request.getParameter("content"));
		b.setBoardType(boardType);

		if(dao.insert(b)){
			request.setAttribute("msg", "게시물 등록 성공");
			request.setAttribute("url", "list?boardType="+boardType);
			return "alert";
		}
		
		request.setAttribute("msg", "게시물 등록 실패");
		request.setAttribute("url", "writeForm");
		return "alert";
	}
	
	@RequestMapping("list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		if(login == null) login = "";
		if(request.getParameter("boardType") != null){
			// session에 게시판종류 정보등록
			request.getSession().setAttribute("boardType", request.getParameter("boardType"));
			request.getSession().setAttribute("pageNum", "1");	// 현재 페이지 정보
		}

		// 전체글 / 추천게시글
		String excep_mode = "";
		String excep_mode_ = request.getParameter("excep_mode");
		if(excep_mode_!= null && !excep_mode_.equals("")) excep_mode = excep_mode_;
		
		String boardType = (String)request.getSession().getAttribute("boardType");
		if(boardType == null || boardType.equals("")) {
			boardType = "1";
			request.getSession().setAttribute("boardType", boardType);
		}
		String pageNum_ = request.getParameter("pageNum");
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		
		int pageNum = 1;
		String field = "title+content";
		String query = "";
		
		if(field_ != null && !field_.equals("")) field = field_;
		if(query_ != null && !query_.equals("")) query = query_;
		if(pageNum_ != null && !pageNum_.equals("")) {
			try{
				pageNum = Integer.parseInt(pageNum_);
			}catch (Exception e) {e.printStackTrace();}
		}
		
		int limit = 10;	// 한 페이지에 보여질 게시물 건 수
		int boardCnt = 0;
		
		// 운영자리스트 / 일반회원 리스트
		boolean isAdmin = login.equals("admin")? true : false;
		boardCnt = dao.boardCount(boardType, field, query, isAdmin, excep_mode);
		List<BoardListView> list = dao.list(boardType, pageNum, limit, field, query, isAdmin, excep_mode);	// (문자열, 정수, 정수)
		List<String> imgSrc = new ArrayList<>();
		List<Integer> imgCnt = new ArrayList<>();
		for(BoardListView b : list) {
		    String regex = "<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>";
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(b.getContent());
		    int count = 0;
		    while (matcher.find()) {
		    	if(count == 0) {
		    		imgSrc.add(matcher.group(1));
		    	}
		        count++;
		    }
		    imgCnt.add(count-1);
		    if(count == 0) {
		        imgSrc.add(null);
		    }
		}
		request.setAttribute("boardCnt", boardCnt);
		request.setAttribute("list", list);
		request.setAttribute("imgSrc", imgSrc);
		request.setAttribute("imgCnt", imgCnt);
		
		// 맨 위 상단 공지글 2개
		List<Board> nList = dao.nList();
		if(nList != null) {
			int nCnt = nList.size();
			request.setAttribute("nList", nList);
			request.setAttribute("nCnt", nCnt);
		}
		
		// 운영자 프사등록
		Member admin = new MemberMybatisDao().selectOneNick("운영자");
		request.setAttribute("adminPicture", admin.getPicture());
		
		int maxPage = (int)((double)boardCnt/limit +0.95);
		int startPage = pageNum-(pageNum-1)%5;
		int endPage = startPage + 4;
		if(endPage > maxPage) endPage = maxPage;
		
		int boardNum = boardCnt-(pageNum-1) * limit;
		
		request.setAttribute("boardNum", boardNum);
		request.setAttribute("boardType", boardType);
		request.setAttribute("boardName", boardName(boardType));
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("maxPage", maxPage);
		request.setAttribute("today", new Date());
		
		return "board/list";
	}
	
	// 공개여부
	@RequestMapping("public")
	public String pub(HttpServletRequest request, HttpServletResponse response) {
		String login = (String)request.getSession().getAttribute("login");
		
		if(login == null || !login.equals("admin")) {	// 로그인 상태가 아니거나 운영자가 아닐 때
			request.setAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("url", "list");
			return "alert";		
		}
		
		String[] openNos = new String[0];
		String[] openNos_ = request.getParameterValues("noChks");	// 체크(공개)된 게시글 번호
		if(openNos_ != null) openNos = openNos_; 
		String nos_ = request.getParameter("nos");	// 전체번호
		String[] nos = nos_.trim().split(" ");	// 공백단위로 잘라서 집어넣음
		
		List<String> oids = Arrays.asList(openNos);		// 공개된 게시글번호
		
		List<String> cids = new ArrayList(Arrays.asList(nos));		
		cids.removeAll(oids);		// cids : 비공개 게시글 번호
		
		if(dao.pubBoardAll(oids, cids)) {
			return "redirect:list";
		}
		
		request.setAttribute("msg", "공개여부 과정에서 문제가 생겼습니다.");
		request.setAttribute("url", "list");
		return "alert";	
	}
	
	//상세페이지
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, HttpServletResponse response) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		int no = Integer.parseInt(request.getParameter("no"));
		
		String hit = request.getParameter("hit");
		
		// 댓글쓰기 조회수 늘어나기 방지용
		if(hit == null || !hit.equals("f")) 
			dao.HitAdd(no);
	
		Member mem = new MemberMybatisDao().selectOneNick(nickname);
		
		BoardDetailView b = dao.selectOne(no);
		BoardDetailView bNext = dao.selectNext(b);
		BoardDetailView bPrevious = dao.selectPrevious(b);
		String boardName = boardName(b.getBoardType());
		
		int commCnt = cDao.commCnt(no);
	
		request.setAttribute("boardName", boardName);
		request.setAttribute("b", b);
		request.setAttribute("bNext", bNext);
		request.setAttribute("bPrevious", bPrevious);
		request.setAttribute("mem", mem);
		request.setAttribute("today", new Date());
		request.setAttribute("commCnt", commCnt);
		
		return "board/detail";
	}
	
	@RequestMapping("imgupload")
	public String imgupload(HttpServletRequest request, HttpServletResponse response) {
		String uploadPath = request.getServletContext().getRealPath("/") + "/upload/imgfile/"; // 절대경로
		File f = new File(uploadPath);
		if(!f.exists()) f.mkdirs();
		int size = 1024*1024*10;
		MultipartRequest multi = null;
		try {
			multi = new MultipartRequest(request, uploadPath, size, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileName = multi.getFilesystemName("upload");
		request.setAttribute("fileName", fileName);
		
		return "ckeditor";
	}
	
	@RequestMapping("updateForm")
	public String updateForm(HttpServletRequest request, HttpServletResponse response) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		String login = (String)request.getSession().getAttribute("login");
		String boardType = (String)request.getSession().getAttribute("boardType");
		
		int no = Integer.parseInt(request.getParameter("no"));
		BoardDetailView b = dao.selectOne(no);
		
		if(login == null) {
			request.setAttribute("msg", "잘못된 접근입니다.");
			request.setAttribute("url", "list?boardType="+boardType);
			return "alert";		
		}else if(b.getBoardType().equals("4") && !login.equals("admin")) {
			request.setAttribute("msg", "공지사항은 운영자만 수정 가능합니다.");
			request.setAttribute("url", "detail?no="+b.getNo());
			return "alert";		
		}else if(!login.equals("admin") && !nickname.equals(b.getNickname())) {
			request.setAttribute("msg", "본인이 작성한 글만 수정이 가능합니다.");
			request.setAttribute("url", "detail?no="+b.getNo());
			return "alert";	
		}
		
		request.setAttribute("boardName", boardName(b.getBoardType()));
		request.setAttribute("b", b);
		
		return "board/updateForm";
	}
	
	@RequestMapping("update")
	public String update(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Board b = new Board();
		b.setNo(Integer.parseInt(request.getParameter("no")));
		b.setTitle(request.getParameter("title"));
		b.setContent(request.getParameter("content"));
		
		String msg = "";
		String url = "detail?no=" + b.getNo();
		
		if(dao.update(b)){
			msg = "게시물 수정 완료";
		} else{
			msg = "게시물 수정 실패";
			url = "updateForm?no="+b.getNo();
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String nickname = (String)request.getSession().getAttribute("nickname");
		String login = (String)request.getSession().getAttribute("login");
		String boardType = (String)request.getSession().getAttribute("boardType");
		
		int no = Integer.parseInt(request.getParameter("no"));
		BoardDetailView b = dao.selectOne(no);
		
		String msg = "게시글이 삭제 되었습니다.";
		String url = "list?boardType="+boardType;
		
		if(login == null) {
			msg = "잘못된 접근입니다.";
		}else if(boardType.equals("4") && !login.equals("admin")) {
			msg = "공지사항은 운영자만 삭제 가능합니다.";
			url = "detail?no="+no;
		}else if(!login.equals("admin") && !nickname.equals(b.getNickname())) {
			msg = "본인이 작성한 글만 삭제 가능합니다.";
			url = "detail?no="+no;
		}else {
			if(!dao.delete(no)) {
				msg = "삭제 실패";
				url = "detail?no="+no;
			}
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		return "alert";
	}
}
