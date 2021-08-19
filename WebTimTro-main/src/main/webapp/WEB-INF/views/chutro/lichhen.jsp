<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>

<body>
	<!-- Page Contents -->
	<div class="ui grid" style="margin-top: 17px; margin-left:2%; margin-right:2%; border-radius: 5px">
		<div class="fiveteen wide column" style="color: #00ffff; border: 1px pink solid">
			<h2 style="text-align: center; color: #00ffff; "> Lịch hẹn xem phòng </h2>	
			<div class="ui divided items">
				<table id="mytable" class="ui celled table responsive nowrap unstackable" style="width: 100%">
					<thead><tr>
						<th >Với</th>
						<th>Thời gian</th>
						<th>Nhà trọ</th>
						<th>Đồng ý</th>
						<th>Thành công</th>
						<th>Thay đổi ngày</th>
						<th>Hủy lịch hẹn</th>
					</tr></thead>
					<tbody>
						<c:forEach var="lichhen" items="${lichhens}">
							<tr>
								<td><div class="ui card">
									<div class="ui tiny image">
										<img src="resources/images/avatar/${lichhen.khachThue.account.username}.png" style="border-radius: 10%" alt="">
									</div>
									<div class="content">
										<a class="header" href="account/khachthue?username=${lichhen.khachThue.account.username}"><i class="user icon"></i> ${lichhen.khachThue.account.username}</a>
										<div class="meta">
											<span><i class="user icon"></i> ${lichhen.khachThue.account.hoTen}</span>
										</div>
										<div class="meta">
											<span><i class="mobile alternate icon"></i> ${lichhen.khachThue.account.dienThoai}</span>
										</div>
										<div class="meta">
											<span><i class="mail icon"></i> ${lichhen.khachThue.account.email}</span>
										</div>
										
									</div>
								</div></td>
								<td>${lichhen.thoigian}</td>
								<td><div class="ui items">
								<div class="item">
										<div class="image"><img src="resources/images/nhatro/${lichhen.nhaTro.id}_1.png"></div>
									<div class="content">
										<a class="header" href="chutro/nhatro/${lichhen.nhaTro.id}.htm">${lichhen.nhaTro.tieuDe}</a>
										<div class="meta">
											<span>Địa chỉ: ${lichhen.nhaTro.diachi.diaChi}/${lichhen.nhaTro.diachi.ward.name}/${lichhen.nhaTro.diachi.ward.district.name}/${lichhen.nhaTro.diachi.ward.district.province.name}</span>
										</div>
									</div>
								</div>
								</div></td>
								<td class="center aligned"><c:choose>
									<c:when test="${lichhen.dongy}">
										<h2> <i class="green check circle outline icon"></i> </h2> <br>
										<a href="chutro/lichhen/kodongy/${lichhen.id}.htm" class="basic circular ui icon button"> <i class=" red ban icon"></i> </a>
									</c:when>
									<c:otherwise>
										<h2> <i class="red ban icon"></i> </h2> <br>
										<a href="chutro/lichhen/dongy/${lichhen.id}.htm" class="basic circular ui icon button"> <i class="green check circle outline icon"> </i></a>
									</c:otherwise>
								</c:choose></td>
								<td class="center aligned"><c:choose>
									<c:when test="${lichhen.thanhcong}">
										<h2> <i class="green check circle outline icon"></i> </h2> <br>
										<a href="chutro/lichhen/kothanhcong/${lichhen.id}.htm" class="basic circular ui icon button"> <i class=" red ban icon"></i> </a>
									</c:when>
									<c:otherwise>
										<h2> <i class="red ban icon"></i> </h2> <br>
										<a href="chutro/lichhen/thanhcong/${lichhen.id}.htm" class="basic circular ui icon button"> <i class="green check circle outline icon"> </i> </a>
									</c:otherwise>
								</c:choose></td>
								<td class="center aligned">
								<div class="basic circular ui icon button" onclick="thaydoi(${lichhen.id},'${lichhen.thoigian}')" ${lichhen.thanhcong?'disabled':''}>
								<i class="teal edit icon"></i>
								</div></td>
								<td class="center aligned">
								<a href="chutro/lichhen/huy/${lichhen.id}.htm" class="basic circular ui icon button"><i class="red x icon"></i></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div></div>
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