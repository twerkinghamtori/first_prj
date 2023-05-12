<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script>
	function listsubmit(page) {
		document.f.pageNum.value = page;
		document.f.submit();		
	}
</script>
<title>회원목록</title>
<style type="text/css">
	/* userList.jsp에 들어갈 css속성임 */
  body{font-size: 12px;}
  .container>div>div, .btn.btn-dark{font-family: 'Dongle', sans-serif; font-size:1.5em;}
  #title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: 7vh;
  }
  .btn{font-size: 12px;}
  a{text-decoration: none;}
  .container{width: 60vw;}
  .table-form {display: flex;}
  .table-form .input-group * {height: 30px; font-size: 12px;}
  tr th:nth-child(1){width: 8vw;}
  tr th:nth-child(2) {width: 6vw;}
  tr th:nth-child(3) {width: 8vw;}
  tr th:nth-child(4) {width: 6vw;}
  tr th:nth-child(5){width: 10vw;}
  #profile{width: 30px; height: 30px; border-radius: 50%;}
  /* ************************************ */
</style>
</head>
<body>
 <!-- About Section -->
  <div class="container">
    <h1 id="title"><a href="userList">회원목록</a></h1>

    <div style="display: flex; justify-content: space-between; margin-bottom: -7px;">
      <div>회원 수:${memberCount }</div>
      <form action="userList" class="table-form" name="f">
      	<input type="hidden" name="pageNum" value="1">
        <div class="input-group mb-3 ms-1">
          <input type="text" class="form-control" name="nickname" placeholder="닉네임 검색">
          <button class="btn btn-outline-secondary" type="submit" id="button-addon2"><i class="fa fa-search"></i></button>
        </div>
      </form>
    </div>
    
    <table class="table table-hover text-center">
      <thead>
        <tr class="table-dark">
          <th>no</th>
          <th>이메일</th>
          <th>사진</th>
          <th>닉네임</th>
          <th>가입일자</th>
          <th>수정 / 탈퇴</th>
        </tr>
      </thead>

      <tbody>
        <c:forEach var="m" items="${list }">
        <tr>
          <td>${memberNum }</td>
          <c:set var="memberNum" value="${memberNum+1 }" />
          
          <td scope="row">${m.emailaddress }</td>
          <c:if test="${m.picture=='basic-profile.JPG' }">
      		<td><img src="${path }/images/basic-profile.JPG" id="profile"></td>
      	  </c:if>
     	  <c:if test="${m.picture != 'basic-profile.JPG' }">
      		<td><img src="/first_prj/upload/member/${m.picture}"  id="profile"></td>
      	  </c:if>    
          <td><a href="myPage?email=${m.emailaddress }">${m.nickname }</a></td>
          <td><fmt:formatDate value="${m.regdate}" pattern="yyyy년 MM월 dd일"/></td>
          <td>
            <a class="btn btn-dark" href="updateForm?email=${m.emailaddress }">수정</a>
            <!-- 관리자 일 때 -->
            <c:if test="${m.emailaddress == 'admin' }">
            	
            </c:if>
            <c:if test="${m.emailaddress != 'admin' }">
             <a class="btn btn-dark"href="deleteForm?email=${m.emailaddress }">강제탈퇴</a>
            </c:if>            
          </td>
        </tr>
		</c:forEach>
      </tbody>
    </table>
    <!-- 페이징 -->
    <div class="w3-center w3-padding-32">
      <div class="w3-bar">
      	<c:if test="${pageNum <=1}">
       		<a href="" class="w3-bar-item w3-button">&laquo;</a>
      	</c:if>
      	<c:if test="${pageNum >1}">
      		<a href="userList?pageNum=${pageNum-1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&laquo;</a>
      	</c:if>
      	
      	<c:forEach var="a" begin="${startPage }" end="${endPage }">
      			<a href="userList?pageNum=${a }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">${a }</a>
      	</c:forEach>
      	
        <c:if test="${pageNum >= maxPage }">
        	<a href="" class="w3-bar-item w3-button">&raquo;</a>
        </c:if>
        <c:if test="${pageNum < maxPage }">
        	<a href="userList?pageNum=${pageNum+1 }&nickname=${param.nickname}" class="w3-bar-item w3-button w3-hover-black">&raquo;</a>
        </c:if>        
      </div>
    </div>
  </div>
</body>
</html>