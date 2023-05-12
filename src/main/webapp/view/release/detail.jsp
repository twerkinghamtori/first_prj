<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제품 상세</title>
<style type="text/css">
	.container{width: 75vw;}
  #info, strong, span, .btn.btn-dark, .mb-3.mt-3,.w3-button.mt-3{font-family: 'Dongle', sans-serif; font-size: 1.7em;}
  #prodimg>img{box-shadow: 2px 2px 6px rgba(0, 0, 0, 0.3);}
</style>
</head>
<body>
	<div class="container">
  <!-- Push down content on small screens -->
  <div class="w3-hide-large" style="margin-top:80px"></div>
  
  <h4 class="text-center mt-5"><strong>${rd.title }</strong><span>(${rd.subTitle })</span></h4>

  <!-- Slideshow Header -->
  <div class="w3-container text-center" id="apartment">
  	<c:forEach var="img" items="${list}" varStatus="st">
	    <div class="w3-display-container mySlides" id="prodimg">
	    <img src="${img}" style="width:60%;height: 500px;margin-bottom:-6px">
	      <div class="w3-display-bottomleft w3-container w3-black">
	      </div>
    	</div>
    </c:forEach>
	  <button class="w3-button mt-3" onclick="plusSlides(-1)">이전 <i class="fa fa-arrow-circle-left w3-hover-text-teal"></i></button>
    <button class="w3-button mt-3" onclick="plusSlides(1)"><i class="fa fa-arrow-circle-right w3-hover-text-teal"></i> 다음</button>
  </div>
  
	
	<p style="color:blue;" class="mb-3 mt-3"><i class="fa fa-info-circle"></i> 아래 이미지들을 클릭하면 확대하여 볼 수 있습니다.</p>
  <!-- 밑에 사진들 -->
  <div class="w3-row-padding w3-section">
  	<c:forEach var="img" items="${list}" varStatus="st">
	    <div class="w3-col s1">
	      <img class="demo w3-opacity w3-hover-opacity-off" src="${img}" style="width:80%;height: 60px;cursor:pointer" onclick="currentDiv('${st.index+1}')">
	    </div>
    </c:forEach>
    
  </div>

  <div class="w3-container" id="info">
    
    <p></p>
    <div class="w3-row w3-large">
      <div class="w3-col s6" style="font-size:1.5em;">
        <p><i id="brand-icon" class="fa fa-nike"></i> 브랜드 : ${rd.brand }</p><!-- 브랜드 -->
        <p><i class="fa fa-barcode"></i> 제품코드 : ${rd.code }</p> <!-- 제품 코드 -->
        <p><i class="fa fa-paint-brush"></i> 컬러 : ${rd.color }</p> <!-- 제품 색상 -->
        <p><i class="fa fa-calendar"></i> 발매일 : ${rd.releaseDate }</p> <!-- 발매일 -->
        <p>가격 : ${rd.price }</p> <!-- 발매가 -->
      </div>
    </div>
    <hr>
    
    <h4><strong>제품 소개</strong></h4>
    <p>${rd.info }</p>
  </div>
  <hr>
    <div class="text-center"><a type="button" class="btn btn-dark" href="list">목록으로</a></div>

<!-- End page content -->
</div>
<script>
let slideIndex = 1;
showSlides(slideIndex);

function plusSlides(n) {
  showSlides(slideIndex += n);
}

function currentDiv(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  let i;
  let slides = $(".mySlides");
  let dots = $(".demo");
  if (n > slides.length) {slideIndex = 1}
  if (n < 1) {slideIndex = slides.length}
  slides.hide();
  dots.removeClass("w3-opacity-off");
  slides.eq(slideIndex-1).show();
  dots.eq(slideIndex-1).addClass("w3-opacity-off");
}

showDivs(slideIndex);

function plusDivs(n) {
  showDivs(slideIndex += n);
}

function currentDiv(n) {
  showDivs(slideIndex = n);
}

function showDivs(n) {
  let x = $(".mySlides");
  let dots = $(".demo");
  if (n > x.length) {slideIndex = 1}
  if (n < 1) {slideIndex = x.length}
  x.hide();
  dots.removeClass("w3-opacity-off");
  x.eq(slideIndex-1).show();
  dots.eq(slideIndex-1).addClass("w3-opacity-off");
}
  </script>
</body>
</html>