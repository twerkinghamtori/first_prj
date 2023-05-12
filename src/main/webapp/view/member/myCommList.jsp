<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 댓글</title>
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
  tr th:nth-child(2) {width: 40vw;}
  tr th:nth-child(3) {width: 8vw;}
</style>
</head>
<body>
	 <!-- About Section -->
  <div class="container">
    <h1 id="title"><a href="myCommList?email=${email }&nickname=${nickname}">내가 쓴 댓글</a></h1>

    <h3>내가 쓴 댓글 <span id="cnt">${myCommCnt }</span>개</h3>
    <table class="table table-hover">

      <thead>
        <tr class="table-dark">
          <th scope="col">게시글 번호</th>
          <th scope="col">내용</th>
          <th scope="col">작성일</th>
        </tr>
      </thead>

      <tbody>
      	<c:forEach var="c" items="${commList }">
        <tr>
          <th class="fw-bold"><span class="blue">${myCommNum }</span></th>
          <c:set var="myCommNum" value="${myCommNum+1 }" />
          <td><a href="/first_prj/board/detail?no=${c.no }">${c.content }</a></td>
          <td><fmt:formatDate value="${c.regdate}" pattern="yyyy년 MM월 dd일"/></td>
        </tr>
        </c:forEach>
      </tbody>

    </table>

    <!-- Pagination -->
    <div class="w3-center w3-padding-32">
      <div class="w3-bar">
       <c:if test="${pageNum <=1}">
       		<a href="" class="w3-bar-item w3-button">&laquo;</a>
      	</c:if>
      	<c:if test="${pageNum >1}">
      		<a href="myCommList?pageNum=${pageNum-1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&laquo;</a>
      	</c:if>
      	
      	<c:forEach var="a" begin="${startPage }" end="${endPage }">
      			<a href="myCommList?pageNum=${a }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">${a }</a>
      	</c:forEach>
      	
        <c:if test="${pageNum >= maxPage }">
        	<a href="" class="w3-bar-item w3-button">&raquo;</a>
        </c:if>
        <c:if test="${pageNum < maxPage }">
        	<a href="myCommList?pageNum=${pageNum+1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&raquo;</a>
        </c:if>
      </div>
    </div>
  </div>
</body>
</html>