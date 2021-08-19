<%@page import="ptithcm.entity.NhaTro"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="ptithcm.entity.DiaChi"%>
<%@page import="ptithcm.entity.Ward"%>
<%@page import="ptithcm.entity.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ptithcm.entity.Province"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<script src="resources/semantic/semantic.min.js" type="text/javascript"></script>
<script src="resources/semantic/semantic.js" type="text/javascript"></script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid" style="background: url(resources/images/background/background.png) repeat; background-size: cover;">
	<!-- BEGIN CONTEN -->
	<div class="right floated thirteen wide computer sixteen wide phone column" id="content">
		<div class="ui container grid" style="width: 100%; margin-top: 5px">
			<div class="row">
				<div class="fifteen wide computer sixteen wide phone centered column">
					<h2><a href="admin/nhatro.htm?chu=${chu}"> <i class="table icon"></i> Quản lý bài đăng </a></h2>
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
							<form:form class="ui large form" action="admin/${action}nhatro/${nhatro.id}.htm?chu=${chu}"
									modelAttribute="nhatro" enctype="multipart/form-data" method="post">
								<div class="ui stacked segment">
									<h2 class="ui big">
										<i class="signup icon"></i>
										<c:if test="${action=='add'}">Thêm bài đăng</c:if>
										<c:if test="${action=='edit'}">Cập nhật bài đăng</c:if>
									</h2>
									<div class="ui grid">
										<div class="twelve wide column">
											<div class="field">
												<label style="float: left;">Tiêu đề 
												<i style="color: red;"> *</i></label>
												<div class="ui left input">
													<form:input path="tieuDe" value="" type="text" />
													<form:errors style="color: red;font-size: 15px;" path="tieuDe" />
												</div>
											</div>
										</div>
										<div class="four wide column"><div class="field">
											<label style="float: left;">Trạng thái 
											<i style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:select path="tinhtrang"  >
												<form:option value="0">Chờ duyệt</form:option>
												<form:option value="1">Đã duyệt</form:option>
												<form:option value="-1">Không duyệt</form:option>
												</form:select>
												<form:errors style="color: red;font-size: 15px;" path="tieuDe" />
											</div>
										</div></div>
									</div>
									<div class="ui three column grid">
										<div class="column">
											<div class="field">
												<label for="chutro">Chủ trọ</label>
												<div class="ui left input">
													<c:if test="${action=='edit'}">
														<form:input id="chutro" path="chuTro.account.username" readonly="true" />
													</c:if>
													<c:if test="${action=='add'}">
														<form:select id="chutro" path="chuTro" items="${chutros}"
															itemLabel="account.username" itemValue="id" />
													</c:if>
													<i><form:errors style="color: red;font-size: 15px;"
															path="chuTro" /></i>
												</div>
											</div>
										</div>
										<div class="column">
											<label>Ảnh 1</label> 
											<input name="photo1" type="file" class="form-control"/>
										</div>
										<div class="column">
											<label>Ảnh 2</label> 
											<input name="photo2" type="file" class="form-control" />
										</div>
									</div>
									<div class="ui three column grid">
										<div class="column">
											<div class="field">
												<label><b>Số phòng cho thuê</b>
												<i style="color: red;"></i></label>
												<div class="ui left input">
													<form:input path="soPhongChoThue" value="" type="number"
														min="1" />
													<form:errors style="color: red;font-size: 15px;"
														path="soPhongChoThue" />
												</div>
											</div>
										</div>
										<div class="column">
											<div class="field">
												<label><b>Số phòng có sẵn</b>
												<i style="color: red;">*</i> </label>
												<div class="ui left input">
													<form:input path="soPhongCoSan" value="" type="number"
														min="0" max="${nhatro.soPhongChoThue}" />
													<form:errors style="color: red;font-size: 15px;"
														path="soPhongCoSan" />
												</div>
											</div>
										</div>
										<div class="column">
											<div class="field">
												<label><b>Số người mỗi phòng</b>
												<i style="color: red;"> *</i> </label>
												<div class="ui left input">
													<form:input path="soNguoiTrenPhong" value="" type="number"
														min="1" />
													<form:errors style="color: red;font-size: 15px;"
														path="soNguoiTrenPhong" />
												</div>
											</div>
										</div>
									</div>
									<div class="ui three column grid">
										<div class="column">
											<div class="field">
												<label><b>Diện tích</b> 
												<i style="color: red;">*</i> </label>
												<div class="ui left input">
													<form:input path="dienTich" value="" type="number" min="1" />
													<form:errors style="color: red;font-size: 15px;"
														path="dienTich" />
												</div>
											</div>
										</div>
										<div class="column">
											<div class="field">
												<label><b>Tiền thuê</b>
												<i style="color: red;">*</i> </label>
												<div class="ui left input">
													<form:input path="tienThue" value="" type="number" min="0"
														step="10000" />
													<form:errors style="color: red;font-size: 15px;"
														path="tienThue" />
												</div>
											</div>
										</div>
										<div class="column">
											<div class="field">
												<label><b>Tiền cọc</b>
												<i style="color: red;"> *</i></label>
												<div class="ui left input">
													<form:input path="tienCoc" value="" type="number" min="0"
														step="10000" />
													<form:errors style="color: red;font-size: 15px;"
														path="tienCoc" />
												</div>
											</div>
										</div>
									</div>
									<div class="field">
										<label style="float: left;">Mô tả 
										<i style="color: red;"> *</i></label>
										<div class="ui left input">
											<!-- <i class="user icon"></i> -->
											<form:textarea path="moTa" id="moTa"/>
											<i><form:errors style="color: red;font-size: 15px;" path="moTa"/></i>
										</div>
									</div>
									<div class="field">
										<label style="float: left;">Địa chỉ 
										<i style="color: red;"> *</i></label>
										<div class="ui left input">
											<form:input path="diachi.diaChi" value="" type="text" />
											<form:errors style="color: red;font-size: 15px;"
												path="diachi" />
										</div>
									</div>
									<div class="ui three column grid">
										<div class="column">
											<div class="field">
												<label><b>Tỉnh/Thành phố</b> 
												<i style="color: red;">*</i> </label>
												<div class="ui left input">
													<select class="ui search dropdown" id="comboboxProvince"
														required="required" name="province">
															<c:forEach var="p" items="${provinces}">
															<c:choose>
																<c:when
																	test="${p.id==nhatro.diachi.ward.district.province.id}">
																	<option value="${p.id}" selected="selected">${p.name}</option>
																</c:when>
																<c:otherwise>
																	<option value="${p.id}">${p.name}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select>
													<form:errors style="color: red;font-size: 15px;" path="" />
												</div>
											</div>
										</div>
											<div class="column">
											<div class="field">
												<label><b>Quận/Huyện</b><i style="color: red;">
														*</i> </label>
												<div class="ui left input">
													<select class="ui search dropdown" id="comboboxDistrict"
														required="required" name="district"><option
															disabled="disabled">--Chọn huyện--</option></select>
													<form:errors style="color: red;font-size: 15px;" path="" />
												</div>
											</div>
										</div>
										<div class="column">
											<div class="field">
												<label><b>Xã/Phường</b><i style="color: red;">
														*</i> </label>
												<div class="ui left input">
													<form:select path="diachi.ward.id"
														class="ui search dropdown" id="comboboxWard"
														required="required" name="ward">
														<form:options
															items="${nhatro.diachi.ward.district.wards}"
															itemLabel="name" itemValue="id" />
													</form:select>
													<form:errors style="color: red;font-size: 15px;" path="" />
												</div>
											</div>
										</div>
									</div>
										<button class="ui fluid large teal submit button"
											id="filterButton" style="margin-top: 5vh;">
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
	<script src="resources/ckfinder/ckfinder.js"></script>
	<script src= "resources/ckeditor/ckeditor.js"></script>
	<script type="text/javascript">
	$('.ui.dropdown').dropdown();
	
	var ckeditor=CKEDITOR.replace('moTa',{
		height: '600px',
	});
	CKFinder.setupCKEditor(ckeditor,'${pageContext.request.contextPath}/resources/ckfinder/');
	
