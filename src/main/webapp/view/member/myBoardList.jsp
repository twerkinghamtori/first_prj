<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 게시글</title>
<style type="text/css">
  body{font-size: 12px;}
  #title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: 7vh;
  }
  h3{font-family: 'Dongle', sans-serif;}
  #cnt{color : red;}
  a{text-decoration: none;}
  .container{width: 70vw;}
  .input-group * {height: 30px; font-size: 12px;}
  tr th{text-align: center;}
  tr td:not(:nth-child(2)){text-align: center;}
  tr th:nth-child(1) {width: 5vw;}
  tr th:nth-child(2) {width: 20vw;}
  tr th:nth-child(3) {width: 8vw;}
  tr th:nth-child(4) {width: 6vw;}
  tr th:nth-child(5) {width: 5vw;}
</style>
</head>
<body>
	<!--  
		내가 만들어놓은 BoardListView에서 where nickname = ? 조건문 걸어서 쓰면 될 듯
	-->

	<!-- About Section -->
  <div class="container">
    <h1 id="title"><a href="myBoardList?email=${email }&nickname=${nickname}">내가 쓴 게시글</a></h1>

		<h3>내가 쓴 게시글 <span id="cnt">${myBoardCnt }</span>개</h3>
    <table class="table table-hover">
		
      <thead>
        <tr class="table-dark">
          <th scope="col">번호</th>
          <th scope="col">제목</th>
          <th scope="col">작성일</th>
          <th scope="col">조회수</th>
          <th scope="col">추천</th>
        </tr>
      </thead>

      <tbody>
      <c:if test="${myBoardCnt==0 }">
   			<tr>
      			<td colspan="5"> 등록된 게시글이 없습니다.</td>
   			</tr>
   		</c:if>
   	   <c:if test="${myBoardCnt != 0 }">
      	<c:forEach var="b" items="${boardList }">
        <tr>
          <th class="fw-bold"><span class="blue">${myBoardNum }</span></th>
          <c:set var="myBoardNum" value="${myBoardNum+1 }" />
          <td><a href="/first_prj/board/detail?no=${b.no }">${b.title }</a></td>
          <td><fmt:formatDate value="${b.regdate}" pattern="yyyy년 MM월 dd일"/></td>
          <td>${b.hit }</td>
          <td>${b.recommend }</td>
        </tr>
        </c:forEach>
        </c:if>
      </tbody>
    </table>

    <!-- Pagination -->
    <div class="w3-center w3-padding-32">
      <div class="w3-bar">
        <c:if test="${pageNum <=1}">
       		<a href="" class="w3-bar-item w3-button">&laquo;</a>
      	</c:if>
      	<c:if test="${pageNum >1}">
      		<a href="myBoardList?pageNum=${pageNum-1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&laquo;</a>
      	</c:if>
      	
      	<c:forEach var="a" begin="${startPage }" end="${endPage }">
      			<a href="myBoardList?pageNum=${a }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">${a }</a>
      	</c:forEach>
      	
        <c:if test="${pageNum >= maxPage }">
        	<a href="" class="w3-bar-item w3-button">&raquo;</a>
        </c:if>
        <c:if test="${pageNum < maxPage }">
        	<a href="myBoardList?pageNum=${pageNum+1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&raquo;</a>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>