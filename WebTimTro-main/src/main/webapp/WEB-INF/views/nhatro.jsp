<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>
<body style='background: url(resources/images/background/background.png); background-size: cover;'>
	<!-- Page Contents -->

	<div class="ui grid" style="padding-left:15%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
			<div class="row" style="margin-top: 10px">
				<jsp:include page="diachi.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="five wide column" style="padding-left:2%; border-radius: 2%; background-color: white">
		<div class="ui segment column" style="position: fixed;">
			<div class="ui small image">
				<img src="resources/images/avatar/${nhatro.chuTro.account.username}.png" style="border-radius:  10%">
			</div>
			<div class="content">
				<div class="header">Người đăng:	${nhatro.chuTro.account.hoTen}</div>
				<div class="meta">Điện thoại:	${nhatro.chuTro.account.dienThoai}</div>
				<div class="meta">Email:	${nhatro.chuTro.account.dienThoai}</div>
			</div>
			<hr>
			<h3>Đăng kí lịch xem phòng</h3>
			<c:if test="${datlich!=null}"> <span style="color: red">${datlich}</span> </c:if>
			<div class="fluid ui input">
				<input type="date" disabled="disabled" onclick="$('.ui.basic.modal').modal('show');">
			</div>
			<br>
			<button class="fluid ui primary left labeled icon button" onclick="$('.ui.basic.modal').modal('show');">
				<i class="calendar icon"></i> Đặt lịch
			</button>
			<div class="ui basic modal">
			  <div class="ui icon header">
			    <i class="yellow exclamation triangle icon"></i>Thông báo</div>
			  <div class="content">
			    <p>Bạn cần đăng kí tài khoản để sử dụng tính năng này! </p>
			  </div>
			  <div class="actions">
			    <div class="ui red basic cancel inverted button">
			      <i class="remove icon"></i> Đóng
			    </div>
			  </div>
			</div>
		</div>
	</div>
		<div class="ui grid" style="padding-left:15%; max-width: 150%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="padding-left:2%; padding-right:2%; background-color: white; border-radius: 5px; padding-left: 2%">
			<div class="ui divided items">
				<c:if test="${message!=null}">
					<div class="alert alert-danger" role="alert" id="alert">
						<h3>${message}</h3>
					</div>
				</c:if>
				<div class="item">
					<div class="ui large image">
						<div class="ui slide masked reveal image">
							<img src="resources/images/nhatro/${nhatro.id}_1.png" class="visible content">
							<img src="resources/images/nhatro/${nhatro.id}_2.png" class="hidden content">
						</div>
					</div>
					<div class="content">
						<a class="divided header" href="khachthue/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
						<div class="meta">
							<span>Địa chỉ:
								${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
						</div>
						<div class="meta">
							<span>Diện tích: <f:formatNumber type="number" currencySymbol=""
														maxFractionDigits="2">${nhatro.dienTich} </f:formatNumber>m<sup>2</sup></span>
						</div>
						<div class="meta">
							<span>${nhatro.soNguoiTrenPhong} người/phòng</span>
						</div>
						<div class="meta">
							<span>Số phòng cho thuê: ${nhatro.soPhongChoThue} phòng</span>
						</div>
						<div class="meta">
							<span>Số phòng đã thuê: ${nhatro.soPhongChoThue-nhatro.soPhongCoSan} phòng</span>
						</div>
						<div class="meta">
							<span>Giá thuê: <f:formatNumber type="currency" currencySymbol=""
														maxFractionDigits="0">${nhatro.tienThue}</f:formatNumber>vnd</span>
						</div>
						<div class="meta">
							<span>Tiền cọc: <f:formatNumber type="currency" currencySymbol=""
														maxFractionDigits="0">${nhatro.tienCoc}</f:formatNumber>vnd</span>
						</div>
						<div class="meta">
							<span>Điểm đánh giá: <i class="yellow star icon"></i> ${nhatro.diem} </span>
						</div>
						<div class="meta">
							<span>Ngày thêm: ${nhatro.ngayThem}</span>
						</div>
						<div class="extra">
							<span><i class="user icon"></i>${nhatro.soLuot} đã
								thuê</span>
							<c:if test="${nhatro.gioitinh[0]&&nhatro.gioitinh[1]>0}">
								<span><i class="pink female icon"></i>${nhatro.gioitinh[1]} nam đã thuê</span>
							</c:if>
							<c:if test="${!nhatro.gioitinh[0]&&nhatro.gioitinh[1]>0}">
								<span><i class="blue male icon"></i>${nhatro.gioitinh[1]} nữ đã thuê</span>
							</c:if>
						</div>
					</div>
					<br>
				</div>
				<br>
				<div>
					${nhatro.moTa}
				</div>
			</div>
		</div>
	</div>
	<div class="ui grid"style="padding-left:15%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color:white; border-radius: 5px;">
			<div class="ui comments" id="write-comment" style="padding-left: 10px;">
			<div class="ui dividing header">Đánh giá</div>
			<c:forEach var="comment" items="${nhatro.comment}" begin="0" end="10">
				<div class="comment">
					<a class="avatar"><img src="resources/images/avatar/${comment.khachthue.account.username}.png"></a>
					<div class="content">
						<a class="author">${comment.khachthue.account.username}</a>
						<div class="metadata">
							<span class="date">${comment.thoiGian}</span>
							<div class="rating"><i class="star icon"></i>${comment.diem}</div>
						</div>	
						<div class="text">${comment.comment}</div>
					</div>
				</div>
			</c:forEach>
			</div>
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