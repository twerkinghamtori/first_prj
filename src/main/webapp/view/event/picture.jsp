<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script>
	self.close();
	const img = opener.document.getElementById("pic");
	img.src = "/first_prj/upload/event/${fname}";
	opener.document.f.picture.value="${fname}";	
</script>

