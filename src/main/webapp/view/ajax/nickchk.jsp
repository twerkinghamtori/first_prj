<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
	<c:when test="${emptyChk=='emptyChk' }">
		<span>닉네임을 입력하세요.</span>

		<script type="text/javascript">
			nickname = document.querySelector("#nickname");
			nickname.classList.remove("is-valid");
			nickname.classList.add("is-invalid");
			nickMsg = document.querySelector("#nickMsg");
			nickMsg.classList.remove("valid-feedback");
			nickMsg.classList.add("invalid-feedback");
			
		</script>
	</c:when>
	<c:when test="${able }">
		<span>사용가능한 닉네임입니다.</span>

		<script type="text/javascript">
			nickname = document.querySelector("#nickname");
			nickname.classList.remove("is-invalid");
			nickname.classList.add("is-valid");
			nickMsg = document.querySelector("#nickMsg");
			nickMsg.classList.remove("invalid-feedback");
			nickMsg.classList.add("valid-feedback");
			$("#nicknamechkchk").val("nicknamechecked")
		</script>   
	</c:when>	
	<c:otherwise>
		<span>사용중인 닉네임입니다.</span>
 
		<script type="text/javascript">
			nickname = document.querySelector("#nickname");
			nickname.classList.remove("is-valid");
			nickname.classList.add("is-invalid");
			nickMsg = document.querySelector("#nickMsg");
			nickMsg.classList.remove("valid-feedback");
			nickMsg.classList.add("invalid-feedback");
		</script> 
	</c:otherwise>
</c:choose>