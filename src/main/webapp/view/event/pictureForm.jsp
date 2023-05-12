<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<title>이벤트 사진 등록</title>
<style type="text/css">
	.container{margin-top : 30px;}
</style>
</head>
<body>
<div class="container">
 <h2 align="center">사진 업로드</h2>
 <table class="table table-hover">

   <tr>
     <td><img src="" id="preview"></td>
   </tr>

   <tr>
     <td>
       <form action="picture" method="post" enctype="multipart/form-data">
         <input type="file" name="picture" id="imageFile" accept="img/*">
         <input class="btn btn-dark ms-5" type="submit" value="사진등록">
       </form>
     </td>
   </tr>

 </table>
 </div>
<script>
	const imagefile = document.querySelector("#imageFile");
  const preview = document.querySelector("#preview");
  
  imagefile.addEventListener("change", (event)=>{
   const get_file = event.target.files;	// 선택된 파일.
   const reader = new FileReader();			// 파일을 읽기위한 스트림
   reader.onload = ((Img)=>{
     return function(event){
       Img.src=event.target.result;
     }
   })(preview);
   // get_file : 선택된 파일이 존재하면
   // get_file[0] : 첫번째 선택된 파일
   // readAsDataURL : 파일 읽기 => onload 이벤트발생
   if(get_file){
     reader.readAsDataURL(get_file[0]);
   }
 	});
</script>
</body>
</html>