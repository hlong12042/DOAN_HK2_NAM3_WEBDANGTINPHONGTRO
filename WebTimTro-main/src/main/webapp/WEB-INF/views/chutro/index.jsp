<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="ptithcm.entity.Province"%>
<%@page import="ptithcm.entity.Truong"%>
<%@page import="ptithcm.entity.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>
	<!-- Page Contents -->
		<div class="ui card" style="margin-left: 2%; position: fixed; background-color: white;">
			<div class="ui large image">
				<img src="resources/images/avatar/${account.username}.png" style="border-radius: 5%" alt="">
			</div>
			<div class="content">
				<a class="header" href="/account/${account.username}.htm">${account.username}</a>
				<div class="meta">
				<i class="user icon"></i>
				${account.hoTen}</div>
				<div class="meta">
				<i class="calendar alternate outline icon"></i>
				Tham gia: ${account.ngayDangKy}</div>
			</div>
		</div>
		<div class="ui grid" style="margin-left: 18%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color: white;padding-left:2%; border-radius: 5px;">
			<h1 style="color: red; padding-left: 2%"> Thành tích </h1>
			<br>
			<div class="ui five statistics">
				<div class="brown statistic">
					<div class="value">
					<i class="brown newspaper outline icon"></i>${account.chuTro.sobai}</div>
					<div class="label"><a href="chutro/thongke.htm"> BÀI ĐĂNG </a></div>
				</div>
				<div class="orange statistic">
					<div class="value">
					${account.chuTro.sobaidang}<i class="orange check circle icon"></i></div>
					<div class="label"><a href="chutro/thongke.htm"> BÀI ĐÃ DUYỆT </a></div>
				</div>
				<div class="red statistic">
					<div class="value">
					${account.chuTro.sobaivipham}<i class="red exclamation triangle icon"></i></div>
					<div class="label"><a href="chutro/thongke.htm"> BÀI VI PHẠM </a></div>
				</div>
				<div class="yellow statistic">
					<div class="value">
					${account.chuTro.diem}/5<i class="yellow star icon"></i></div>
					<div class="label"><a href="chutro/thongke.htm"> TRUNG BÌNH ĐIỂM BÀI VIẾT </a></div>
				</div>
			</div>
			<br>
			<div class="ui four statistics">
				<div class="teal statistic">
					<div class="value">
					${account.chuTro.soluotxem}<i class="teal users icon"></i></div>
					<div class="label"><a href="chutro/lichhen.htm"> SỐ LƯỢT ĐẶT LỊCH </a></div>
				</div>	
				<div class="blue statistic">
					<div class="value">
					${account.chuTro.thanhcong}<i class="blue check circle icon"></i></div>
					<div class="label"><a href="chutro/lichhen.htm"> THÀNH CÔNG </a></div>
				</div>
				<div class="violet statistic">
					<div class="value">
					${account.chuTro.cho}<i class="violet user plus icon"></i></div>
					<div class="label"><a href="chutro/lichhen.htm"> ĐANG CHỜ CHẤP NHẬN </a></div>
				</div>
			</div>
			<br>
			<hr style="color:silver">
			<h1 style="color: red; padding-left: 2%"> Bài đăng </h1>
			<br>
			<div class="ui cards">
			<c:forEach var="nhatro" items="${account.chuTro.nhaTro}">
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
							<a class="header" href="chutro/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
							<div class="meta">
								<span>Địa chỉ: ${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
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
				</c:forEach>
				</div>
		</div>
		</div>
	<script type="text/javascript">
	</script>
</body>

</html>