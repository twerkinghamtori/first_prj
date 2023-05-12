<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" var="t"/>
  <div style="display: flex; justify-content: space-between; margin: 15px auto;">
          <div>
            전체 댓글 <span id="commCnt" style="color:red">${commCnt }</span>개
          </div>
          <div></div>
       </div>
<input id="lastPage" type="hidden" value="${maxPage }">
<div id="line">
  <table  class="table table-hover align-middle">
            <tbody>
            
            <c:if test="${commCnt <= 0 }">
            	<tr align="center"><td>등록된 댓글이 없습니다.</td></tr>
            </c:if>
            
            <c:forEach var="comm" items="${commList }" varStatus="st">
            	<span id="id${comm.no}${comm.seq}"></span>
              <tr class="${comm.recommend >=5? 'table-secondary' : ''  }">
                <td width="20%" style="position:relative" class="item">
                	<c:if test="${comm.grpLevel == 1}">&nbsp;&nbsp;&#10551;&nbsp;&nbsp;</c:if>
                	<c:if test="${comm.picture == 'basic-profile.JPG'}">
				           	<img id="prof" src="${path }/images/basic-profile.JPG">
				           </c:if>
				           <c:if test="${comm.picture != 'basic-profile.JPG'}">
				           	<img id="prof" src="/first_prj/upload/member/${comm.picture}">
				           </c:if>
				           &nbsp;
				           <span class="name" onclick="$('.msg').not($(this).next('.msg')).hide();$(this).next('.msg').toggle();">${comm.nickname }</span>
       							<span class="msg ${comm.grpLevel == 1? 'msg-3' : 'msg-2'}" onclick="location.href='/first_prj/messenger/msgForm?receiver=${comm.nickname}'"><i class="fa fa-comments-o" aria-hidden="true"></i>&nbsp;쪽지</span>
                </td>
                
                <c:if test="${comm.recommend >= 5 }">
	                <td width="100vw" align="left">
		                <span class="badge rounded-pill bg-success">Best</span><br>
		                <p class="fw-bold" style="margin:0;">${comm.content }</p>
	                </td>
                </c:if>
                <c:if test="${comm.recommend < 5 }">
	                <td width="100vw" align="left">
		                <p  style="margin:0;">${comm.content }</p>
	                </td>
                </c:if>
                
                <fmt:formatDate value="${comm.regdate }" pattern="yyyy-MM-dd" var="r2"/>
                <c:if test="${t eq r2}">
									<td width="10%">
										<font size="3"><fmt:formatDate value="${comm.regdate }" pattern="HH:mm"/></font>
									</td>
								</c:if>
								<c:if test="${t != r2}">
									<td width="10%">
										<font size="3"><fmt:formatDate value="${comm.regdate }" pattern="yyyy-MM-dd"/></font>
									</td>
								</c:if>
                
                <td width="9%"><span  id="recommend${comm.no}${comm.seq}"></span></td>
                
                <td width="10%">
                	<c:if test="${comm.nickname == mem.nickname || sessionScope.login == 'admin'}">
                		  <!-- Button trigger modal -->
											<a type="button" class="btn btn-dark comm" data-bs-toggle="modal" data-bs-target="#staticBackdrop${comm.no}${comm.seq}">
											 	삭제
											</a>
											
											<%-- Modal --%>
											<div class="modal fade" id="staticBackdrop${comm.no}${comm.seq}" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
											  <div class="modal-dialog">
											    <div class="modal-content">
											      <div class="modal-header">
											        <h5 class="modal-title" id="staticBackdropLabel">댓글 삭제</h5>
											        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
											      </div>
											      <div class="modal-body">
											        해당 댓글을 삭제 하시겠습니까?
											      </div>
											      <div class="modal-footer">
											        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
											        <a class="btn btn-dark" href="commDel?no=${comm.no }&seq=${comm.seq}">삭제</a>
											      </div>
											    </div>
											  </div>
											</div>
											
                	</c:if>
                	<c:if test="${comm.grpLevel <1 && sessionScope.login != null}">
                		<a class="btn btn-dark comm" href="#id${comm.no}${comm.seq}" onclick="openReply(this)">댓글작성</a>
                	</c:if>
                </td>
                
              </tr>
              
              <tr class="reply">
              	<td colspan="5">
              	<c:if test="${!empty sessionScope.login}">
              	
							    <form action="commReply" method="post" name="f${comm.no}${comm.seq}" id="f${comm.no}${comm.seq}">
							    	<input type="hidden" name="no" value="${comm.no }">
										<input type="hidden" name="grp" value="${comm.grp}">
										<input type="hidden" name="grpLevel" value="${comm.grpLevel }">
										<input type="hidden" name="grpStep" value="${comm.grpStep }">
										
										
							      <table class="table align-middle table-borderless" >
							        <tr>
							          <th class="table-light">
							          	<c:if test="${mem.picture == 'basic-profile.JPG'}">
							           		&nbsp;&#10551;&nbsp;&nbsp;<img id="prof" src="${path }/images/basic-profile.JPG">
							           	</c:if>
							           	<c:if test="${mem.picture != 'basic-profile.JPG'}">
							           		&nbsp;&#10551;&nbsp;&nbsp;<img id="prof" src="/first_prj/upload/member/${mem.picture}">
							           	</c:if>
							           	&nbsp;${mem.nickname }
							           	<input type="hidden" name="nickname" value="${mem.nickname }"> 
							          	</th>
							          <td width="75%">
							          	<input type="text" name="content" id="cont${comm.no}${comm.seq}" class="form-control">
							          </td>
							          <td align="right"><button type="submit" class="btn btn-dark">댓글등록</button></td>
							        </tr>
							      </table>
									</form>
								</c:if>
								</td>
								
								<script>
								$('#f${comm.no}${comm.seq}').submit(function(event) {
									  if($("#cont${comm.no}${comm.seq}").val() == ''){
									    alert("내용을 입력해주세요.");
									    $("#cont${comm.no}${comm.seq}").focus();
									    return false;
									  }
									  event.preventDefault(); // 기본 제출 동작을 막음
									  const form = $(this);
									  const formData = form.serialize(); // 폼 데이터 직렬화
									  $.ajax({
									    type: form.attr('method'), // 폼의 method 속성 값을 사용
									    url: form.attr('action'), // 폼의 action 속성 값을 사용
									    data: formData,
									    success: function(response) {
									      // 등록 성공 시 댓글 목록을 새로고침
									      $("#comment").load("commList?no=${comm.no}&pageNum="+${pageNum});
									      $("#cont${comm.no}${comm.seq}").val("");
									    },
									    error: function(jqXHR, textStatus, errorThrown) {
									      console.error(textStatus + ": " + errorThrown);
									    }
									  });
									});
								
								$(()=>{
									 $.ajax({
								     url : "/first_prj/ajax/replyRecommForm?no=${comm.no}&seq=${comm.seq}",
								     success : function(html){
								    	 $("#recommend${comm.no}${comm.seq}").html(html);
								     },
									 	error : function(e){
								        alert("서버오류" + e.status)
								      }
								     
								   })
								  
								   $(document).on('click', function(e) {
									    if(!$(e.target).closest('.item').length) {
									      $('.msg').hide();
									    }
									  });
								})
								
									function replyRecomm(no, seq){
									 $.ajax({
								     url : "/first_prj/ajax/replyRecomm?no="+no+"&seq="+seq,
								     success : function(html){
								    	 $("#comment").load("/first_prj/board/commList?no=${comm.no}&pageNum=${pageNum}");
								     },
									 	error : function(e){
							       alert("서버오류" + e.status)
							     }
								     
								   })
								 }
								</script>
								
              </tr>
              
              </c:forEach>
              
            </tbody>
          </table>
          </div>
          <%-- paging --%>
			  <div class="w3-center w3-padding-32">
			    <div class="w3-bar">
				    <c:if test="${startPage <= 1}">
							<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('이전 페이지가 없습니다.');">&laquo;</a>
						</c:if>
						<c:if test="${startPage > 1}">
							<a class="w3-bar-item w3-button w3-hover-black" href="javascript:curPage(${a }-1)">&laquo;</a>
						</c:if>
						
						<c:forEach var="a" begin="${startPage}" end="${endPage}">
							<c:if test="${a <= maxPage}">
								<a class="w3-bar-item w3-button w3-hover-black ${a == pageNum ? 'w3-black' : '' }" href="javascript:curPage(${a })" id="${a}">${a}</a>
							</c:if>
						</c:forEach>
							
						<c:if test="${startPage+4 >= maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" onclick="alert('다음 페이지가 없습니다.');">&raquo;</a>
						</c:if>
						<c:if test="${startPage+4 < maxPage}">
							<a class="w3-bar-item w3-button w3-hover-black" href="javascript:curPage(${a }+1)">&raquo;</a>
						</c:if>
			    </div>
			  </div>
