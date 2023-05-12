<%@page import="org.apache.ibatis.reflection.SystemMetaObject"%>
<%@page import="model.MemberMybatisDao"%>
<%@page import="model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쪽지함</title>
<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.nicescroll/3.6.8-fix/jquery.nicescroll.min.js"></script>
<script type="text/javascript">
	$(function(){
    	$(".chat").niceScroll();
    	$("#wannaFix").keypress(function(e) {
    		if(e.keyCode == 13) {
    			if($(this).val().trim() == "") {
    				 alert("메세지를 입력하세요.");
    				 $(this).focus();
    			     return false;
    			} else {
    				document.f.submit();
    			}    			
    		}    		
    	})
    	$("#btn").click(function() {
    		let content = document.f.content.value;
    		if(content=="") {
    			alert("메세지를 입력하세요.")
    			f.content.focus();
    			return;
    		}
    		document.f.submit();
    	})
    	$("#out").click(function() {
    		document.f.out.value = "out";
    		
    	})
	}) 
	window.addEventListener("load", function() {
  		const fixed = document.getElementById("wannaFix"); 
  		fixed.focus();
  		fixed.tabIndex=0;  		
	});
</script>
<style type="text/css">
.body{ margin-top:20px; background:#eee;}
.row.row-broken { padding-bottom: 0;}
.col-inside-lg {padding: 20px; }
.chat {height: calc(100vh - 180px);}
.decor-default {background-color: #ffffff;}
.col-inside-lg .decor-default { overflow-y: scroll;}
.chat-users h6 {font-size: 20px; margin: 0 0 20px;}
.chat-users #msgBtn {margin: 0 0 20px;}
.chat-users .user {position: relative;padding: 0 0 10px 50px;display: block;cursor: pointer;margin: 0 0 20px;}
.chat-users .user .avatar {top: 0;left: 0;}
.chat .avatar {width: 40px;height: 40px;position: absolute;}
.chat .avatar img {display: block;border-radius: 50%;height: 100%;}
.chat-users .user .name {font-size: 14px;font-weight: bold;line-height: 20px;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;}
.chat-users .user .mood {font: 200 14px/20px "Raleway", sans-serif;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;}

/*****************CHAT BODY *******************/
.chat-body .answer.left {padding: 0 0 0 58px;text-align: left;float: left;}
.chat-body .answer {position: relative;max-width: 600px;overflow: hidden;clear: both;}
.chat-body .answer.left .avatar {left: 0;}
.chat-body .answer .avatar {bottom: 36px;}
.chat .avatar {width: 40px;height: 40px;position: absolute;}
.chat .avatar img {display: block;border-radius: 50%;height: 100%;}
.chat-body .answer .name {font-size: 14px;line-height: 36px;}
.chat-body .answer.left .avatar .status {right: 4px;}
.chat-body .answer .avatar .status {bottom: 0;}
.chat-body .answer.left .text {background: white;color: black;border-radius: 8px 8px 8px 0;}
.chat-body .answer .text {padding: 12px;font-size: 16px;line-height: 26px;position: relative;}
.chat-body .answer.left .text:before {left: -30px;border-right-color: white;border-right-width: 12px;}
.chat-body .answer .text:before {content: '';display: block;position: absolute;bottom: 0;border: 18px solid transparent;border-bottom-width: 0;}
.chat-body .answer.left .time {padding-left: 12px;color: #333333;}
.chat-body .answer .time {font-size: 16px;line-height: 36px;position: relative;padding-bottom: 1px;}
/*RIGHT*/
.chat-body .answer.right {padding: 0 58px 0 0;text-align: right;float: right;}

.chat-body .answer.right .avatar {right: 0;}
.chat-body .answer.right .avatar .status {left: 4px;}
.chat-body .answer.right .text {background: black;color: white;border-radius: 8px 8px 0 8px;}
.chat-body .answer.right .text:before {right: -30px;border-left-color: black;border-left-width: 12px;}
.chat-body .answer.right .time {padding-right: 12px;color: #333333;}
.decor-default{background-color: rgba(0,0,0,0.1);}
/**************ADD FORM ***************/
.answer-add {
    clear: both;
    position: relative;
    margin: 20px -20px -20px;
    padding: 20px;
    background: rgba(0,0,0,0.3);
}
.answer-add input {
    border: none;
    background: none;
    display: inline-block;
    width: 100%;
    font-size: 25px;
    line-height: 20px;
    padding: 0;
}
.chat input {
    -webkit-appearance: none;
    border-radius: 0;
}
.chat-body .answer-add .answer-btn-1 {
    background: url("http://91.234.35.26/iwiki-admin/v1.0.0/admin/img/icon-40.png") 50% 50% no-repeat;
    right: 56px;
}
.chat-body .answer-add .answer-btn {
    display: block;
    cursor: pointer;
    width: 36px;
    height: 36px;
    position: absolute;
    top: 50%;
    margin-top: -18px;
}
.chat-body .answer-add .answer-btn-2 {
    right: 20px;
}
.chat input::-webkit-input-placeholder {color: #fff;}

.chat input:-moz-placeholder { /* Firefox 18- */color: #fff;  
}

.chat input::-moz-placeholder {  /* Firefox 19+ */color: #fff;  }

.chat input:-ms-input-placeholder {  color: #fff;  }
.chat input {
    -webkit-appearance: none;
    border-radius: 0;
}
.bootstrap.snippets.bootdey div{font-family: 'Dongle', sans-serif; font-size: 1.1em}
h2, h4{font-family: 'Dongle', sans-serif; font-size:75px;}
a {text-decoration: none;}
#select{margin:0 auto; position:absolute; top:50%; left:50%; font-size:50px;}
</style>
</head>
<body class="body">

	<div class="container">
	<h2 class="text-center"><a href="msgForm">쪽지함</a></h2>
	<form action="search" method="post" name="searchF">
	   	<div class="input-group ms-3 mb-1" style="width:23%">
			<input type="text" class="form-control" name="nickname" placeholder="닉네임 검색">
			<button class="btn btn-outline-secondary" type="submit" id="button-addon2"><i class="fa fa-search"></i></button>
		</div>
     </form>
<div class="content container-fluid bootstrap snippets bootdey">
      <div class="row row-broken">
        <div class="col-sm-3 col-xs-12">
          <div class="col-inside-lg decor-default chat" style="overflow: hidden; outline: none;" tabindex="5000">
            <div class="chat-users">
            <h5 style="font-size:0.1em; margin:0">&nbsp;</h5>
              <c:forEach var="r" items="${senderInfoMap }" varStatus="st">
              	<c:set var="sender" value="${r.key}" />
  				<c:set var="cnt" value="${r.value.cnt}" />
  				<c:set var="pic" value="${r.value.pic}" />
                <div class="user mb-5" onclick="location.href='msgForm?receiver=${r.key}'">                
                    <div class="avatar">
                 	 	<c:if test="${pic=='basic-profile.JPG' }">
      						<img src="${path }/images/basic-profile.JPG" width="40px"><br>
      					</c:if>
      					<c:if test="${pic != 'basic-profile.JPG' }">
      						<img src="/first_prj/upload/member/${pic}" width="40px"><br>
      					</c:if>  
                    </div>
                    <div class="name">
                    	${sender}                    	
                    </div>
                    <c:if test="${cnt!=0 }">
                    	<span class="position-absolute translate-middle badge rounded-pill bg-danger" style="left: 230px; top:10px;">
    							${cnt }
    						<span class="visually-hidden">unread messages</span>
  			  			</span>
                    </c:if>
                    
                </div>
                
              </c:forEach>
            </div>
          </div>
        </div>
        <div class="col-sm-9 col-xs-12 chat" style="overflow: hidden; outline: none; dispaly:relative; background:#eee;" tabindex="5001">
               
            	<h4 id="select">채팅방을 선택해주세요.</h4>
           
          </div>
        </div>
      </div>
    </div>
</body>
</html>