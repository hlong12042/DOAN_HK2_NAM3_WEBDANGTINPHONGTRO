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
<link rel="icon" href="resources/images/icon/google.png" sizes="32x32">
<!-- datatables:css -->
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-se/css/dataTables.semanticui.min.css">
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-responsive-se/css/responsive.semanticui.min.css">
<link rel="stylesheet" href="resources/vendors/datatables.net/datatables.net-buttons-se/css/buttons.semanticui.min.css">
<link rel="stylesheet" type="text/css" href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js"></script>
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
body{
	background: url("resources/images/background/background(1).png"); 
	background-size: cover;
}
.sidenav {
  height: 100%; /* 100% Full-height */
  width: 0; /* 0 width - change this with JavaScript */
  position: fixed; /* Stay in place */
  z-index: 1; /* Stay on top */
  top: 0; /* Stay at the top */
  right: 0;
  background-color: white; /* Black*/
  background: url("resources/images/background/background.png") no-repeat; 
  overflow-x: hidden; /* Disable horizontal scroll */
  padding-top: 60px; /* Place content 60px from the top */
  transition: 0.5s; /* 0.5 second transition effect to slide in the sidenav */
}
.sidenav a {
  padding: 20px 8px 20px 32px;
  text-decoration: none;
  font-size: 18px;
  color: #00ffff;
  display: block;
  transition: 0.3s;
  border-bottom: 1px #00ffff solid;
}
</style>
</head>
<body>
	<!-- Following Menu -->
	<div class="ui top fixed inverted menu">
			<c:choose>
				<c:when test="${user.role.id==2}">
				<a href="khachthue/index.htm" class="item">
				<i class="red google icon"></i>trọ Việt Nam</a>
				</c:when>
				<c:otherwise>
				<a href="chutro/index.htm" class="item">
				<i class="red google icon"></i>trọ Việt Nam</a>
				</c:otherwise>
			</c:choose>
			
			<div class="right menu">
				<div class="item" onclick="openInfo()">
						<i class="mail icon"></i>
				</div>
				<img src="resources/images/avatar/${account.username}.png" style="border-radius: 50%; height: 55px" onclick="openNav()">
			</div>
		</div>
	<div class="sidenav" id="sidebar" onmouseleave="closeNav()">
		<div class="menu">
			<div class="item">
			<a href="${pageContext.servletContext.contextPath}/account/${account.username}.htm" class="item">
			<i class="user icon"></i>Thông tin tài khoản</a></div>
			<div class="item">
			<a class="item" onclick="$('.large.report.modal').modal('show');"><i class="exclamation triangle icon"></i> Báo lỗi </a></div>
			<div class="item">
			<a href="${pageContext.servletContext.contextPath}/logout.htm" class="item">
			<i class="logout icon"></i>Đăng xuất</a></div>
		</div>
	</div>
	<div class="sidenav" id="sideinfo" onmouseleave="closeInfo()">
		<div class="menu">
		<a onclick="closeInfo()" style="color: white"> <i class="mail icon"></i> Thông báo </a>
		<c:forEach var="thongbao" items="${thongbaos}" begin="0" end="10">
			<c:if test="${thongbao!=null}">
				<a href="${thongbao.link}" class="item">${thongbao.thongbao}</a>
			</c:if>
		</c:forEach>
		</div>
	</div>
	<div class="ui large report modal">
		<div class="header"> <i class="exclamation triangle icon"></i> Báo lỗi </div>
		<div class="content">
		<form action="report.htm" method="post">
			<div class="ui form">
				<div class="field">
					<textarea name="thongbao"></textarea>
				</div>
				<div class="field">
				<input type="hidden" name="username" value="${account.username}">
					<button type="submit" class="ui positive right labeled icon button">
						Gửi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<h1 class="ui inverted header">Padding</h1>
	<h1 style="text-align: center; color: #00ffff">KÊNH THÔNG TIN PHÒNG TRỌ SỐ MỘT VIỆT NAM</h1>
  	<div class="ui grid" style="padding-left: 15%">
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
$('.ui.dropdown').dropdown();
/* Set the width of the side navigation to 250px */
function openNav() {
  document.getElementById("sidebar").style.width = "250px";
}

/* Set the width of the side navigation to 0 */
function closeNav() {
  document.getElementById("sidebar").style.width = "0";
}
function openInfo() {
  document.getElementById("sideinfo").style.width = "250px";
}
/* Set the width of the side navigation to 0 */
function closeInfo() {
  document.getElementById("sideinfo").style.width = "0";
}
</script>
