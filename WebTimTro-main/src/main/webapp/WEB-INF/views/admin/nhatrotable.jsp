<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid" style="background: url(resources/images/background/background.png) repeat; background-size: cover;">	
		<!-- BEGIN CONTEN -->
		<div class="right floated thirteen wide computer sixteen wide phone column" id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div class="fifteen wide computer sixteen wide phone centered column">
						<h2>
							<i class="table icon"></i> Quản lý bài đăng
						</h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div
								class="sixteen wide computer sixteen wide phone centered column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>

								<!-- BEGIN DATATABLE -->
								<%-- <a href="admin/addnhatro.htm?chu=${chu}"
									style="font-size: 16px; color: white;">
									<button class="ui green icon label button"
										style="margin-right: 0; font-size: 16px;">
										<i class="plus square outline icon"
											style="padding-right: 0.5vw;"></i> Thêm
									</button>
								</a> --%>
								<div class="ui stacked segment rig" style="overflow: auto;">
									<table id="mytable" class="ui celled table responsive nowrap unstackable"
										style="width: 100%;">
										<thead style="text-align: center;">
											<tr>
												<!-- <th>ID</th> -->
												<th>Chủ trọ</th>
												<th>Tiêu đề</th>
												<th>Địa chỉ</th>
												<th>Số phòng</th>
												<th>Số người</th>
												<th>Có sẵn</th>
												<th>Diện tích</th>
												<th>Tiền cọc</th>
												<th>Tiền thuê</th>
												<th>Điểm</th>
												<th>Ngày thêm</th>
												<th>Tình trạng</th>
												<th>Action</th>
												<th>Mô tả</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${nhatros}">
												<c:if test="${i.tinhtrang==-1}">
													<tr style="background-color: lightpink;">
												</c:if>
												<c:if test="${i.tinhtrang==0}">
													<tr style="background-color: lightgoldenrodyellow">
												</c:if>
												<c:if test="${i.tinhtrang==1}">
													<tr style="background-color: lightgreen;">
												</c:if>
												<%-- <td>${i.id}</td> --%>
												<%-- <td><img style="max-width: 45px;" alt=""
														src="resources/images/avatar/${i.account.username}.png"></td> --%>
												<td>${i.chuTro.account.username}</td>
												<td>${i.tieuDe}</td>
												<td>${i.diachi.diaChi},
													${i.diachi.ward.name}, ${i.diachi.ward.district.name}, ${i.diachi.ward.district.province.name}
												</td>
												<td>${i.soPhongChoThue}</td>
												<td>${i.soNguoiTrenPhong}</td>
												<td>${i.soPhongCoSan}</td>
												<td><f:formatNumber type="number" currencySymbol=""
														maxFractionDigits="0">
                                                                                    ${i.dienTich}</f:formatNumber>m<sup>2</sup>
												</td>
												<td><f:formatNumber type="currency" currencySymbol=""
														maxFractionDigits="0">
                                                                                    ${i.tienCoc}</f:formatNumber><sup>đ</sup>
												</td>
												<td><f:formatNumber type="currency" currencySymbol=""
														maxFractionDigits="0">
                                                                                    ${i.tienThue}</f:formatNumber><sup>đ</sup>
												</td>
												<td>${i.diem}</td>
												<td>${i.ngayThem}</td>
												<td><c:if test="${i.tinhtrang==-1}">Vi Phạm</c:if> <c:if
														test="${i.tinhtrang==0}">Chưa duyệt</c:if> <c:if
														test="${i.tinhtrang==1}">Đã duyệt</c:if></td>

												<td style="text-align: center;"><c:if
														test="${i.tinhtrang==0}">
														<a href="admin/approve/${i.id}.htm?chu=${chu}"><button
																class="positive ui button"
																style="color: white; font-size: 13px">Duyệt</button></a>
														<a href="admin/refuse/${i.id}.htm?chu=${chu}"><button
																class="negative ui button"
																style="color: white; font-size: 13px">Vi phạm</button></a>
													</c:if><a href="admin/editnhatro/${i.id}.htm?chu=${chu}"><button
															class="primary ui button"
															style="color: white; font-size: 13px">Sửa</button></a> <%-- <a href="admin/deletenhatro/${i.id}.htm"><button
																class="negative ui button"
																style="color: white; font-size: 13px">Xóa</button></a> --%></td>
												<td>${i.moTa}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
								<!-- END DATATABLE -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END CONTENT -->
	</div>
</body>
<!-- inject:js -->
<jsp:include page="footer.jsp"></jsp:include>

</html>