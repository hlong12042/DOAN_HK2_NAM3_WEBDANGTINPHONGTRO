<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Login</title>
<!-- <link href="resources/css/styles.css" rel="stylesheet" /> -->

<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js"></script>
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
	max-width: 450px;
}
</style>
</head>
<body id="homelogin">
	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<form:form class="ui large form" action="login.htm" modelAttribute="account">
				<div class="ui stacked segment">
					<h2 class="ui big">
						<i class="signup icon"></i> Đăng nhập </h2>
					<div class="field">
						<i style="color: red; float: left;"><form:errors
							path="username"></form:errors></i>
						<div class="ui left icon input">
							<i class="user icon"></i>
							<form:input path="username" placeholder="Tài khoản" />
						</div>
					</div>
					<div class="field">
						<i style="color: red; float: left;"><form:errors
							path="password"></form:errors></i>
						<div class="ui left icon input">
							<i class="lock icon"></i>
							<form:input path="password" value="${user.password}"
								type="password" placeholder="Mật khẩu" />

						</div>
					</div>
					<button class="ui fluid large teal submit button">Đăng
						nhập</button>
				</div>
				<div class="ui error message"></div>
			</form:form>
			<div class="card-footer text-center" style="margin-top: 5px">
				<div class="ui message">
					<a href="password.htm">Quên mật khẩu</a>
					<hr> 
					Chưa có tài khoản? <a href="register.htm"> Đăng ký!</a>
				</div>
			</div>
			<div>${message}</div>
		</div>
	</div>
</body>

</html>