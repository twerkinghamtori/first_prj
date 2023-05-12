<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
	1. 이미지파일 업로드 request 객체 사용불가
	2. opener 화면에 결과 전달 => javascript
	3. 현재화면 close() => javascript
 --%>

<script>
	const img = opener.document.getElementById("pic");
	img.src = "/first_prj/upload/member/${fname}";
	opener.document.f.picture.value="${fname}";
	self.close();
</script>

