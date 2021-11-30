<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
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
<!-- <script>
  $(document)
    .ready(function() {
      $('.ui.form')
        .form({
          fields: {
            email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your e-mail'
                },
                {
                  type   : 'email',
                  prompt : 'Please enter a valid e-mail'
                }
              ]
            },
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your password'
                },
                {
                  type   : 'length[6]',
                  prompt : 'Your password must be at least 6 characters'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script> -->
</head>

<body id="homelogin">
	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<form:form class="ui large form" action="password.htm" modelAttribute="account" method="post">
				<div class="ui stacked segment">
					<h2 class="ui big">
						<i class="signup icon"></i> Quên mật khẩu
					</h2>
					<div class="field">
						<div class="ui left icon input">
							<i class="user icon"></i>
							<form:input path="username" placeholder="Tài khoản" />
						</div>
					</div>
					<i style="color: red; float: left;"><form:errors
							path="username"></form:errors></i>
					<div class="field">
						<div class="ui left icon input">
							<i class="mail icon"></i>
							<form:input path="email"
								type="email" placeholder="Email" />
						</div>
					</div>
					<i style="color: red; float: left;"><form:errors
							path="email"></form:errors></i>
					<button class="ui fluid large teal submit button">Lấy lại password</button>
				</div>
				<div class="ui error message"></div>
			</form:form>
			<div class="card-footer text-center" style="margin-top: 5px">

				<div class="ui message">
					Đã có tài khoản? <a href="login.htm"> Đăng nhập</a>
					<hr>
					Chưa có tài khoản? <a href="register.htm"> Đăng ký!</a>
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