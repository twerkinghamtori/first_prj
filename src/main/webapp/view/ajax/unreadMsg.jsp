<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<a type="button" class="position-relative" id="msgBtn" onclick="location.href='/first_prj/messenger/msgForm'">
            <i class="fa fa-comments-o" aria-hidden="true"></i> 쪽지함
           <c:if test="${unreadMsg  > 0}">
	           <font size="4">
		           <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
		             ${unreadMsg }
		             <span class="visually-hidden">unread messages</span>
		           </span>
	           </font>
           </c:if>
       </a>