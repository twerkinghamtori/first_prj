<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${boardName} 게시글 작성</title>
<style type="text/css">
	/* writeForm.jsp에 들어갈 css속성임 */
  .container{width: 75vw;}
  #title{
    font-family: 'Dongle', sans-serif; 
    text-align: center; 
    font-size: 5em;
    margin-bottom: 7vh;
  }
  th, .btn.btn-dark{font-family:'Dongle', sans-serif; font-size: 1.4em;}
  th {
    text-align: center;
    width: 10vw;
  }
  /* ************************************ */
</style>
<script>
 function inputcheck(){
	 
   if(document.f.title.value.trim() == ""){
     alert("제목을 입력하세요");
     document.f.title.focus();
     return;
   }

   if(CKEDITOR.instances.content.getData().trim() == ""){
     alert("내용을 입력하세요");
     CKEDITOR.instances.content.focus();
     return;
		}
   
   document.f.submit();
 }
</script>
</head>
<body>
	 <form method="post" action="write" name="f" >
    <div class="container">
      <h2 id="title">${boardName} 게시글 작성</h2>
    
      <table class="table table-hover">
      
        <tr>
          <th class="table-light">제목</th>
          <td><input type="text" name="title" class="form-control" class="w3-input" placeholder="제목을 입력하세요."></td>
        </tr>

        <tr>
          <th class="table-light">내용</th>
          <td><textarea rows="15" name="content" class="form-control" class="w3-input" id="content"></textarea></td>
         <script>
	        CKEDITOR.editorConfig = function( config ) {
					  config.htmlFilter = CKEDITOR.filter.disallowAll();
					};
				  CKEDITOR.replace("content",{
				    filebrowserImageUploadUrl : "imgupload",
				    // 툴바 구성 변경
				    toolbar: [
				      { name: 'document', items: [ 'Source','-','Save','NewPage','Preview','-','Templates' ] },
				      { name: 'clipboard', items: [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
				      { name: 'insert', items: [ 'Image','Table','HorizontalRule','SpecialChar' ] },
				      '/',
				      { name: 'styles', items: [ 'Styles','Format','Font','FontSize' ] },
				      { name: 'basicstyles', items: [ 'Bold','Italic','Strike','-','RemoveFormat' ] }
				    ],
				    // 스킨 변경
				    skin: 'moono',
				    // 기타 옵션
				    language: 'ko',
				    height: 300,
				    resize_enabled: false
				  });
				</script>
        </tr>

        <tr>
          <td colspan="2" align="center"><a class="btn btn-dark" href="javascript:inputcheck()">등록하기</a></td>
        </tr>

      </table>
    </div>
  </form>
</body>
</html>