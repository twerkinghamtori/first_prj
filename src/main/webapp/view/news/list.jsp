<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스니커 소식</title>
<style type="text/css">
h3, .btn.btn-dark{font-family: 'Dongle', sans-serif;}
h3{font-size : 30px}
.btn{font-size : 20px}
.item:hover::before {
  content: "";
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 1;
}

.item:hover h3,
.item:hover button {
  opacity: 1;
  transform: translateY(-50%);
  transition: all 0.5s ease-in-out;
  z-index:99;
}

.item h3{
  position: absolute;
  top: 40%;
  left: 0;
  right: 0;
  transform: translateY(-50%);
  text-align: center; /* 텍스트를 수평 가운데 정렬합니다. */
  opacity: 0;
  color: white;
}

.item button {
  position: absolute;
  top: 60%;
  left: 30%;
  right: 30%;
  transform: translateY(-50%);
  text-align: center; /* 텍스트를 수평 가운데 정렬합니다. */
  opacity: 0;
}
</style>
</head>
<body>
	 <!-- !PAGE CONTENT! -->
  <div class="w3-main" style="margin: -8px -20px 0;">

    
    <!-- Photo grid -->
    <div class="w3-row">
    
	      <div class="w3-third" >
	      	<div style="position:relative">
		        <div class="item text-center">
		        	<img src="${list[0].src }" style="width:100%">
		        	<h3>${list[0].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[0].no}'">바로가기</button>
		        </div>
	        </div>
	        
	       <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[3].src }" style="width:100%">
		        	<h3>${list[3].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[3].no}'">바로가기</button>
		        </div>
	        </div>
	        
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[6].src }" style="width:100%">
		        	<h3>${list[6].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[6].no}'">바로가기</button>
		        </div>
	        </div>
	      </div>
	
	      <div class="w3-third">
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[1].src }" style="width:100%">
		        	<h3>${list[1].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[1].no}'">바로가기</button>
		        </div>
	        </div>
	        
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[4].src }" style="width:100%">
		        	<h3>${list[4].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[4].no}'">바로가기</button>
		        </div>
	        </div>
	        
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[7].src }" style="width:100%">
		        	<h3>${list[7].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[7].no}'">바로가기</button>
		        </div>
	        </div>
	      </div>
	      
	      <div class="w3-third">
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[2].src }" style="width:100%">
		        	<h3>${list[2].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[2].no}'">바로가기</button>
		        </div>
	        </div>
	        
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[5].src }" style="width:100%">
		        	<h3>${list[5].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[5].no}'">바로가기</button>
		        </div>
	        </div>
	        
	        <div style="position:relative">
		        <div class="item" >
		        	<img src="${list[8].src }" style="width:100%">
		        	<h3>${list[8].alt }</h3>
	  					<button class="btn btn-dark" onclick="javascript:location.href='detail?no=${list[8].no}'">바로가기</button>
		        </div>
	        </div>
	      </div>
    </div>
	    
		

  </div>
</body>
</html>