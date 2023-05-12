<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<style type="text/css">
	/* info.jsp에 들어갈 css속성임 */
	#title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: -2vh;
  }
  .container{width: 70vw;}
  .container.form{ 
    padding-top: 5vh;
    display: flex;
    justify-content: space-evenly;
  }
  .btn, h4{font-family: 'Dongle', sans-serif; font-size: 1.2em;}
  #pic{border-radius: 50%;}
  .form-group{width: 35vw;}
  .form-control, .form-select{display: inline-block;}
  #usr, #sel{width:15vw;}
  .mt-3 .btn{font-size: 24px;}
  .mt-3 .btn:hover {color: lightgray;}
</style>
</head>
<body>
	<h1 id="title">마이페이지</h1>

  <!-- About Section -->
  <div class="container form">
    <!-- 왼쪽 사진등록 구역 -->
    <div class="be-light">
      <input type="hidden" name="picture" value="">
      <c:if test="${mem.picture=='basic-profile.JPG' }">
      <img src="${path }/images/basic-profile.JPG" width="200" height="200" id="pic"><br>
      </c:if>
      <c:if test="${mem.picture != 'basic-profile.JPG' }">
      	<img src="/first_prj/upload/member/${mem.picture}" width="200" height="200" id="pic"><br>
      </c:if>      
    </div>
    <!-- 오른쪽 아이디/비번/닉네임 입력구역-->
    <div>
      <!-- 이메일 -->
      <div class="form-group mb-3">
        <label class="mb-1" for="email">이메일</label>
        <div class="input-group mb-3">
        	<c:set var="email" value="${mem.emailaddress}" />
          	<c:set var="split" value="@" />
            <input type="text" class="form-control" id="email1" name="email1" readonly value="${fn:substringBefore(email,split) }">
            <span class="input-group-text">@</span>
            <input type="text" class="form-control" id="email2" name="email2" readonly value="${fn:substringAfter(email,split) }" >
        </div>
      </div>
      
      <!-- 닉네임 -->
      <div class="form-group">
        <label class="mb-1" for="nickname">닉네임</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control" name="nickname" id="nickname" readonly value="${mem.nickname }">
        </div>
      </div>
      
      <h4 class="mt-4">가입일자 : <fmt:formatDate value="${mem.regdate}" pattern="yyyy년 MM월 dd일"/></h4>

      <div class="mt-3">
        <a class="btn" href="myBoardList?email=${mem.emailaddress }&nickname=${mem.nickname }"><font size="5">게시글 : ${myBoardCnt }개</font></a>
        <br>
        <a class="btn" href="myCommList?email=${mem.emailaddress }&nickname=${mem.nickname }"><font size="5">댓글 : ${myCommCnt }개</font></a>
      </div>
    </div>

  </div>
  <br>
  <!-- 회원가입 / 초기화 -->
  <div class="container mt-3" align="center">
    <a class="btn" href="updateForm?email=${sessionScope.login }">수정</a>
    <a class="btn ms-2" href="deleteForm?email=${sessionScope.login }">회원탈퇴</a>
    <c:if test="${sessionScope.login =='admin' }">
    	<a class="btn ms-2" href="userList">회원리스트</a>
    </c:if>
  </div>

</body>
</html>