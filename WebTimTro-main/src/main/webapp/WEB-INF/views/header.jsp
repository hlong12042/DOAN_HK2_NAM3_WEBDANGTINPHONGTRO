<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>G trọ Việt Nam</title>
<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<script src="resources/ckfinder/ckfinder.js" type="text/javascript"></script>
<script type="text/javascript" src= "resources/ckeditor/ckeditor.js"></script>
<link rel="icon" href="resources/images/icon/google.png" sizes="64x64">
<link rel="stylesheet" type="text/css" href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js"></script>
<!-- datatables:css -->
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-se/css/dataTables.semanticui.min.css">
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-responsive-se/css/responsive.semanticui.min.css">
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-buttons-se/css/buttons.semanticui.min.css">
<!-- endinject -->
<style type="text/css">
.dropbtn {
	background-color: #ffffff00;
	color: rgb(255, 255, 255);
	padding: 1vh 5vw;
	font-size: 18px;
	border: 2px solid #00000000;
	border-radius: 10px;
	transition: 0.2s;
	text-transform: uppercase;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: rgba(0, 0, 0, 0.548);
	min-width: 13vw;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
	border-radius: 10px;
}

.dropdown-content a {
	color: rgb(255, 255, 255);
	padding: 7px 20px;
	text-decoration: none;
	display: block;
	font-size: 2vh;
	transition: 0.2s;
}

.dropdown-content a:hover {
	background-color: #ff0000;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	border: 2px solid #ff0000;
	color: #ff0000;
	font-weight: bold;
}
.thongbao{
	height: 20px;
	background: url("resources/images/icon/mail.png") ;
}
.form-popup {
  display: none;
  position: fixed;
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}
</style>
</head>
<body>
	<!-- Following Menu -->
	<div class="ui top fixed inverted menu">
		<a href="index.htm" class="item">
			<i class="google icon"></i>trọ Việt Nam
		</a>
		<div class="right menu">
				<a href="register.htm" class="item">
					<i class="user plus icon"></i>
					Đăng kí
				</a>
				<a href="login.htm" class=" item">
					<i class="sign in alternate icon"></i>
					Đăng nhập
				</a>
			</div>
		</div>
	<span class="ui inverted header">Padding</span>
	<h1 style="text-align: center; color: #00ffff">KÊNH THÔNG TIN PHÒNG TRỌ SỐ MỘT VIỆT NAM</h1>
	<div class="ui grid" style="margin-left: 15%">
		<div class="fourteen wide column">
	  	<c:if test="${message!=null}"> 
	  		<div class="ui teal message"> 
	  			<i class="close icon"></i> 
	  			<div class="header"> Thông báo </div>
	  			<p> ${message} </p> 
	  		</div> 
	  	</c:if>
	  	<c:if test="${error!=null}"> 
	  		<div class="ui red message"> 
	  			<i class="close icon"></i> 
	  			<div class="header"> Lỗi! </div>
	  			<p> ${error} </p> 
	  		</div> 
	  	</c:if>
		<c:if test="${success!=null}"> 
			<div class="ui green message"> 
				<i class="close icon"></i> 
	  			<div class="header"> Thành Công! </div>
	  			<p> ${success} </p> 
			</div> 
		</c:if>
		</div>
	</div>

<script>
$('.message .close').on('click', function() {
	$(this).closest('.message').transition('fade');
});
</script>
</body>
</html>