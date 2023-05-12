<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
  <title>비밀번호 변경</title>
  <style>
    .container{margin: 30px auto; padding: 0 50px;}
    h2{margin-bottom: 30px;}
    .container .form-group{position: relative;}
    #minMsg{position: absolute; bottom: 5vh; left: 0; color: red;}
    #cor1, #cor2{position:relative;}
  	#corPwMsg{position:absolute; bottom:-40%; left:0;}
  	#pwChkMsg{ position:absolute; bottom:-37%; left:0;}
  </style>
  <script>
	function inchk(f){
		if(f.pass.value.trim() === ""){
			alert("변경 비밀번호를 입력하세요");
	        f.pass.focus();
	        return false;
		}
			
		if(f.pass2.value.trim() === ""){
			alert("변경 재입력 비밀번호를 입력하세요");
	        f.pass2.focus();
	        return false;
		}
	    if(f.pwchkchk.value != "pwchecked") {
			alert("비밀번호를 확인해주세요.");
			return false;
		 }
		 if(f.corpwchk.value != "pwchecked") {
			alert("비밀번호를 확인해주세요.");
			return false;
		}
	    return true;
	}
	$(function() {
		$("#pwd").keyup(function() {
			corPwChk();
			pwChk();
		})
		$("#pwd2").keyup(function() {
			corPwChk();
			pwChk();
		})
	})
	function corPwChk() {
		let param = {pass:$("#pwd").val(), pass2:$("#pwd2").val()};
		$.ajax({
			url : "/first_prj/ajax/corrPassChk",
			type : "POST",
			data : param,
			success : function(result) {
				$("#corPwMsg").html(result)
			},
			error : function(e) {
				alert("비밀번호 입력" + e.status)
			}
		})
	}
	function pwChk() {
		let param = {pass:$("#pwd").val(), pass2:$("#pwd2").val()};
		$.ajax({
			url : "/first_prj/ajax/passChk",
			type : "POST",
			data : param,
			success : function(result) {
				$("#pwChkMsg").html(result)
			},
			error : function(e) {
				alert("비밀번호 입력" + e.status)
			}
		})
	}
</script>
</head>
<body>
   <form action="password1"  method="post" name="f" onsubmit="return inchk(this)" >
     <div class="container">
     <input type="hidden" name="email" value="${param.email}">
       <h2>비밀번호 변경</h2>
       <!-- 비밀번호-->
       <div id="cor1" class="form-group">
         <label class="mb-1" for="pwd">변경 비밀번호</label>
         <input type="password" class="form-control mb-4" id="pwd" name="pass" placeholder="8~16자 영대소문자/숫자 조합 특수문자 불가">
         <div class="invalid-feedback" id="corPwMsg"></div>
       </div>
       <!-- 비밀번호 재입력 -->
       <div id="cor2" class="form-group mt-5">
         <label class="mb-1" for="pwd">변경 비밀번호 재입력</label>
         <input type="password" class="form-control mb-5" id="pwd2" name="pass2">
         <div class="invalid-feedback" id="pwChkMsg"></div> 
       </div>

       <div class="form-group">
         <button type="submit" class="btn btn-dark">변경하기</button>
       </div>
       
     </div>
   </form>
</body>
</html>