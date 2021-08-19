<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid">		
		<div class="right floated thirteen wide computer sixteen wide phone column"
			id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div
						class="fifteen wide computer sixteen wide phone centered column">
						<h2>
							<a href="admin/thongbao.htm?user=${user}"> <i
								class="table icon"></i> Quản lý Thông báo
							</a>
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
									action="admin/${action}thongbao/${thongbao.id}.htm?user=${user}"
									modelAttribute="thongbao" method="post">
									<div class="ui stacked segment">
										<h2 class="ui big">
											<i class="signup icon"></i>
											<c:if test="${action=='add'}">Thêm Thông báo</c:if>
											<c:if test="${action=='edit'}">Cập nhật Thông báo</c:if>
										</h2>

										<div class="field">
											<label style="float: left;">Tài khoản </label>
											<div class="ui left input">
												<form:select path="account.username" items="${accounts}"
													itemLabel="username" itemValue="username">

												</form:select>
												<form:errors style="color: red;font-size: 15px;"
													path="account" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Thời gian <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="thoigian" type="date" required="required" />
												<form:errors style="color: red;font-size: 15px;"
													path="thoigian" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Nội dung <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="thongbao" required="required" />
												<form:errors style="color: red;font-size: 15px;"
													path="thongbao" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Link </label>
											<div class="ui left input">
												<form:select path="link">
													<form:option value="/account/">/account.htm</form:option>
													<form:option value="/khachthue/index.htm">/khachthue/index.htm</form:option>
													<form:option value="/khachthue/thongtinthem.htm">/khachthue/thongtinthem.htm</form:option>
													<form:option value="/khachthue/lichhen.htm">/khachthue/lichhen.htm</form:option>
													<form:option value="/chutro/index.htm">/chutro/index.htm</form:option>
													<form:option value="/chutro/lichhen.htm">/chutro/lichhen.htm</form:option>
													<form:option value="/chutro/thongke.htm">/chutro/thongke.htm</form:option>
												</form:select>

												<form:errors style="color: red;font-size: 15px;" path="link" />
											</div>
										</div>

										<button class="ui fluid large teal submit button">
											<c:if test="${action=='add'}">Thêm</c:if>
											<c:if test="${action=='edit'}">Cập nhật</c:if>
										</button>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<jsp:include page="footer.jsp"></jsp:include>
</html>