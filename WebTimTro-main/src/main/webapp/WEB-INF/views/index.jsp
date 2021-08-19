<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>
<body style="background: url('resources/images/background/background.png'); background-size: cover;">
	<!-- Page Contents -->
	<div class="five wide column" style="padding-left: 2%;">
		<div class="ui segment column" style="position: fixed; max-width: 10%">
			<form action="loc.htm" method="post">
			<h4>Điểm đánh giá</h4>
			<div class="ui checkbox">
				<input type="radio" name="diem" value="1"><label>⭐     </label> <br>
			</div>
			<div class="ui checkbox">
				<input type="radio" name="diem" value="2"> <label>⭐⭐   </label> <br>
			</div>
			<div class="ui checkbox">
				<input type="radio" name="diem" value="3"> <label>⭐⭐⭐  </label> <br>
			</div>
			<div class="ui checkbox">
				<input type="radio" name="diem" value="4"> <label>⭐⭐⭐⭐ </label> <br>
			</div>	
			<div class="ui checkbox">
				<input type="radio" name="diem" value="5"> <label>⭐⭐⭐⭐⭐</label> <br>
			</div>
			<div class="ui checkbox" hidden="true">
				<input type="radio" name="diem" value="" checked="checked"> <br>
			</div>
			<br>
			<h4>Số lượt đánh giá</h4>	
			<div class="ui checkbox">
				<input type="radio" name="soluot" value="0">  <label>Bài vừa được đăng</label> <br>
			</div>	 
			<div class="ui checkbox">
				<input type="radio" name="soluot" value="10">  <label>10+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="soluot" value="20"> <label>20+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="soluot" value="50"> <label>50+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="soluot" value="100"> <label>100+</label> <br>
			</div>
			<div class="ui checkbox" hidden="true">
				<input type="radio" name="soluot" value="" checked="checked"><br>
			</div>
			<br>
			<h4>Số người trên phòng</h4>
			<div class="ui checkbox">
				<input type="radio" name="songuoi" value="1"> <label>1</label> <br>
			</div>	 
			<div class="ui checkbox">
				<input type="radio" name="songuoi" value="2"> <label>2+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="songuoi" value="4"> <label>4+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="songuoi" value="8"> <label>8+</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="songuoi" value="12"> <label>12+</label> <br>
			</div>
			<div class="ui checkbox" hidden="true">
				<input type="radio" name="songuoi" value="" checked="checked"> <br>
			</div>	
			<br>
			<h4>Giá thuê</h4>
			<div class="ui checkbox">
				<input type="radio" name="giathue" value="1000000">  <label>-1 000 000</label> <br>
			</div>	 
			<div class="ui checkbox">
				<input type="radio" name="giathue" value="2000000">  <label>-2 000 000</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="giathue" value="3000000"> <label>-3 000 000</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="giathue" value="5000000"> <label>-5 000 000</label> <br>
			</div>
			<div class="ui checkbox">	
				<input type="radio" name="giathue" value="10000000"> <label>-10 000 000</label> <br>
			</div>
			<div class="ui checkbox" hidden="true">
				<input type="radio" name="giathue" value="" checked="checked"> <br>
			</div>
			<br>
			<button class="fluid ui primary left labeled icon button">
  				<i class="right arrow icon"></i>Lọc</button>
			</form>
		</div>
		</div>
	<div class="ui grid" style="padding-left:15%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
			<jsp:include page="diachi.jsp"></jsp:include>
		</div>
	</div>
	<div class="ui grid" style="padding-left:15%; max-width: 150%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color: white; border-radius: 5px">
			<br>
			<div class="ui divided items" style="padding-left: 2%; padding-right: 5%">
				<c:if test="${nhatros!=null}">
				<c:forEach var="nhatro" items="${nhatros}" begin="${page*10-10}" end="${page*10}">
					<div class="item">
						<div class="ui small image">
							<div class="image">
								<a class="header" href="nhatro/${nhatro.id}.htm">
									<img src="resources/images/nhatro/${nhatro.id}_1.png" alt="">
								</a>
							</div>
						</div>	
						<div class="content">
							<a class="header" href="nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
							<div class="meta">
								<span>Địa chỉ: ${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
							</div>
							<div class="meta">
								<span>Diện tích: <f:formatNumber type="number" currencySymbol=""
														maxFractionDigits="2">${nhatro.dienTich} </f:formatNumber>m<sup>2</sup> </span>
							</div>
							<div class="meta">
								<span>${nhatro.soNguoiTrenPhong} người/phòng</span>
							</div>
							<div class="meta">
								<span>Giá thuê: <f:formatNumber type="currency" currencySymbol=""
														maxFractionDigits="0">${nhatro.tienThue}</f:formatNumber> vnd</span>
							</div>
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
				</c:if>
			</div>
			<form action="timkiem.htm" class="ui grid">
			<div class="fourteen wide column">
				<b>Trang: </b>
				<div class="ui input"><input name="page" value="${page}" required="required" min="1" type="number"></div>
			</div>
			</form>
		</div>
	</div>
	<div></div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({
			on : 'hover'
		});
	</script>
</body>
</html>