<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="ptithcm.entity.Province"%>
<%@page import="ptithcm.entity.Truong"%>
<%@page import="ptithcm.entity.KhachThue"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>
<!-- Page Contents -->
<div class="ui card"
	style="margin-left: 2%; position: fixed; border-radius: 10%">
	<div class="ui large image">
		<img src="resources/images/avatar/${account.username}.png"
			style="border-radius: 10%" alt="">
	</div>
	<div class="content">
		<a class="header" href="account/${account.username}.htm">${account.username}</a>
		<div class="meta">
			<i class="user icon"></i> ${account.hoTen}
		</div>
		<div class="meta">
			<i class="calendar icon"></i> Tham gia: ${account.ngayDangKy}
		</div>
	</div>
</div>
<div class="ui grid"
	style="margin-left: 20%; margin-top: 17px; border-radius: 5px">
	<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
		<h2 style="text-align: center; color: red; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black;"> Thông tin tài khoản </h2>
		<table class="ui table">
			<tbody>
				<tr>
					<td><h4>Họ tên:</h4></td>
					<td>${user.hoTen}</td>
					<td>
						<button class="circular ui basic icon button"
							onclick="$('.large.hoten.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button>
					</td>
				</tr>
				<tr>
					<td><h4>Password:</h4></td>
					<td>**********</td>
					<td>
						<button class="circular ui basic icon button"
							onclick="$('.large.password.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button>
					</td>
				</tr>
				<tr>
					<td><h4>Số chứng minh thư/căn cước</h4></td>
					<td>${user.cmnd}</td>
					<td>
						<button class="circular ui basic icon button"
							onclick="$('.large.cmnd.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button>
					</td>
				</tr>
				<tr>
					<td><h4>Điện thoại</h4></td>
					<td>${user.dienThoai}</td>
					<td>
						<button class="circular ui basic icon button"
							onclick="$('.large.dienthoai.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button>
					</td>
				</tr>
				<tr>
					<td><h4>Email</h4></td>
					<td>${user.email}</td>
					<td>
						<button class="circular ui basic icon button"
							onclick="$('.large.email.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button>
					</td>
				</tr>
				<tr>
					<td><h4>Ảnh đại diện:</h4></td>
					<td>${user.username}.png</td>
					<td><button class="circular ui basic icon button"
							onclick="$('.large.avata.modal').modal('show');">
							<i class="icon teal edit"></i>
						</button></td>
				</tr>
				<tr>
					<td><h4>Tài khoản:</h4></td>
					<td>${user.role.name}</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<div class="ui large hoten modal">
			<div class="header">Chỉnh sửa họ tên</div>
			<div class="content">
				<form action="account/doihoten/${user.username}.htm" method="post">
					<div class="ui form">
						<div class="field">
							<label>Họ tên: </label> <input class="ui input" type="text"
								name="hoten" value="${user.hoTen}" required="required">
						</div>
						<div class="field">
							<label>Password: </label> <input class="ui input" type="password"
								name="password" required="required">
						</div>
						<div class="field">
							<button type="submit"
								class="ui positive right labeled icon button">
								Lưu thay đổi <i class="checkmark icon"></i>
							</button>
						</div>

					</div>
				</form>
			</div>
		</div>
		<div class="ui large password modal">
			<div class="header">Thay đổi password</div>
			<div class="content">
				<form action="account/doipassword/${user.username}.htm" method="post">
					<div class="ui form">
						<div class="field">
							<label>Password cũ: </label> <input class="ui input"
								type="password" name="oldpassword" required="required">
						</div>
						<div class="field">
							<label>Password mới: </label> <input class="ui input"
								type="password" name="password" required="required">
						</div>
						<div class="field">
							<label>Nhập lại password: </label> <input class="ui input"
								type="password" name="repassword" required="required">
						</div>
						<div class="field">
							<button type="submit"
								class="ui positive right labeled icon button">
								Lưu thay đổi <i class="checkmark icon"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="ui large cmnd modal">
			<div class="header">Chỉnh sửa số chứng minh thư/căn cước</div>
			<div class="content">
				<form action="account/doicmnd/${user.username}.htm" method="post">
					<div class="ui form">
						<div class="field">
							<label>Chứng minh thư/căn cước </label> <input class="ui input"
								type="text" name="cmnd" value="${user.cmnd}" required="required">
						</div>
						<div class="field">
							<label>Password: </label> <input class="ui input" type="password"
								name="password" required="required">
						</div>
						<div class="field">
							<button type="submit"
								class="ui positive right labeled icon button">
								Lưu thay đổi <i class="checkmark icon"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="ui large dienthoai modal">
			<div class="header">Chỉnh sửa số điện thoại</div>
			<div class="content">
				<form action="account/doisdt/${user.username}.htm" method="post">
					<div class="ui form">
						<div class="field">
							<label>Số điện thoại: </label> <input class="ui input"
								type="text" name="sdt" value="${user.dienThoai}" required="required">
						</div>
						<div class="field">
							<label>Password: </label> <input class="ui input" type="password"
								name="password" required="required">
						</div>
						<div class="field">
							<button type="submit"
								class="ui positive right labeled icon button">
								Lưu thay đổi <i class="checkmark icon"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>

		<div class="ui large email modal">
			<div class="header">Chỉnh sửa email</div>
			<div class="content">
				<form action="account/doiemail/${user.username}.htm" method="post">
					<div class="ui form">
						<div class="field">
							<label>Email: </label> <input class="ui input" type="text"
								name="email" value="${user.email}" required="required">
						</div>
						<div class="field">
							<label>Password: </label> <input class="ui input" type="password"
								name="password" required="required">
						</div>
						<div class="field">
							<button class="ui positive right labeled icon button">
								Lưu thay đổi <i class="checkmark icon"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="ui large avata modal">
			<div class="header">Đổi avata</div>
			<div class="image content">
				<img class="ui medium image"
					src="resources/images/avatar/${user.username}.png" alt="">
				<div class="description">
					<form action="account/doiavata/${user.username}.htm" method="post"
						enctype="multipart/form-data">
						<div class="ui form">
							<div class="field">
								<label>Tải avata mới: </label> <input class="ui input"
									type="file" name="avata">
							</div>
							<div class="field">
								<label>Password: </label> <input class="ui input"
									type="password" name="password">
							</div>
							<div class="field">
								<button class="ui positive right labeled icon button">
									Lưu thay đổi <i class="checkmark icon"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$('.special.cards .image').dimmer({
		on : 'hover'
	});
	$('.ui.form').form({
		on : 'change',
		revalidate : 'true',
		transition : 'scale',
		delay : 'true',
		inline : 'true',
		fields : {
			hoten : {
				identifier : 'hoten',
				rules : [ {
					type : 'empty',
					prompt : ' Không được để trống ! '
				} ]
			},
			password : {
				identifier : 'password',
				rules : [ {
					type : 'empty',
					prompt : ' Không được để trống '
				} ]
			},
			cmnd : {
				identifier : 'cmnd',
				rules : [ {
					type : 'regExp[//d]',
					prompt : ' Chưa đúng định dạng '
				} ]
			},
			sdt : {
				identifier : 'sdt',
				rules : [ {
					type : 'regExp[//d]',
					prompt : ' Chưa đúng định dạng '
				} ]
			},
			avata : {
				identifier : 'avata',
				rules : [ {
					type : 'empty',
					prompt : ' Không được để trống '
				} ]
			},
			email : {
				identifier : 'email',
				rules : [ {
					type : 'email',
					prompt : ' Chưa đúng định dạng '
				} ]
			},
		}
	});
	function kt() {
		return $('.ui.form').form('is valid')
	};
</script>
<body></body>

</html>