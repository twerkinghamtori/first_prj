<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${boardName}</title>
<style type="text/css">
	/* detail.jsp에 들어갈 css속성임 */
  .container{width: 85vw; overflow-x : auto;}
  #title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5rem;
    margin-bottom: 7vh;
  }
  
  th, .btn.btn-dark{font-family:'Dongle', sans-serif; font-size: 1.4em;}
  #f th {min-width : 12vw; text-align: center;}
  th {
    text-align: center;
    width: 12vw;
  }
  
  .table>:not(caption)>*>* {padding: 10px 10px 10px 15px;}
  #prof{width: 30px; height: 30px; border-radius: 50%;}
  #content{height: 40vh;}
  #recomm{font-family:'Dongle', sans-serif; font-size: 50px;}
  #recomm1{font-family:'Dongle', sans-serif; font-size: 25px;}
  #line{border-top: 2px solid; border-bottom: 2px solid;}
  td .btn{font-family: 'Dongle', sans-serif; font-size: 20px;}
  td .btn:hover {color: lightgray;}
  .btn.btn-dark.comm{font-size:12px;}
  .reply{display : none;}
  .msg{
 	position:absolute;
 	border:1px solid black;
 	background-color:black; 
 	color:white; 
 	padding:5px;
 	box-shadow: 2px 2px 5px rgba(0,0,0,0.5);
 	display:none;
 	cursor: pointer;
 }
 .msg-1{
 	top:70%; 
 	left:10%; 
 }
 .msg-2{
 	top:50%; 
 	left:40%; 
 }
  .msg-3{
 	top:50%; 
 	left:60%; 
 }
 .name{cursor: pointer;}
 .msg:hover{
 	background-color: grey;
 	border:1px solid grey;
 }
  /* ************************************ */
</style>
<script type="text/javascript">
	$(()=>{
		 $.ajax({
	     url : "commList?no=${b.no}&pageNum=1",
	     success : function(html){
	    	 $("#comment").html(html);
	     },
		 	error : function(e){
         alert("서버오류" + e.status)
       }
	     
	   })
	   
   	 $.ajax({
	     url : "/first_prj/ajax/recommForm?no=${b.no}",
	     success : function(html){
	    	 $("#recommend").html(html);
	     },
		 	error : function(e){
	        alert("서버오류" + e.status)
	      }
	   })
	   $('#commForm').submit(function(event) {
		   if($("#cont").val() == ''){
			   alert("내용을 입력해주세요.");
			   $("#cont").focus();
			   return false;
		   }
	     event.preventDefault(); // 기본 제출 동작을 막음
	     const form = $(this);
	     const formData = form.serialize(); // 폼 데이터 직렬화
	     const lastPage = $("#lastPage").val()==0? 1:$("#lastPage").val();
	     
	     $.ajax({
         type: form.attr('method'), // 폼의 method 속성 값을 사용
         url: form.attr('action'), // 폼의 action 속성 값을 사용
         data: formData,
         success: function(response) {
             // 등록 성공 시 댓글 목록을 새로고침
             $("#comment").load("commList?no=${b.no}&pageNum="+lastPage);
             $("#cont").val("");
         },
         error: function(jqXHR, textStatus, errorThrown) {
             console.error(textStatus + ": " + errorThrown);
         }
	     });
	 		});
		 $('.item .name').on('click', function() {
			    $('.msg').not($(this).next('.msg')).hide();
			    $(this).next('.msg').toggle();
			  });

			  $(document).on('click', function(e) {
			    if(!$(e.target).closest('.item').length) {
			      $('.msg').hide();
			    }
			  });
			  
			  
	})
  function curPage(n){
		 $.ajax({
	     url : "commList?no=${b.no}&pageNum="+n,
	     success : function(html){
	    	 $("#comment").html(html);
	     },
		 	error : function(e){
         alert("서버오류" + e.status)
       }
	     
	   })
	 }
	function recomm(){
		 $.ajax({
	     url : "/first_prj/ajax/recomm?no=${b.no}",
	     success : function(html){
	    	 $("#recommend").html(html);
	     },
		 	error : function(e){
        alert("서버오류" + e.status)
      }
	     
	   })
	 }
	
