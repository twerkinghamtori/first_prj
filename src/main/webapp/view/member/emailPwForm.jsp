<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 여기는 이메일을 입력하고 인증번호 입력하러 오는곳임 --%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
  <title>이메일 인증</title>
  <style>
    .container{margin: 30px auto; padding: 0 50px;}
    h2{margin-bottom: 30px;}
  </style>
</head>
<body>
<form action="emailPwchk" name="f">
	<input type="hidden" name="emailchkchk" value="emailunchecked">
	<input type="hidden" name="email" value="${param.email }">
    <div class="container">
      <h2>이메일 인증</h2>
      <!-- 인증번호-->
      <div class="form-group">
        <label class="mb-1" for="authNum">인증번호</label>
        <input type="text" class="form-control mb-4" id="autoNum" name="autoNum">
      </div>
      <div class="form-group">
      	<button type="submit" class="btn btn-dark">확인</button>
      </div>
    </div>
</form>
</body>
</html>