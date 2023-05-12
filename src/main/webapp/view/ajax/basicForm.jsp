<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
	function basicPicUpdate() {
		document.f.picture.value = "basic-profile.JPG";
		$("#pic").attr("src","/first_prj/images/basic-profile.JPG")
	}
</script>
<input type="hidden" name="picture" value="${picture}">
    <c:if test="${picture=='basic-profile.JPG' }">
      	<img src="/first_prj/images/basic-profile.JPG" width="200" height="200" id="pic"><br>
    </c:if>
    <c:if test="${picture != 'basic-profile.JPG' }">
      	<img src="/first_prj/upload/member/${picture}" width="200" height="200" id="pic"><br>
    </c:if>
    
    <div align="center">
       <font size="1"><a href="javascript:win_upload()">사진등록</a></font>
        <br>
        <font size="1"><span id="basic" style="text-decoration:underline;" onclick="basicPicUpdate()">기본이미지로 변경</span></font>
    </div>