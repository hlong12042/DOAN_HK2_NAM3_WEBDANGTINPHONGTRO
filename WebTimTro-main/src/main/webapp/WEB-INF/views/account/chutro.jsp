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
		<img src="resources/images/avatar/${user.username}.png"
			style="border-radius: 10%" alt="">
	</div>
	<div class="content">
		<a class="header" href="account/chutro?username=${user.username}.htm">${user.username}</a>
		<div class="meta">
			<i class="user icon"></i> ${user.hoTen}
		</div>
		<div class="meta">
			<i class="calendar icon"></i> Tham gia: ${user.ngayDangKy}
		</div>
	</div>
</div>
<div class="ui grid"
	style="margin-left: 20%; margin-top: 17px; border-radius: 5px">
	<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
		<h2 style="padding-left: 2%; color: red"> Thông tin tài khoản </h2>
		<table class="ui table">
			<tbody>
				<tr>
					<td><h4>Họ tên:</h4></td>
					<td>${user.hoTen}</td>
				</tr>
				<tr>
					<td><h4>Điện thoại</h4></td>
					<td>${user.dienThoai}</td>
				</tr>
				<tr>
					<td><h4>Email</h4></td>
					<td>${user.email}</td>
				</tr>
				<tr>
					<td><h4>Tài khoản:</h4></td>
					<td>${user.role.name}</td>
				</tr>
			</tbody>
		</table>
		<hr style="color:silver">
		<h2 style="color: red; padding-left: 2%"> Bài đăng </h2>
		<br>
		<div class="ui cards">
		<c:forEach var="nhatro" items="${user.chuTro.nhaTro}">
		<c:if test="${nhatro.tinhtrang==1}">
				<div class="card">
					<div class="ui medium image">
					<div class="header">
					<div class="ui slide masked reveal image">
						<img src="resources/images/nhatro/${nhatro.id}_1.png" class="visible content">
						<img src="resources/images/nhatro/${nhatro.id}_2.png" class="hidden content">
					</div>
					</div>
				</div>
						<div class="content">
							<a class="header" href="account/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
						<div class="meta">
							<span>Địa chỉ: ${nhatro.diachi.diaChi}, ${nhatro.diachi.ward.name}, ${nhatro.diachi.ward.district.name}, ${nhatro.diachi.ward.district.province.name}</span>
						</div>
						<div class="meta">
							<span>Diện tích: ${nhatro.dienTich}m2</span>
						</div>
						<div class="meta">
							<span>${nhatro.soNguoiTrenPhong} người/phòng</span>
						</div>
						<div class="meta">
							<span>Giá thuê: ${nhatro.tienThue}vnd</span>
						</div>
						<div class="meta">
							<span>Tiền cọc: ${nhatro.tienCoc}vnd</span>
						</div>
					</div>	
					<div class="extra content">
						<div class="meta">
							<span>Điểm đánh giá: ${nhatro.diem}<i class="yellow star icon"></i></span>
						</div>
						<div class="meta">
							<span>Ngày đăng: ${nhatro.ngayThem}</span>
						</div>
						<div class="extra">
							<span><i class="user icon"></i>${nhatro.soLuot}
								đã thuê</span>
						</div>
					</div>
					<br>
				</div>
			</c:if>
			</c:forEach>
			</div>
	</div>
</div>
<script type="text/javascript">
	$('.special.cards .image').dimmer({
		on : 'hover'
	});
</script>
<body></body>

</html>