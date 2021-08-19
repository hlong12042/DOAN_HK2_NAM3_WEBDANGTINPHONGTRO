<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid">
		<!-- BEGIN CONTEN -->
		<div class="right floated thirteen wide computer sixteen wide phone column"
			id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div class="fifteen wide computer sixteen wide phone centered column">
						<h2> <a href="admin/account.htm"> 
						<i class="table icon"></i> Quản lý tài khoản </a>
						</h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div class="ten wide computer sixteen wide phone centered column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>

								<form:form class="ui large form"
									action="admin/${action}account.htm" modelAttribute="account"
									enctype="multipart/form-data" method="post">
									<div class="ui stacked segment">
										<h2 class="ui big">
											<i class="signup icon"></i>
											<c:if test="${action=='add'}">Thêm tài khoản</c:if>
											<c:if test="${action=='edit'}">Cập nhật tài khoản</c:if>
										</h2>

										<div class="field">
											<label style="float: left;">Avatar</label>
											<div class="ui left input">
												<!-- 	<i class="lock icon"></i> -->
												<input name="photo" type="file" class="form-control" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Tài khoản<b
												style="color: red;">*</b></label>
											<div class="ui left input">

												<c:if test="${action=='edit'}">
													<form:input path="username" value="${user.username}"
														type="text" placeholder="Tài khoản" readonly="true" />
												</c:if>
												<c:if test="${action=='add'}">
													<form:input path="username" value="${user.username}"
														type="text" placeholder="Tài khoản" />
												</c:if>
												<i><form:errors style="color: red;font-size: 15px;"
														path="username" /></i>
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Mật khẩu<b
												style="color: red;">*</b></label>
											<div class="ui left input">
												<!-- 	<i class="lock icon"></i> -->
												<form:input path="password" value="${user.password}"
													type="password" placeholder="Mật khẩu" />
												<form:errors style="color: red;font-size: 15px;"
													path="password" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Họ tên <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="hoTen" type="text" placeholder="Họ tên" />
												<form:errors style="color: red;font-size: 15px;"
													path="hoTen" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">CMND <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="cmnd" type="text" placeholder="CMND" />
												<form:errors style="color: red;font-size: 15px;" path="cmnd" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">SĐT <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="dienThoai" type="text" placeholder="SĐT" />
												<form:errors style="color: red;font-size: 15px;"
													path="dienThoai" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Email <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="email" value="${user.email}" type="email"
													placeholder="Email address" />
												<form:errors style="color: red;font-size: 15px;"
													path="email" />
											</div>
										</div>

										<c:if test="${action=='add'}">
											<div class="ui form">
												<div class="grouped fields">
													<label style="float: left;">Loại tài khoản</label>
													<div class="field" style="float: left;">
														<div class="ui radio checkbox">
															<input type="radio" checked="checked" name="roles"
																value="2"> <label>Khách thuê</label>
														</div>
													</div>
													<div class="field" style="float: left;">
														<div class="ui radio checkbox">
															<input type="radio" name="roles" value="1"> <label>Chủ
																trọ</label>
														</div>
													</div>
												</div>
											</div>
										</c:if>
										<button class="ui fluid large teal submit button">
											<c:if test="${action=='add'}">Thêm</c:if>
											<c:if test="${action=='edit'}">Cập nhật</c:if>
										</button>
									</div>
								</form:form>
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