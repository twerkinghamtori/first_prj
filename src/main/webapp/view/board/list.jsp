<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardName }</title>
<style type="text/css">
 	body{font-size: 12px;}
  #title, h4{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: 7vh;
  }
  a{text-decoration: none;}
  .container{width: 80vw;}
  .orange{color:rgb(255, 102, 0);}
  .blue{color:blue;}
  #write{width:70px; height: 30px; font-size: 16px; padding: 0; font-family: 'Dongle', sans-serif;}
  .table-form {display: flex;}
  .table-form #sel{width: 130px; height: 30px; font-size: 12px;}
  .table-form .input-group * {height: 30px; font-size: 12px;}
  tr th{text-align: center;}
  tr td:not(:nth-child(2)){text-align: center;}
  tr th:nth-child(1) {width: 5vw;}
  tr th:nth-child(2) {width: 30vw;}
  tr th:nth-child(3) {width: 10vw;}
  tr th:nth-child(4) {width: 6vw;}
  tr th:nth-child(5) {width: 5vw;}
  tr th:nth-child(6) {width: 5vw;}
  tr th:nth-child(7) {width: 2vw;}
  #profile{width: 16px; height: 16px; border-radius: 50%;}
  .notice{background-color:lightgray;}
  #cnt{padding:0px; font-family: 'Dongle', sans-serif; font-size:25px;}
  #best{font-weight : 900; color : green;}
  .msg, .msg-1{
  	position:absolute;
  	border:1px solid black;
  	background-color:black; 
  	color:white; 
  	padding:5px;
  	box-shadow: 2px 2px 5px rgba(0,0,0,0.5);
  	display:none;
  	cursor: pointer;
  }
  .msg{
  	top:60%; 
  	left:60%; 
  }
  .msg-1{
  	top:20%; 
  	left:40%; 
  }
  .name{cursor: pointer;}
  .msg:hover, .msg-1:hover{
  	background-color: grey;
  	border:1px solid grey;
  }
  /* ************************************ */
  .pic{
    border-radius: 50%;
    width: 35px; 
    height: 35px;
    margin : 5px 15px 5px 5px
  }
  .info_1{
    padding: 5px;
    font-size : 1.4em;
  }
  .info_2{
    display: flex;
  }
  .w3-col.l2.s4 {
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.5);
    margin: 5px 10px;
    position: relative;
    font-family: 'Dongle', sans-serif;
    font-size : 14px;
    width: 18%;
  }
  .w3-row div a { font-size : 20px;}
</style>
 <script type="text/javascript">
  	function goWriteForm() {
			location.href="writeForm?boardType=${sessionScope.boardType}" 
		}
  	function goWriteNoticeForm() {
			location.href="writeForm?boardType=4" 
		}
	 $(function(){
			$('#allChk').click(function(){
				$('.noChk').prop('checked', $(this).is(':checked'));
			});
		});
	 $(()=>{
		 $('.item .name').on('click', function(e) {
			    e.preventDefault();
			    $('.msg, .msg-1').not($(this).next('.msg, .msg-1')).hide();
			    $(this).next('.msg, .msg-1').toggle();
			  });

			  $(document).on('click', function(e) {
			    if(!$(e.target).closest('.item').length) {
			      $('.msg, .msg-1').hide();
			    }
			  });
	 })
