<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<style type="text/css">
  /* deleteForm.jsp에 들어갈 css속성임 */
  #title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: 7vh;
  }
  .container{width: 70vw;}
  .btn{font-family: 'Dongle', sans-serif; font-size: 1.6em;}
  .form-group{width: 35vw;}
  .mt-3 .btn:hover {color: lightgray;}
  /* ************************************ */
</style>
<script>
	function inputcheck(f){
		if(f.pass.value.trim() == ""){
	    alert("비밀번호를 입력하세요")
	    f.pass.focus();
	    return false;
	  }
	}
</script>
</head>
<body>
		<form action="delete" method="post" name="f" onsubmit="return input_check(this)">

    <input type="hidden" name="email" value="${param.email }">
    
		<h1 id="title">회원탈퇴</h1>
    <div class="form-group container">
      <label class="mb-1" for="pwd">비밀번호</label><input placeholder="비밀번호" type="password" class="form-control mb-3" id="pwd" name="pass">
    </div>
    <div class="container mt-3" align="center">
      
       <!-- Button trigger modal -->
               <a type="button" class="btn btn-dark" data-bs-toggle="modal" data-bs-target="#staticBackdrop">
                   탈퇴하기
               </a>
               
               <!-- Modal -->
               <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                 <div class="modal-dialog">
                   <div class="modal-content">
                     <div class="modal-header">
                       <h5 class="modal-title" id="staticBackdropLabel">회원 탈퇴</h5>
                       <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                     </div>
                     <div class="modal-body">
                       탈퇴 시 모든 회원 데이터가 삭제됩니다. 정말 탈퇴하시겠습니까?
                     </div>
                     <div class="modal-footer">
                       <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
                       <button type="submit" class="btn btn-primary">탈퇴</button>
                     </div>
                   </div>
                 </div>
               </div>
    </div>
	</form>
</body>
</html>
