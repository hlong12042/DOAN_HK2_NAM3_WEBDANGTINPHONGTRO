<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid">	
		<!-- BEGIN CONTEN -->
		<div class="right floated thirteen wide computer sixteen wide phone column" id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div class="fifteen wide computer sixteen wide phone centered column">
						<h2><i class="map icon"></i> Quản lý trường </h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div class="sixteen wide computer sixteen wide phone centered column">
								<c:if test="${success!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Thành Công!</div>
										<p>${success}</p>
									</div>
								</c:if>
								<c:if test="${error!=null}">
									<div class="ui negative message">
										<i class="close icon"></i>
										<div class="header">Lỗi! </div>
										<p>${error}</p>
									</div>
								</c:if>
								<!-- BEGIN DATATABLE -->
								<div class="ui stacked segment rig" style="overflow: auto;">
									<table id="mytable" class="ui celled table responsive nowrap unstackable"
										style="width: 100%;">
										<thead style="text-align: center;">
											<tr>
												<th>ID</th>
												<th>Trường</th>
												<th>Tỉnh/Thành phố</th>
												<th>Lệnh</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${truongs}">
												<tr>
												<td>${i.id}</td>
												<td>${i.ten}</td>
												<td>${i.province.name}</td>
												<td class="center aligned">
												<button class="ui primary button" onclick="showModal(${i.id}, '${i.ten}', '${i.province.id}')"> Sửa </button>
												</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<!-- END DATATABLE -->
							</div>
							<div class="right aligned sixteen wide computer sixteen wide phone centered column">
							<form action="admin/truong/themtruong.htm" method="post">
							<div class="ui grid">
								<div class="seven wide column"><div class="ui fluid input">
								<input name="ten" placeholder="Tên trường">
								</div></div>
								<div class="seven wide column">
									<select class="ui fluid search dropdown" required="required" name="idprovince">
									<option disabled="disabled">--Chọn tỉnh/thành phố--</option>
									<c:forEach var="p" items="${provinces}">
										<option value="${p.id}">${p.name}</option>
									</c:forEach>
									</select>
								</div>
								<div class="two wide column"><button type="submit" class="ui positive icon button"> <i class="plus icon"></i> Thêm trường </button></div>
							</div>
							</form>
							</div>
							<div class="right aligned sixteen wide computer sixteen wide phone centered column">
								<span class="meta">Lưu ý: 
								Những thông tin này không thể xóa, chúng ảnh hưởng nghiêm trọng đến các thông tin liên quan.</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END CONTENT -->
	</div>
	<div class="ui large chinhsuatruong modal">
			<div class="header"> Chỉnh sửa trường </div>
			<div class="content">
				<form action="admin/truong/chinhsuatruong.htm" method="post">
					<div class="ui form">
						<div class="field">
							<b> Tên trường </b>
							<input type="hidden" name="idtruong" id="idtruong">
							<input class="ui input" type="text" name="ten" id="ten">
						</div>
						<div class="field">
							<b>Tỉnh/Thành phố</b>
							<select class="ui search dropdown" required="required" name="idprovince">
								<option disabled="disabled">--Chọn tỉnh/thành phố--</option>
								<c:forEach var="p" items="${provinces}">
									<option value="${p.id}" id="${p.id}">${p.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="field">
							<button type="submit" class="ui positive button">Lưu</button>
						</div>
					</div>
				</form>
			</div>
		</div>
</body>
<!-- inject:js -->
<jsp:include page="footer.jsp"></jsp:include>
<script type="text/javascript">
	function showModal(idtruong, ten, idprovince){
		document.getElementById('idtruong').value = idtruong;
		document.getElementById('ten').value = ten;
		document.getElementById(idprovince).selected = true;
		$('.large.chinhsuatruong.modal').modal('show');
	}
</script>
</html>