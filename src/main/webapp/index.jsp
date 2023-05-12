<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SHOERACE</title>
<style type="text/css">
body,h1,h2,h3,h4,h5 {font-family: 'Dongle', sans-serif;}
body {font-size: 16px;}
.carousel-item img {
  min-width: 500px;
  width: 100vw;
  height: 95vh; /* 이미지 높이 고정 */
  object-fit: cover; /* 이미지 비율 유지 */
}
	.jumbo {
	  position: absolute;
	  top: 40%;
	  left: 50%;
	  transform: translate(-50%, -50%);
	  text-align: center;
	  z-index: 1;
	}
	.jumbo h3 {
	  font-size: 100px;
	  color: white;
	  text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.5);
	  margin: 0;
	}
	.jumbo p {
	  font-size: 40px;
	  color: white;
	  text-shadow: 3px 3px 3px rgba(0, 0, 0, 0.4);
	  margin: 0;
	}
	.jumbo a {
	  display: inline-block;
	  padding: 12px 30px;
	  margin-top: 40px;
	  background-color: #007bff;
	  color: white;
	  border-radius: 5px;
	  text-decoration: none;
	}
</style>
</head>
<body>
<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel" style="margin: -30px -20px 0;">
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
    <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3" aria-label="Slide 4"></button>
  </div>
  <div class="carousel-inner">
  
    <div class="carousel-item active">
    	<div class="jumbo">
    		<h3 class="display-4 fw-bold mb-4">자유게시판</h3>
		    <p class="mb-4">회원분들과 소통을 하는 공간입니다.</p>
		    <p><a class="btn btn-primary btn-lg" href="${path}/board/list?boardType=1" role="button">바로가기</a></p>
    	</div>
      <img src="${path }/images/jumbo.jpg" class="d-block w-100" alt="...">
    </div>
    
    <div class="carousel-item">
    	<div class="jumbo">
    		<h3 class="display-4 fw-bold mb-4">실시간 발매정보</h3>
		    <p class="mb-4">발매예정인 상품들의 정보를 볼 수 있습니다.</p>
		    <p><a class="btn btn-primary btn-lg" href="${path}/release/list" role="button">바로가기</a></p>
    	</div>
      <img src="${path }/images/jumbo1.jpg" class="d-block w-100" alt="...">
    </div>
    
    <div class="carousel-item">
    	<div class="jumbo">
    		<h3 class="display-4 fw-bold mb-4">스니커 소식</h3>
		    <p class="mb-4">스니커 소식들을 접해봅니다.</p>
		    <p><a class="btn btn-primary btn-lg" href="${path}/news/list" role="button">바로가기</a></p>
    	</div>
      <img src="${path }/images/jumbo2.jpg" class="d-block w-100" alt="...">
    </div>
    
     <div class="carousel-item">
    	<div class="jumbo">
    		<h3 class="display-4 fw-bold mb-4">EVENT</h3>
		    <p class="mb-4">슈레이스에서 주최하는 응모이벤트!</p>
		    <p><a class="btn btn-primary btn-lg" href="${path}/event/eventForm" role="button">바로가기</a></p>
    	</div>
      <img src="${path }/images/jumbo3.jpg" class="d-block w-100" alt="...">
    </div>
    
  </div>
  <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Previous</span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
    <span class="carousel-control-next-icon" aria-hidden="true"></span>
    <span class="visually-hidden">Next</span>
  </button>
</div>

<script>
setInterval(function() {
	  $(".carousel-control-next").click();
	}, 4500);
</script>
</body>
</html>