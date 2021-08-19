<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>
	<!-- Page Contents -->
<body style="background: url('resources/images/background/background(1).png'); background-size: cover;">
	<div class="ui grid" style="padding-left:15%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
			<jsp:include page="diachi.jsp"></jsp:include>
		</div>
	</div>
	
	<div class="four wide column" style="padding-left:2%; border-radius: 2%; background-color: white">
		<div class="ui segment column" style="position: fixed; max-width: 15%">
			<div class="ui small image">
				<img src="resources/images/avatar/${nhatro.chuTro.account.username}.png" style="border-radius:  10%">
			</div>
			<div class="content">
				<a class="header" href="account/chutro.htm?username=${nhatro.chuTro.account.username}">Tài khoản đăng: ${nhatro.chuTro.account.username}</a>
				<div class="meta">Người đăng: ${nhatro.chuTro.account.hoTen}</div>
				<div class="meta">Điện thoại liên lạc: ${nhatro.chuTro.account.dienThoai}</div>
				<div class="meta">Email: ${nhatro.chuTro.account.dienThoai}</div>
			</div>
			<hr>
			<form action="khachthue/datlich.htm" method="post">
			<h3>Đăng kí lịch xem phòng</h3>
			<c:if test="${dlerror!=null}"> <span style="color: red">${dlerror}</span> </c:if>
			<c:if test="${dlsuccess!=null}"> <span style="color: green">${dlsuccess}</span> </c:if>
			<div class="fluid ui input">
				<input type="date" name="thoigian" required="required">
				<input type="hidden" name="id" value="${nhatro.id}"> 
			</div>
			<br>
			<button class="fluid ui primary left labeled icon button" >
				<i class="calendar icon"></i> Đặt lịch
			</button>
			</form>
		</div>
	</div>
		<div class="ui grid" style="padding-left:15%; max-width: 150%; margin-top: 17px; border-radius: 5px; overflow: auto;" id="container">
		<div class="fourteen wide column" style="padding-left:2%; padding-right:2%; background-color: white; border-radius: 5px; padding-left: 2%">
			<div class="ui divided items">
				<div class="item">
					<div class="ui large image">
						<div class="ui slide masked reveal image">
							<img src="resources/images/nhatro/${nhatro.id}_1.png" class="visible content">
							<img src="resources/images/nhatro/${nhatro.id}_2.png" class="hidden content">
						</div>
					</div>
					<div class="content">
						<a class="header" href="khachthue/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
						<div class="meta">
							<span>Địa chỉ:
								${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
						</div>
						<div class="meta">
							<span>Diện tích: ${nhatro.dienTich} m2</span>
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
							<span>Giá thuê: ${nhatro.tienThue} vnd </span>
						</div>
						<div class="meta">
							<span>Tiền cọc: ${nhatro.tienCoc}vnd</span>
						</div>
						<div class="meta">
							<span>Điểm đánh giá: <i class="yellow star icon"></i> ${nhatro.diem} </span>
						</div>
						<div class="meta">
							<span>Ngày đăng: ${nhatro.ngayThem}</span>
						</div>
						<div class="extra">
							<span><i class="user icon"></i>${nhatro.soLuot} đã
								thuê. Trong đó</span>
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
			<br>
			</div>
	</div>
	</div>
	<div class="ui grid"style="padding-left:15%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color:white; border-radius: 5px;">
			<div class="ui comments" id="write-comment" style="padding-left: 10px;">
			<div class="ui dividing header">Đánh giá</div>
			<form class="ui reply form" action="khachthue/nhatro/comment.htm" method="post">
				<div class="inline field">
					<h4>Điểm đánh giá: </h4>
					<div class="rating">
					  <label>
					    <input type="radio" name="diem" value="1" />
					    <i class="star icon"></i>
					  </label>
					  <label>
					    <input type="radio" name="diem" value="2" />
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					  </label>
					  <label>
					    <input type="radio" name="diem" value="3" />
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>   
					  </label>
					  <label>
					    <input type="radio" name="diem" value="4" />
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					  </label>
					  <label>
					    <input type="radio" name="diem" value="5" checked/>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					    <i class="star icon"></i>
					  </label>
					</div>
					<input type="hidden" name="id" value="${nhatro.id}">
				</div>
				<div class="field">
					<h4>Để lại bình luận của bạn: </h4>
					<textarea name="comment"></textarea>
				</div>
				<button type="submit" class="ui green labeled submit icon button">
      				<i class="icon edit"></i> Gửi 
    			</button>
			</form>
			<c:forEach var="comment" items="${nhatro.comment}" begin="0" end="10">
				<div class="comment">
					<a class="avatar"><img src="resources/images/avatar/${comment.khachthue.account.username}.png"></a>
					<div class="content">
						<a class="author" href="account/khachthue.htm?username=${comment.khachthue.account.username}">${comment.khachthue.account.username}</a>
						<div class="metadata">
							<span class="date">${comment.thoiGian}</span>
							<div class="rating"><i class="star icon"></i>${comment.diem}</div>
						</div>	
						<div class="text">${comment.comment}</div>
						<c:if test="${comment.khachthue.account.username==user.username}">
						<a style="cursor: pointer;" onclick="chinhsua(${comment.id},${comment.diem},'${comment.comment}')"> Chỉnh sửa 		</a>
						<a href="khachthue/nhatro/xoacomment/${comment.id}.htm">Xóa</a>
						</c:if>
					</div>
				</div>
				<br>
			</c:forEach>
			</div>
		</div>
	</div>
	<div class="ui medium chinhsua modal">
		<div class="header">Chỉnh sửa comment</div>
			<div class="content">
				<div class="ui comments" id="write-comment" style="padding-left: 10px;">
				<div class="ui dividing header">Đánh giá</div>
				<form class="ui reply form" action="khachthue/nhatro/doicomment.htm" method="post">
					<div class="inline field">
						<h4>Điểm đánh giá: </h4>
						<div class="rating">
						  <label>
						    <input type="radio" name="diem" value="1" id="diem_1"/>
						    <i class="star icon"></i>
						  </label>
						  <label>
						    <input type="radio" name="diem" value="2" id="diem_2"/>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						  </label>
						  <label>
						    <input type="radio" name="diem" value="3" id="diem_3"/>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>   
						  </label>
						  <label>
						    <input type="radio" name="diem" value="4" id="diem_4"/>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						  </label>
						  <label>
						    <input type="radio" name="diem" value="5" id="diem_5" checked="checked"/>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						    <i class="star icon"></i>
						  </label>
						</div>
						<input type="hidden" name="idc" id="id">
						<input type="hidden" name="id" value="${nhatro.id}">
					</div>
					<div class="field">
						<h4>Để lại bình luận của bạn: </h4>
						<textarea name="comment" id="comment"></textarea>
					</div>
					<button type="submit" class="ui green labeled submit icon button">
	      				<i class="icon edit"></i> Chỉnh sửa 
	    			</button>
	    		</form>
    		</div>
		</div>
	</div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({on : 'hover'});
		$('.star.rating').rating({initialRating: 5, maxRating: 5}).rating('enable');
		$(':radio').change(function() {console.log('New star rating: ' + this.value);});
		function chinhsua(id,diem,comment){
			$('.medium.chinhsua.modal').modal('show');
			document.getElementById("id").value = id;
			document.getElementById("comment").value = comment.replaceAll('<br>','\r\n');
			document.getElementById("diem_" + parseInt(diem)).checked=true;
		}
	</script>
</body>
</html>