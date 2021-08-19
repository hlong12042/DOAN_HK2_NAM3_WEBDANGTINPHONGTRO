<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid">
		<!-- BEGIN CONTEN -->
		<div class="right floated thirteen wide computer sixteen wide phone column"
			id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div
						class="fifteen wide computer sixteen wide phone centered column">
						<h2>
							<i class="table icon"></i> Quản lý khách thuê
						</h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div
								class="sixteen wide computer sixteen wide phone left floated column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>

								<!-- BEGIN DATATABLE -->
								<a href="admin/addaccount.htm"
									style="font-size: 16px; color: white;">
									<button class="ui green icon label button"
										style="margin-right: 0; font-size: 16px;">
										<i class="plus square outline icon"
											style="padding-right: 0.5vw;"></i> Thêm
									</button>
								</a>
								<div class="ui stacked segment rig">


									<table id="mytable"
										class="ui celled table responsive nowrap unstackable"
										style="width: 100%; text-align: center;">
										<thead style="text-align: center;">
											<tr>
												<th>Username</th>
												<th>Avatar</th>
												<th>Trường</th>
												<th>Năm sinh</th>
												<th>Giới tính</th>
												<th>Quê quán</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${khachthues}">
												<tr>
													<td>${i.account.username}</td>
													<td><img style="max-width: 45px;" alt=""
														src="resources/images/avatar/${i.account.username}.png"></td>
													<td>${i.truong.ten}</td>
													<td>${i.namSinh}</td>
													<td>${i.gioiTinh?"Nam":"Nữ"}</td>
													<td>${i.queQuan}</td>
													<td style="text-align: center;"><a href="admin/editkhachthue/${i.id}.htm">
														<button class="positive ui button" style="color: white; font-size: 13px">Sửa thông tin thêm</button></a>
														<a href="admin/editaccount/${i.account.username}.htm">
														<button class="positive ui button" style="color: white; font-size: 13px">Sửa tài khoản</button></a> 
														<a href="admin/deleteaccount/${i.account.username}.htm">
														<button class="negative ui button" style="color: white; font-size: 13px">Xóa</button></a>
													</td>
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