</script>
</head>
<body>
	 <!-- About Section -->
  <div class="container">
    <h1 id="title">${boardName }</h1>

    <div style="display: flex; justify-content: space-between; margin-bottom: -7px;">

      <div id="cnt">
      	<%-- <span style="color:red">${empty boardCnt? 0 : boardCnt}</span>개의 글 --%>
     		<a type="button" href="list" class="btn btn-success">전체글</a>
     		<a type="button" href="list?excep_mode=recomm" class="btn btn-success">추천글</a>
      </div>

      <form class="table-form">
      	<input type="hidden" name="excep_mode" value="${param.excep_mode}">
        <select id="sel" class="form-select" name="f">
          <option ${(param.f == "title+content") ? "selected" : ""} value="title+content">제목+내용</option>
          <option ${(param.f == "nickname") ? "selected" : ""} value="nickname">작성자</option>
        </select>
        <div class="input-group mb-3 ms-1">
          <input type="text" class="form-control" name="q" placeholder="검색어" value="${param.q }">
          <button class="btn btn-outline-secondary" type="submit" id="button-addon2"><i class="fa fa-search"></i></button>
        </div>
      </form>
    </div>
    
    <c:if test="${boardType != 3 }"> <%-- 후기게시판이 아닐 때  --%>
		<form method="post" action="public"> <%-- 일괄공개여부(운영자전용)  --%>
    <table class="table table-hover">
			
      <thead>
        <tr class="table-dark">
          <th scope="col">번호</th>
          <th scope="col">제목</th>
          <th scope="col">글쓴이</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
          <th scope="col">추천</th>
          
          <c:if test="${sessionScope.login == 'admin'}">
          	<th scope="col"><input type="checkbox" class="form-check-input" name="allChk"  id="allChk"></th>
          </c:if>
        </tr>
      </thead>
			
      <tbody>
      
      	<!-- 공지사항 고정글 1개 + 이벤트 당첨글 1개 -->
      	<!-- 공지 글이 없을 때 -->
      	<c:if test="${boardType != 4 }">
		      <c:if test="${nCnt <= 0}">
						<tr><td colspan="7">등록된 공지사항이 없습니다.</td></tr>
					</c:if>
					<!-- 공지 글이 있을 때 -->
					
					<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" var="t"/>

					<c:if test="${nCnt > 0}">
						<c:set var="cnt" value="${nCnt==1? 0:nCnt }"/>
						<c:forEach var="n" items="${nList}" begin="0" end="${cnt}">
			      	<tr class="notice">
			          <th class="fw-bold"><span class="blue">공지</span></th>
			          
			          <c:set var="title" value="${n.title }" />
			          <c:if test="${fn:length(b.title) >= 35 }">
								  <c:set var="title" value="${fn:substring(n.title, 0, 35)}..." />
								</c:if>
			          
			          <td class="fw-bold"><a href="detail?no=${n.no }">${title}</a></td>
			          <td class="fw-bold">
			          	<c:if test="${adminPicture == null }">
			          		<img id="profile" src="${path }/images/basic-profile.JPG">
			          	</c:if>
			          	<c:if test="${adminPicture != null }">
			          		<img id="profile" src="/first_prj/upload/member/${adminPicture}">
			          	</c:if>
			          		운영자
		          	</td>
		          	
		          	<fmt:formatDate value="${n.regdate }" pattern="yyyy-MM-dd" var="r2"/>
			          <c:if test="${t eq r2}">
									<td><fmt:formatDate value="${n.regdate }" pattern="HH:mm"/></td>
								</c:if>
								<c:if test="${t != r2}">
									<td><fmt:formatDate value="${n.regdate }" pattern="yyyy-MM-dd"/></td>
								</c:if>
							
			          <td>${n.hit }</td>
			          <td></td>
			          <c:if test="${sessionScope.login == 'admin'}"><td></td></c:if>
			        </tr>
			        </c:forEach>
	        </c:if>
        </c:if>
        <!-- 게시판 글이 없을 때 -->
	      <c:if test="${boardCnt <= 0}">
					<tr><td colspan="7">등록된 게시글이 없습니다.</td></tr>
				</c:if>
				
				<c:set var="nos" value=""/>
				<!-- 게시판 글이 있을 때 -->
				<c:if test="${boardCnt > 0}">
					<c:forEach var="b" items="${list }" varStatus="st">
					
					<c:set var="nos" value="${nos } ${b.no }"/>
					
					<c:if test="${b.pub == 1 || sessionScope.login == 'admin' }">
		        <tr>
		          <td scope="row">${boardNum}</td>	<c:set var="boardNum" value="${boardNum-1 }"/>
		          <td>
		          
	          		<c:if test="${imgSrc[st.index] != null}">
									<i class="fa fa-picture-o" aria-hidden="true"></i>	          		
		          	</c:if>
		          	
		          	<c:set var="title" value="${b.title }" />
			          <c:if test="${fn:length(b.title) >= 35 }">
								  <c:set var="title" value="${fn:substring(b.title, 0, 35)}..." />
								</c:if>
		          	
		          	<c:if test="${b.recommend >= 5}">
		          		<a id="best" href="detail?no=${b.no }"><span class="badge rounded-pill bg-success">Best</span> ${title }</a>  <!--  👍 -->
		          	</c:if>
		          	<c:if test="${b.recommend < 5}">
		          		<a href="detail?no=${b.no }">${title }</a>
		          	</c:if>
		          	
		          	<c:if test="${b.commCnt != 0}"> 
		          		<a href="detail?no=${b.no }#comment" class="orange">[${b.commCnt }]</a>
		          	</c:if>
		          </td>
		          <td style="position:relative" class="item">
		          	<c:if test="${b.picture == 'basic-profile.JPG' }">
		          		<img id="profile" src="${path }/images/basic-profile.JPG">
		          	</c:if>
		          	<c:if test="${b.picture != 'basic-profile.JPG' }">
		          		<img id="profile" src="/first_prj/upload/member/${b.picture}">
		          	</c:if>
		          	<span class="name">${b.nickname }</span>
	          		<span class="msg" onclick="location.href='/first_prj/messenger/msgForm?receiver=${b.nickname}'"><i class="fa fa-comments-o" aria-hidden="true"></i>&nbsp;쪽지</span>
		          </td> 
		          
		          <fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd" var="r"/>
							<c:if test="${t eq r}">
								<td><fmt:formatDate value="${b.regdate }" pattern="HH:mm"/></td>
							</c:if>
							<c:if test="${t != r}">
								<td><fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd"/></td>
							</c:if>
							
		          <td>${b.hit}</td>
		          <td>${b.recommend}</td>
		          <c:if test="${sessionScope.login == 'admin'}">
			          	<td><input type="checkbox" class="form-check-input noChk" name="noChks"  value="${b.no}" ${b.pub ==1? "checked" : "" }></td>
			        </c:if>
		        </tr>
	        </c:if>
		        
	        </c:forEach>
        </c:if>
        
      </tbody>

    </table>
    

		<!-- 공지사항으로 들어오면 글 못쓰는데 어드민이면 공지글쓰기 가능 -->
    <div align="right">
    	<c:if test="${sessionScope.boardType != 4}">
    		<button id="write" type="button" class="btn btn-dark" onclick="goWriteForm()">글쓰기</button>
    	</c:if>
    	<c:if test="${sessionScope.login == 'admin'}">
    		<input type="hidden" name="nos" value="${nos}">
    		<button id="write" type="button" class="btn btn-dark" onclick="goWriteNoticeForm()">공지사항 작성</button>
    		<button id="write" type="submit" class="btn btn-dark" onclick="goWriteNoticeForm()">일괄공개/비공개</button>
    	</c:if>
    </div>
		</form>
		</c:if>
	
	
	<%-- ---------------------------------------------------------------------------------------------------------------------------- --%>
	<%-- ---------------------------------------------------------------------------------------------------------------------------- --%>
	<%-- ---------------------------------------------------------------------------------------------------------------------------- --%>
	
	
		<c:if test="${boardType == 3 }">
			<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" var="t"/>
			<form method="post" action="public">
			
			<table class="table table-hover">
			
      <thead>
        <tr class="table-dark">
          <th scope="col">번호</th>
          <th scope="col">제목</th>
          <th scope="col">글쓴이</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
          
          <c:if test="${sessionScope.login == 'admin'}">
          	<th scope="col"><input type="checkbox" class="form-check-input" name="allChk"  id="allChk"></th>
          </c:if>
        </tr>
      </thead>
			
      <tbody>
      
      	<!-- 공지사항 고정글 1개 + 이벤트 당첨글 1개 -->
      	<!-- 공지 글이 없을 때 -->
      	<c:if test="${boardType != 4 }">
		      <c:if test="${nCnt <= 0}">
						<tr><td colspan="7">등록된 공지사항이 없습니다.</td></tr>
					</c:if>
					<!-- 공지 글이 있을 때 -->
					<c:if test="${nCnt > 0}">
						<c:set var="cnt" value="${nCnt==1? 0:nCnt }"/>
						<c:forEach var="n" items="${nList}" begin="0" end="${cnt}">
			      	<tr class="notice">
			          <th class="fw-bold"><span class="blue">공지</span></th>
			          
			          <c:set var="title" value="${n.title }" />
			          <c:if test="${fn:length(b.title) >= 35 }">
								  <c:set var="title" value="${fn:substring(n.title, 0, 35)}..." />
								</c:if>
			          
			          <td class="fw-bold"><a href="detail?no=${n.no }">${title}</a></td>
			          <td class="fw-bold">
			          	<c:if test="${adminPicture == null }">
			          		<img id="profile" src="${path }/images/basic-profile.JPG">
			          	</c:if>
			          	<c:if test="${adminPicture != null }">
			          		<img id="profile" src="/first_prj/upload/member/${adminPicture}">
			          	</c:if>
			          		운영자
		          	</td>
		          	
		          	<fmt:formatDate value="${n.regdate }" pattern="yyyy-MM-dd" var="r2"/>
			          <c:if test="${t eq r2}">
									<td><fmt:formatDate value="${n.regdate }" pattern="HH:mm"/></td>
								</c:if>
								<c:if test="${t != r2}">
									<td><fmt:formatDate value="${n.regdate }" pattern="yyyy-MM-dd"/></td>
								</c:if>
							
			          <td>${n.hit }</td>
			          <c:if test="${sessionScope.login == 'admin'}"><td></td></c:if>
			        </tr>
			        </c:forEach>
	        </c:if>
        </c:if>
        </tbody>
        </table>
			
				<c:if test="${boardCnt <= 0}">
					<h4 class="text-center">등록된 게시물이 없습니다.</h4>
				</c:if>
				
				<c:set var="nos" value=""/>
				<!-- 게시판 글이 있을 때 -->
				<c:if test="${boardCnt > 0}">
					<div class="w3-row">
						<c:forEach var="b" items="${list }" varStatus="st">
						
						<c:set var="nos" value="${nos } ${b.no }"/>
						
						<c:if test="${b.pub == 1 || sessionScope.login == 'admin' }">
			      	<c:if test="${st.index <5 }">
						    <div class="w3-col l2 s4">
						      <div class="w3-display-container">
						      	<c:if test="${imgSrc[st.index] != null}">
						        	<a href="detail?no=${b.no }"><img src="${imgSrc[st.index] }" style="width:100%; height: 225px;"></a>
						        </c:if>
						        <c:if test="${imgSrc[st.index] == null}">
						        	<a href="detail?no=${b.no }"><img src="${path }/images/basic-thumb.jpg" style="width:100%; height: 225px;"></a>
						        </c:if>
						        <c:if test="${imgCnt[st.index] > 0 }">
						        	<span class="w3-tag w3-display-topright">+${imgCnt[st.index]}장의 사진</span>
						        </c:if>
						      </div>
						      <div class="info_1">
						        <c:set var="title" value="${b.title }" />
			          <c:if test="${fn:length(b.title) >= 15 }">
								  <c:set var="title" value="${fn:substring(b.title, 0, 15)}..." />
								</c:if>
		          	
		          	<c:if test="${b.recommend >= 5}">
		          		<div><a id="best" href="detail?no=${b.no }"><span class="badge rounded-pill bg-success">Best</span> ${title }</a></div>  <!--  👍 -->
		          	</c:if>
		          	<c:if test="${b.recommend < 5}">
		          		<div><a href="detail?no=${b.no }">${title }</a></div>
		          	</c:if>
		          	<hr style="margin: 5px 0; background-color: rgba(0,0,0,0.7);">
						        <div class="info_2 item" style="position:relative">
						          <div>
						          	<c:if test="${b.picture == 'basic-profile.JPG' }">
						          		<img class="pic" src="${path }/images/basic-profile.JPG">
						          	</c:if>
						          	<c:if test="${b.picture != 'basic-profile.JPG' }">
						          		<img class="pic" src="/first_prj/upload/member/${b.picture}">
						          	</c:if>
						          </div>
						          <div>
						          	<span class="name">${b.nickname }</span>
	          						<span class="msg-1" onclick="location.href='/first_prj/messenger/msgForm?receiver=${b.nickname}'"><i class="fa fa-comments-o" aria-hidden="true"></i>&nbsp;쪽지</span> 
						          	<br> 
						          	<fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd" var="r"/>
												<c:if test="${t eq r}">
													<fmt:formatDate value="${b.regdate }" pattern="HH:mm"/>
												</c:if>
												<c:if test="${t != r}">
													<fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd"/>
												</c:if>
												<br>
												<c:if test="${sessionScope.login == 'admin'}">
								          <input type="checkbox" class="form-check-input noChk" name="noChks"  value="${b.no}" ${b.pub ==1? "checked" : "" }>
								        </c:if>
						          </div>
						        </div>
						        <hr style="margin: 5px 0; background-color: rgba(0,0,0,0.7);">
						        <div style="display: flex; justify-content: space-around;">
						          <div style="color:${b.recommend>=5? 'green' : 'black'}"><i class="fa fa-thumbs-o-up"></i> ${b.recommend }</div>
						          <div><i class="fa fa-comment-o" aria-hidden="true"></i> ${b.commCnt }</div>
						          <div><i class="fa fa-eye" aria-hidden="true"></i> ${b.hit }</div>
						        </div>
						      </div>
						    </div>
						  </c:if>
		        </c:if>
			        
		        </c:forEach>
	        </div>
	        <%-- 줄바뀜 --%>
	        <div class="w3-row mt-3">
	        <c:forEach var="b" items="${list }" varStatus="st">
					
					<c:if test="${b.pub == 1 || sessionScope.login == 'admin' }">
		        <c:if test="${st.index >=5 }">
		        <c:set var="nos" value="${nos } ${b.no }"/>
						       <div class="w3-col l2 s4">
						      <div class="w3-display-container">
						        <c:if test="${imgSrc[st.index] != null}">
						        	<a href="detail?no=${b.no }"><img src="${imgSrc[st.index] }" style="width:100%; height: 225px;"></a>
						        </c:if>
						        <c:if test="${imgSrc[st.index] == null}">
						        	<a href="detail?no=${b.no }"><img src="${path }/images/basic-thumb.jpg" style="width:100%; height: 225px;"></a>
						        </c:if>
						        <c:if test="${imgCnt[st.index] != 0 }">
						        	<span class="w3-tag w3-display-topright">+${imgCnt[st.index]}장의 사진</span>
						        </c:if>
						      </div>
						      <div class="info_1">
						        <c:set var="title" value="${b.title }" />
			          <c:if test="${fn:length(b.title) >= 15 }">
								  <c:set var="title" value="${fn:substring(b.title, 0, 15)}..." />
								</c:if>
		          	
		          	<c:if test="${b.recommend >= 5}">
		          		<div><a id="best" href="detail?no=${b.no }"><span class="badge rounded-pill bg-success">Best</span> ${title }</a></div>  <!--  👍 -->
		          	</c:if>
		          	<c:if test="${b.recommend < 5}">
		          		<div><a href="detail?no=${b.no }">${title }</a></div>
		          	</c:if>
		          	<hr style="margin: 5px 0; background-color: rgba(0,0,0,0.7);">
						        <div class="info_2 item" style="position:relative">
						          <div>
						          	<c:if test="${b.picture == 'basic-profile.JPG' }">
						          		<img class="pic" src="${path }/images/basic-profile.JPG">
						          	</c:if>
						          	<c:if test="${b.picture != 'basic-profile.JPG' }">
						          		<img class="pic" src="/first_prj/upload/member/${b.picture}">
						          	</c:if>
						          </div>
						          <div>
						          	<span class="name">${b.nickname }</span>
	          						<span class="msg-1" onclick="location.href='/first_prj/messenger/msgForm?receiver=${b.nickname}'"><i class="fa fa-comments-o" aria-hidden="true"></i>&nbsp;쪽지</span> 
						          	<br> 
						          	<fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd" var="r"/>
												<c:if test="${t eq r}">
													<td><fmt:formatDate value="${b.regdate }" pattern="HH:mm"/></td>
												</c:if>
												<c:if test="${t != r}">
													<td><fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd"/></td>
												</c:if>
						          </div>
						        </div>
						        <hr style="margin: 5px 0; background-color: rgba(0,0,0,0.7);">
						        <div style="display: flex; justify-content: space-around;">
						          <div style="color:${b.recommend>=5? 'green' : 'black'}; font-weight: bold;"><i class="fa fa-thumbs-o-up"></i> ${b.recommend }</div>
						          <div><i class="fa fa-comment-o" aria-hidden="true"></i> ${b.commCnt}</div>
						          <div ><i class="fa fa-eye" aria-hidden="true"></i> ${b.hit }</div>
						          <c:if test="${sessionScope.login == 'admin'}">
							          	<div><input type="checkbox" class="form-check-input noChk" name="noChks"  value="${b.no}" ${b.pub ==1? "checked" : "" }></div>
							        </c:if>
						        </div>
						      </div>
						    </div>
						  </c:if>
	        </c:if>
		        
	        </c:forEach>
	        </div>
        </c:if>
				
				<!-- 공지사항으로 들어오면 글 못쓰는데 어드민이면 공지글쓰기 가능 -->
    <div align="right" class="mt-5">
    	<c:if test="${sessionScope.boardType != 4}">
    		<button id="write" type="button" class="btn btn-dark" onclick="goWriteForm()">글쓰기</button>
    	</c:if>
    	<c:if test="${sessionScope.login == 'admin'}">
    		<input type="hidden" name="nos" value="${nos}">
    		<button id="write" type="button" class="btn btn-dark" onclick="goWriteNoticeForm()">공지사항 작성</button>
    		<button id="write" type="submit" class="btn btn-dark" onclick="goWriteNoticeForm()">일괄공개/비공개</button>
    	</c:if>
    </div>
			</form>
		</c:if>
      <!-- paging -->
		  <div class="w3-center w3-padding-32">
		    <div class="w3-bar">
			    <c:if test="${pageNum<= 1}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('이전 페이지가 없습니다.');">&laquo;</a>
					</c:if>
					<c:if test="${pageNum > 1}">
						<a class="w3-bar-item w3-button w3-hover-black" href="list?pageNum=${pageNum-1}&f=${param.f}&q=${param.q}&excep_mode=${param.excep_mode}">&laquo;</a>
					</c:if>
					
					<c:forEach var="a" begin="${startPage}" end="${endPage}">
						<c:if test="${a <= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="list?pageNum=${a}&f=${param.f}&q=${param.q}&excep_mode=${param.excep_mode}">${a}</a>
						</c:if>
					</c:forEach>
						
					<c:if test="${startPage+4 >= maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
					</c:if>
					<c:if test="${startPage+4 < maxPage}">
						<a class="w3-bar-item w3-button w3-hover-black" href="list?pageNum=${startPage+5}&f=${param.f}&q=${param.q}&excep_mode=${param.excep_mode}">&raquo;</a>
					</c:if>
		    </div>
		  </div>
    
  </div>
</body>
</html>