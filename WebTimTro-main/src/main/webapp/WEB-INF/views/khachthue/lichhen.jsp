<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>

<body style='background: url("resources/images/background/background(1).png") no-repeat; background-size: cover;'>
	<!-- Page Contents -->
	<div class="ui card" style="margin-left: 2%; position: fixed; background-color: white;">
			<div class="ui large image">
				<img src="resources/images/avatar/${sessionScope['username']}.png" style="border-radius: 5%" alt="">
			</div>
			<div class="content">
				<a class="header" href="khachthue/index.htm">${user.username}</a>
				<div class="meta">
				<i class="user icon"></i>
				${user.hoTen}</div>
				<div class="meta">
				<i class="calendar alternate outline icon"></i>
				Tham gia: ${user.ngayDangKy}</div>
			</div>
		</div>
	<div class="ui grid" style="padding-left:20%; margin-top: 17px; border-radius: 5px; overflow: auto;">
		<div class="fourteen wide column" style="color: #00ffff; border: 1px pink solid">
			<h2 style="text-align: center; color: #00ffff; "> Lịch hẹn xem phòng </h2>
			<div class="ui divided items">
				<table id="mytable" class="ui celled table responsive nowrap unstackable" style="width: 100%">
					<thead><tr>
						<th>Thời gian</th>
						<th>Nhà trọ</th>
						<th>Đồng ý</th>
						<th>Thành công</th>
						<th>Chỉnh sửa</th>
						<th>Hủy lịch hẹn</th>
					</tr></thead>
					<tbody>
						<c:forEach var="lichhen" items="${lichhens}">
							<tr>
								<td>${lichhen.thoigian}</td>
								<td><div class="ui items">
								<div class="item">
										<div class="tidy image"><img src="resources/images/nhatro/${lichhen.nhaTro.id}_1.png"></div>
									<div class="content">
										<a class="header" href="khachthue/nhatro/${lichhen.nhaTro.id}.htm">${lichhen.nhaTro.tieuDe}</a>
										<div class="meta">
											<span>Địa chỉ: ${lichhen.nhaTro.diachi.diaChi}/${lichhen.nhaTro.diachi.ward.name}/${lichhen.nhaTro.diachi.ward.district.name}/${lichhen.nhaTro.diachi.ward.district.province.name}</span>
										</div>
									</div>
								</div>
								</div></td>
								<td><c:choose>
									<c:when test="${lichhen.dongy}">
										<div class="ui icon">
											<i class="green check circle icon"></i>
										</div>
									</c:when>
									<c:otherwise>
										<div class="ui icon">
											<i class="red ban icon"></i>
										</div>
									</c:otherwise>
								</c:choose></td>
								<td><c:choose>
									<c:when test="${lichhen.thanhcong}">
										<div class="ui icon">
											<i class="green check circle icon"></i>
										</div>
									</c:when>
									<c:otherwise>
										<div class="ui icon">
											<i class="red ban icon"></i>
										</div>
									</c:otherwise>
								</c:choose></td>
								<td><div class="ui brown animated button" tabindex="0" onclick="thaydoi(${lichhen.id},'${lichhen.thoigian}')" ${lichhen.thanhcong?'disabled':''}>
									<div class="hidden content"> Thay đổi </div>
									<div class="visible content">
									   	<i class="edit icon"></i>	   	
									</div>
								</div></td>
								<td><div class="ui red animated button" tabindex="0">
									<div class="hidden content"><a href="khachthue/lichhen/huy/${lichhen.id}.htm">Hủy</a></div>
									<div class="visible content">
									   	<i class="close icon"></i>	   	
									</div>
								</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="ui mini thaydoi modal">
		<div class="content">
		<form action="khachthue/lichhen/td.htm" method="post">
			<div class="ui form">
				<div class="field">
					<label>Thời gian: </label>
					<input type="date" name="thoigian" id="thoigian">
				</div>
				<div class="field">
				<input type="hidden" name="id" id="id">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div></div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({
			on : 'hover'
		});
		function thaydoi(id, thoigian){
			$('.mini.thaydoi.modal').modal('show')
			document.getElementById("id").value= id;
			document.getElementById("thoigian").value= thoigian;
		};
		$('.ui.form').form({
			on: 'change',
			revalidate: 'true',
			transition:	'scale',
			delay:	'true',
			inline:	'true',
			fields: {
				thoigian: {
					identifier: 'thoigian',
					rules:[{
						type	: 'empty',
						prompt	: ' Không được để trống tiêu đề '
					}]
				},
			}
		});
	function kt(){
		return $('.ui.form').form('is valid')
	};
	</script>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>