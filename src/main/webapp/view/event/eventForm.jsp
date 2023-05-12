<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 페이지</title>
<style type="text/css">
	.container{
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  body, h3,h4{font-family: 'Dongle', sans-serif;}
  #jumbo{
    background-image: url('${path }/images/event.png');
    background-position: center;
    background-size: contain;
    background-repeat: no-repeat;
    width: 100%;
    height: 150vh;
    margin: 0 auto;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }
  #jumbo h3{font-size: 50px; text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.4); color: black}
  #jumbo h4{font-size: 30px; text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.4); color: black}
  #event-init{width: 30vw;}
  table tr:last-child{font-size: 30px;}
  #prize{color: blue; font-weight: bold;}
</style>
<script>
	function win_upload(){
		let op = "width=600, height=500, left=50, top=150";
		open("pictureForm","",op);
	}
</script>
</head>
<body>
	<form action="event" method="post" name="f">
	<c:set var="event" value="${event }" />
	<div class="container">
			 <div id="jumbo" class="w3-container w3-padding-32 w3-center"> 
      <h3 class="mt-3">당첨 상품 : ${event.product}</h3>
      <div class="mb-3">
        <img src="/first_prj/upload/event/${event.picture}" width="450" height="350" id="pic"><br>
        <c:if test="${sessionScope.login == 'admin' }">
        	<div align="center"><a href="javascript:win_upload()">사진등록</a></div>
        </c:if>
      </div>
      <div>      	
        <h4 class="fw-bold mb-3">응모기간 : 
       		<span><fmt:formatDate value="${event.startdate}" pattern="yyyy/ MM/ dd"/> 
        	~ <fmt:formatDate value="${event.enddate}" pattern="yyyy/ MM/ dd"/> </span></h4>
        <c:if test="${sessionScope.login == 'admin' }">
		</c:if>
		
        <c:if test="${sessionScope.login != 'admin' }">
        <a type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
				 	<font size="6">응모하기</font>
				</a>	
			<!-- Modal -->
				<div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
				  <div class="modal-dialog">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h4 class="modal-title" id="staticBackdropLabel">이벤트 응모</h4>
				        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				      </div>
				      <div class="modal-body">
				        <font size="12">응모 하시겠습니까?</font>
				      </div>
				      <div class="modal-footer">
				        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
				        <a type="button" class="btn btn-primary" href="/first_prj/draw/draw?no=${event.no }">응모</a>
				      </div>
				    </div>
				  </div>
				</div>
		</c:if>
				
      </div>
    </div>
   

		<c:if test="${sessionScope.login == 'admin' }">
    <div id="event-init">
      
        <input type="hidden" name="no" value="${event.no }">
        <input type="hidden" name="picture" value="">
        
        <table class="table text-center table-borderless table-hover align-middle ">
          <tr>
            <th class="table-dark">상품명</th>
            <td colspan="3"><input type="text" class="form-control" name="prodName"></td>
          </tr>

          <tr>
            <th class="table-dark">응모기간</th>
            <td><input type="date" class="form-control" name="startdate"></td>
            <td>부터</td>
            <td><input type="date" class="form-control" name="enddate"></td>
          </tr>

          <tr>
            <td align="center" colspan="4">
              <a type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop1">
              	저장
              </a>
              
         			<!-- Modal -->
							<div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h4 class="modal-title" id="staticBackdropLabel">이벤트 저장</h4>
							        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							      </div>
							      <div class="modal-body">
							        <font size="12">저장 하시겠습니까?</font>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
							        <button class="btn btn-primary" type="submit">저장</button>
							      </div>
							    </div>
							  </div>
							</div>
							
              <a type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop2">
              	종료
              </a>
              
              <!-- Modal -->
							<div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
							  <div class="modal-dialog">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h4 class="modal-title" id="staticBackdropLabel">이벤트 종료</h4>
							        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							      </div>
							      <div class="modal-body">
							        <font size="12">종료 하시겠습니까?</font>
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
							        <a type="button" class="btn btn-primary" href="end?no=${event.no }">종료</a>
							      </div>
							    </div>
							  </div>
							</div>
            </td>
          </tr>
          
		  <c:if test="${! empty draw }">
		  	<tr align="center">
            	<td colspan="4">당첨자 : <span id="prize">${mem.nickname}</span></td>
          	</tr>
		  </c:if>
          
        </table>
    </div>
    </c:if>
  </div>
  </form>
</body>
</html>