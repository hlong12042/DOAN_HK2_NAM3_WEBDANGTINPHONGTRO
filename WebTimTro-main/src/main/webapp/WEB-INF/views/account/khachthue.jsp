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
		<a class="header" href="account/khachthue?username=${user.username}.htm">Tài khoản: ${user.username}</a>
		<div class="meta">
			<i class="user icon"></i> Họ tên: ${user.hoTen}
		</div>
		<div class="meta">
			<i class="calendar icon"></i> Tham gia: ${user.ngayDangKy}
		</div>
	</div>
</div>
<div class="ui grid"
	style="margin-left: 20%; margin-top: 17px; border-radius: 5px">
	<div class="fourteen wide column" style="background-color: white; border-radius: 5px; overflow: auto">
		<h2 style="padding-left: 2%; color:red"> Thông tin tài khoản </h2>
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
				<tr>
					<td><h4>Trường theo học: </h4></td>
					<td>${user.khachThue.truong.ten}</td>
					<td></td>
				</tr>
				<tr>
					<td><h4>Năm Sinh: </h4></td>
					<td>${user.khachThue.namSinh}</td>
					<td></td>
				</tr>
				<tr>
					<td><h4>Giới tính: </h4></td>
					<td>${user.khachThue.gioiTinh?'Nữ':'Nam'}</td>
					<td></td>
				</tr>
				<tr>
					<td><h4>Quê quán: </h4></td>
					<td>${user.khachThue.queQuan}</td>
					<td></td>
				</tr>
			</tbody>
		</table>
		<hr style="color: silver">
		<h2 style="padding-left: 2%; color:red"> Nhà trọ đã thuê </h2>
		<table id="mytable" class="ui celled table responsive nowrap unstackable" style="width: 100%">
		<thead>
			<tr>
			<th> Nhà trọ </th>
			<th> Thời gian </th>
			<th> Đánh giá </th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${user.khachThue.lichHen}" var="lichhen">
			<c:if test="${lichhen.thanhcong}">
			<tr>
				<td>
				<div class="card">
					<div class="ui medium image">
					<div class="header">
					<div class="ui slide masked reveal image">
						<img src="resources/images/nhatro/${lichhen.nhaTro.id}_1.png" class="visible content">
						<img src="resources/images/nhatro/${lichhen.nhaTro.id}_2.png" class="hidden content">
					</div>
					</div>
				</div>
						<div class="content">
							<a class="header" href="account/nhatro/${lichhen.nhaTro.id}.htm">${lichhen.nhaTro.tieuDe}</a>
						<div class="meta">
							<span>Địa chỉ: ${lichhen.nhaTro.diachi.diaChi}, ${lichhen.nhaTro.diachi.ward.name}, ${lichhen.nhaTro.diachi.ward.district.name}, ${lichhen.nhaTro.diachi.ward.district.province.name}</span>
						</div>
						</div>
				</div>
				</td>
				<td>
					${lichhen.thoigian}
				</td>
				<td>
				 <c:forEach items="${lichhen.nhaTro.comment}" var="comment">
				 	<div class="item">
						<div class="content">
							<a class="author" href="account/khachthue.htm?username=${comment.khachthue.account.username}">${comment.khachthue.account.username}</a>
							<div class="metadata">
								<span class="date">${comment.thoiGian}</span>
								<div class="rating"><i class="star icon"></i>${comment.diem}</div>
							</div>	
							<div class="text">${comment.comment}</div>
						</div>
					</div>
				 </c:forEach>
				<td>
			</tr>
			</c:if>
		</c:forEach>
		</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
	$('.special.cards .image').dimmer({
		on : 'hover'
	});
</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>