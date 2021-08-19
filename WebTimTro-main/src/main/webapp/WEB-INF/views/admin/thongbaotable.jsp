<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>
	<!-- BEGIN CONTEN -->
	<div class="ui grid">
	<div class="right floated thirteen wide computer sixteen wide phone column"
		id="content">
		<div class="ui container grid" style="width: 100%; margin-top: 5px">
			<div class="row">
				<div
					class="fifteen wide computer sixteen wide phone centered column">
					<h2>
						<i class="table icon"></i> Quản lý thông báo
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
							<a href="admin/addthongbao.htm?user=${user}"
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
									style="width: 100%; text-align: left;">
									<thead style="text-align: center;">
										<tr>
											<!-- <th>ID</th> -->
											<th>Tài khoản</th>
											<th>Thời gian</th>
											<th>Nội dung</th>
											<th>Link</th>
											<th>Action</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="i" items="${thongbaos}">
											<tr>
												<!-- style="background-color: lightpink;" -->
													<td><a
													href="admin/thongbao.htm?user=${i.account.username}">${i.account.username }</a></td>
												<td><f:formatDate value="${i.thoigian}"
														pattern="dd/MM/yyyy" /></td>
												<td>${i.thongbao}</td>
												<td>${i.link}</td>
													<td style="text-align: center;"><a
													href="admin/editthongbao/${i.id}.htm?user=${user}"><button
															class="positive ui button"
															style="color: white; font-size: 13px">Sửa</button></a> <a
													href="admin/deletethongbao/${i.id}.htm?user=${user}"><button
															class="negative ui button"
															style="color: white; font-size: 13px">Xóa</button></a></td>
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