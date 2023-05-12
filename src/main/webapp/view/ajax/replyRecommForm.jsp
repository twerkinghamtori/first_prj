<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${sessionScope.login != null }">
	<c:if test="${isDup == 1 }">
		<a type="button" class="btn" id="recomm1"  onclick="alert('이미 추천 하셨습니다.')">
		 	<i class="fa fa-thumbs-up" style="color: red;"></i>&nbsp;${c.recommend }
		</a>
	</c:if>
	<c:if test="${isDup == 0  }">
		<a type="button" class="btn" id="recomm1"  href="javascript:replyRecomm(${c.no },${c.seq })">
		 	<i class="fa fa-thumbs-o-up"></i>&nbsp;${c.recommend }
		</a>
	</c:if>
</c:if>

<c:if test="${sessionScope.login == null }">
<a type="button" class="btn" id="recomm1" onclick="alert('회원만 추천 가능합니다.')">
 	<i class="fa fa-thumbs-o-up"></i>&nbsp;${c.recommend }
</a>
</c:if>