<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>Register</title>
<!-- <link href="resources/css/styles.css" rel="stylesheet" /> -->

<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js" type="text/javascript"></script>
<style type="text/css">
#homelogin {
	background: url(resources/images/razerlogin.jpg) center top no-repeat;
	background-size: cover;
	-webkit-background-size: cover;
	background-attachment: fixed;
}

body {
	background-color: #DADADA;
}
body>.grid {
	height: 100%;
}
.image {
	margin-top: -100px;
}
.column {
	max-width: 550px;
}
</style>
</head>
<body id="homelogin">
	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<form:form class="ui large form" action="register.htm" modelAttribute="account" method="post">
				<div class="ui stacked segment">
					<h2 class="ui big"> <i class="signup icon"></i> Tạo tài khoản </h2>
					<div class="field">
						<label style="float: left;">Tài khoản<b style="color: red;">*</b></label>
						<div class="ui left icon input">
							<i class="user icon"></i>
							<form:input path="username" value="${user.username}" type="text" placeholder="Tài khoản" />
						</div>
							<i><form:errors style="color: red;font-size: 15px;"
									path="username" /></i>
					</div>

					<div class="field">
						<label style="float: left;">Mật khẩu<b style="color: red;">*</b></label>
						<div class="ui left icon input">
							<i class="lock icon"></i>
							<form:input id="password" path="password" value="${user.password}" type="password" placeholder="Mật khẩu" />
							<i class="lock icon"></i>
							<input name="repassword" value="${user.password}" type="password" placeholder="Nhập lại mật khẩu">
						</div>
						<i><form:errors style="color: red;font-size: 15px;" path="password" /></i>
					</div>

					<div class="field">
						<label style="float: left;">Họ tên <i style="color: red;">*</i></label>
						<div class="ui left input">
							<form:input path="hoTen" type="text" placeholder="Họ tên" />
						</div>
						<i><form:errors style="color: red;font-size: 15px;" path="hoTen"/></i>
					</div>
					<div class="field">
						<label style="float: left;">CMND <i style="color: red;">*</i></label>
						<div class="ui left input">
							<form:input path="cmnd" type="text" placeholder="CMND" />
						</div>
						<i><form:errors style="color: red;font-size: 15px;" path="cmnd" /></i>
					</div>

					<div class="field">
						<label style="float: left;">SĐT <i style="color: red;">*</i></label>
						<div class="ui left input">
							<form:input path="dienThoai" type="text" placeholder="SĐT" />
						</div>
						<i><form:errors style="color: red;font-size: 15px;" path="dienThoai" /></i>
					</div>

					<div class="field">
						<label style="float: left;">Email <i style="color: red;">*</i></label>
						<div class="ui left input">
							<form:input path="email" value="${user.email}" type="email" placeholder="Email address" />
						</div>
						<i><form:errors style="color: red;font-size: 15px;" path="email" /></i>
					</div>

					<div class="ui form">
						<div class="grouped fields">
							<label style="float: left;">Mục đích của bạn là gì?</label>
							<div class="field" style="float: left;">
								<div class="ui radio checkbox">
									<input type="radio"  checked="checked" name="roles" value="2">
									<label>Tìm trọ</label>
								</div>
							</div>
							<div class="field" style="float: left;">
								<div class="ui radio checkbox">
									<input type="radio" name="roles" value="1"> <label>Cho thuê trọ</label>
								</div>
							</div>
						</div>
					</div>
					<c:if test="${message!=null}"><div style="color: red; font: italic;">${message}</div></c:if>
					<button class="ui fluid large teal submit button">Tạo tài khoản</button>
				</div>
			</form:form>
			<div class="card-footer text-center" style="margin-top: 5px">
				<div class="ui message">
					Đã có tài khoản? <a href="login.htm"> Đăng nhập</a>
				</div>
			</div>
			<c:if test="${success!=null}"> 
				<div class="ui green message"> 
					<i class="close icon"></i> 
		  			<div class="header"> Thành Công! </div>
		  			<p> ${success} </p> 
				</div> 
			</c:if>
			<c:if test="${error!=null}"> 
	  		<div class="ui red message"> 
	  			<i class="close icon"></i> 
	  			<div class="header"> Lỗi! </div>
	  			<p> ${error} </p> 
	  		</div> 
	  	</c:if>
		</div>
	</div>
</body>

</html>