</script>
</head>
<body>
	  <!-- About Section -->
  <div class="container">
     <h2 id="title">${boardName}</h2>
   
     <table class="table table-hover align-middle">
       <tr>
         <th class="table-dark">제목</th>
         <td>${b.title}</td>
       </tr>

       <tr>
         <th class="table-dark">조회수</th>
         <td>${b.hit}</td>
       </tr>
       
       <fmt:formatDate value="${today }" pattern="yyyy-MM-dd" var="t"/>
       
       <tr>
       	<th class="table-dark">작성일</th>
       	<fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd" var="r"/>
        <c:if test="${t eq r}">
					<td><fmt:formatDate value="${b.regdate }" pattern="HH:mm"/></td>
				</c:if>
				<c:if test="${t != r}">
					<td><fmt:formatDate value="${b.regdate }" pattern="yyyy-MM-dd"/></td>
				</c:if>
       </tr>

       <tr>
         <th class="table-dark">작성자</th>
         <td style="position:relative" class="item">
         	<c:if test="${b.picture == 'basic-profile.JPG'}">
           	<img id="prof" src="${path }/images/basic-profile.JPG">
           </c:if>
           <c:if test="${b.picture != 'basic-profile.JPG'}">
           	<img id="prof" src="/first_prj/upload/member/${b.picture}">
           </c:if>
           &nbsp;
           <span class="name">${b.nickname }</span>
       			<span class="msg msg-1" onclick="location.href='/first_prj/messenger/msgForm?receiver=${b.nickname}'"><i class="fa fa-comments-o" aria-hidden="true"></i>&nbsp;쪽지</span>
         </td>
       </tr>

       <tr id="content">
         <th class="table-dark">내용</th>
         <td >
           ${b.content }
         </td>
       </tr>

     </table>
     
     <c:if test="${b.boardType != 4 }">
      <!-- 좋아요 -->
      <div align="center" id="recommend"></div>
     </c:if>
     
     <!-- 버튼들 -->
     <div align="center">
     	<c:if test="${sessionScope.login == 'admin' || b.nickname == mem.nickname }">
        <a class="btn btn-dark" href="updateForm?no=${b.no}">수정</a>
        <%-- <a class="btn btn-dark" href="deleteForm?no=${b.no}">삭제</a> --%>
        <!-- Button trigger modal -->
				<a type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
				 	삭제
				</a>
				
				<!-- Modal -->
				<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="staticBackdropLabel">게시물 삭제</h5>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        해당 게시물을 삭제 하시겠습니까?
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				        <a type="button" class="btn btn-primary" href="delete?no=${b.no}" >삭제</a>
				      </div>
				    </div>
				  </div>
				</div>
       </c:if>
       
       <a class="btn btn-dark" href="list?boardType=${b.boardType }">목록으로</a>
     </div>

    <div>&nbsp;</div>

    <table class="table table-hover">
      <tr >
        <th class="table-dark">이전글</th>
        <td>
	        <c:if test="${!empty bPrevious}">
	       		<a href="detail?no=${bPrevious.no}" class="btn">${bPrevious.title}</a>
	       	</c:if>
	       	<c:if test="${empty bPrevious}">
	       		<a class="btn" onclick="alert('이전글이 없습니다.');event.preventDefault();">이전글이 없습니다.</a>
	       	</c:if>
        </td>
      </tr>
      <tr>
        <th class="table-dark">다음글</th>
        <td>
        	<c:if test="${!empty bNext}">
        		<a href="detail?no=${bNext.no}" class="btn">${bNext.title}</a>
        	</c:if>
        	<c:if test="${empty bNext}">
        		<a class="btn" onclick="alert('다음글이 없습니다.');event.preventDefault();">다음글이 없습니다.</a>
        	</c:if>
        </td>
      </tr>
    </table>

    <div>&nbsp;</div>

    <!-- 댓글 -->
    <div class="container" id="comment"></div>
    
    <!-- 댓글작성 폼 -->
    <script>
      function openReply(btn) {
    	  // 클릭한 버튼에 대응하는 댓글 작성 폼 선택
    	  let replyForm = $(btn).closest('tr').next('.reply');
    	  replyForm.toggle();
    	  $('.reply').not(replyForm).hide();
    	}
    </script>
    
    <c:if test="${!empty sessionScope.login && b.boardType != 4}">
	    <form action="comment"  method="post" name="f" id="commForm">
	    	<input type="hidden" name="no" value="${b.no }">
				
	      <table class="table align-middle table-borderless">
	        <tr>
	          <th class="table-light">
	          	<c:if test="${mem.picture == 'basic-profile.JPG'}">
	           		<img id="prof" src="${path }/images/basic-profile.JPG">
	           	</c:if>
	           	<c:if test="${mem.picture != 'basic-profile.JPG'}">
	           		<img id="prof" src="/first_prj/upload/member/${mem.picture}">
	           	</c:if>
	           	&nbsp;${mem.nickname }
	           	<input type="hidden" name="nickname" value="${mem.nickname }"> 
	          	</th>
	          <td><input type="text" name="content"  id="cont" class="form-control"></td>
	        </tr>
	        <tr>
	          <td colspan="2" align="right"><button type="submit" class="btn btn-dark">댓글등록</button></td>
	        </tr>
	      </table>
			</form>
		</c:if>
		
  </div>
  
</body>
</html>