let selected_province = "$('#comboboxProvince').find('option:selected')";
let selected_district = '';
let selected_ward = '';
let list_provinces = [];

init();

var preward ={};
var predist ={};
var preprov ={};
var prepos=0;
  loadpre();  

$('#comboboxProvince').change(function () {
    var optionSelected = $(this).find("option:selected");
    var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();

    selected_province = list_provinces.find((p)=>{
    	return p.id == valueSelected;
    })
    console.log(selected_province);
    let htmlDistricts = '<option disabled="disabled">--Chọn huyện--</option>';
    let districts = selected_province.districts;
    for(let i = 0; i < districts.length; i++) {
    	htmlDistricts += '<option value="' + districts[i].id + '">' + districts[i].name + '</option>';
	}
	$('#comboboxDistrict').html(htmlDistricts);
	document.getElementById("comboboxDistrict").selectedIndex = "0";
    
});

$('#comboboxDistrict').change(function () {
    var optionSelected = $(this).find("option:selected");
    var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();

    selected_district = selected_province.districts.find((d)=>{
    	return d.id == valueSelected;
    })
    console.log(selected_district);
    let htmlWards = ''; /* <option disabled="disabled">--Chọn xã--</option> */
    let wards = selected_district.wards;
    for(let i = 0; i < wards.length; i++) {
    	htmlWards += '<option value="' + wards[i].id + '">' + wards[i].name + '</option>';
	}
	$('#comboboxWard').html(htmlWards);
	document.getElementById("comboboxWard").selectedIndex = "0";
    
});

