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
							<a href="admin/khachthue.htm"> <i class="table icon"></i>
								Quản lý khách thuê
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
									action="admin/${action}khachthue/${khachthue.id}.htm"
									modelAttribute="khachthue" method="post">
									<div class="ui stacked segment">
										<h2 class="ui big">
											<i class="signup icon"></i>
											<c:if test="${action=='add'}">Thêm khách thuê</c:if>
											<c:if test="${action=='edit'}">Cập nhật khách thuê</c:if>
										</h2>

										<div class="field">
											<label style="float: left;">Trường </label>
											<div class="ui left input">
												<form:input path="truong.ten" type="text"
													placeholder="Trường" />
												<form:errors style="color: red;font-size: 15px;"
													path="truong" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Năm sinh <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="namSinh" type="number" min="1950"
													max="2005" placeholder="Năm sinh" />
												<form:errors style="color: red;font-size: 15px;"
													path="namSinh" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Giới tính <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:select path="gioiTinh">
													<form:option value="1">Nam</form:option>
													<form:option value="0">Nữ</form:option>
												</form:select>
												<form:errors style="color: red;font-size: 15px;"
													path="gioiTinh" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Quê quán </label>
											<div class="ui left input">
												<form:input path="queQuan" type="text"
													placeholder="Quê quán" />
												<form:errors style="color: red;font-size: 15px;"
													path="queQuan" />
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