$('#comboboxWard').change(function () {
	var optionSelected = $(this).find("option:selected");
	var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();
    selected_ward = selected_district.wards.find((w)=>{
    	return w.id == valueSelected;
    })
    console.log(selected_ward);
})

$('#filterButton').click(function () {
	console.log(selected_ward)
})



function init() {
	let province ={};
	let district ={};
	let ward = {};
	
	<%ArrayList<Province> provinces = (ArrayList) request.getAttribute("provinces");%>
	<%for (Province p : provinces) {%>
			province = {
					name : "<%=p.getName()%>",
					id : <%=p.getId()%>,
					districts : []
			}
			
			<%List<District> districts = (List) p.getDistricts();%>
			<%for (District d : districts) {%>	
					district = {
							name : "<%=d.getName()%>",
							id : <%=d.getId()%>,
							wards : []
					}
					<%List<Ward> wards = (List) d.getWards();%>
					<%for (Ward w : wards) {%>
						ward = {
								name : "<%=w.getName()%>",
								id : <%=w.getId()%>
						}
						district.wards.push(ward);
					<%}%>
					province.districts.push(district);
			<%}%>
			list_provinces.push(province);	
   	<%}%>  
}
function loadpre() {
	<%NhaTro nhaTro = (NhaTro) request.getAttribute("nhatro");%>
	preward = {
			id:<%=nhaTro.getDiachi().getWard().getId()%>,
			name:"<%=nhaTro.getDiachi().getWard().getName()%>"
	}
	predist = {
			id:<%=nhaTro.getDiachi().getWard().getDistrict().getId()%>,
			name:"<%=nhaTro.getDiachi().getWard().getDistrict().getName()%>"
	}
	preprov = {
			id:<%=nhaTro.getDiachi().getWard().getDistrict().getProvince().getId()%>,
			name:"<%=nhaTro.getDiachi().getWard().getDistrict().getProvince().getName()%>"
	}
		
	
	selected_province = list_provinces.find((p)=>{
    	return p.id == preprov.id;
    })
    console.log(selected_province);
    let htmlDistricts = ''; 
    let districts = selected_province.districts;
    for(let i = 0; i < districts.length; i++) {
    	htmlDistricts += '<option value="' + districts[i].id + '">' + districts[i].name + '</option>';
	}
	$('#comboboxDistrict').html(htmlDistricts);
	for(let i = 0; i < selected_province.districts.length; i++) {
		if (selected_province.districts[i].id==predist.id) {
			selected_district =selected_province.districts[i];
    		prepos=i;
    	}
	}
	document.getElementById("comboboxDistrict").selectedIndex = prepos;
    console.log(selected_district);
    let htmlWards = '';
    let wards = selected_district.wards;
    for(let i = 0; i < wards.length; i++) {
    	htmlWards += '<option value="' + wards[i].id + '">' + wards[i].name + '</option>';
	}
	$('#comboboxWard').html(htmlWards);
	for(let i = 0; i < selected_district.wards.length; i++) {
		if (selected_district.wards[i].id==preward.id) {
			selected_ward =selected_district.wards[i];
    		prepos=i;
    	}
	}
	document.getElementById("comboboxWard").selectedIndex = prepos;
}


</script>
</body>
<!-- inject:js -->
<jsp:include page="footer.jsp"></jsp:include>
</html>