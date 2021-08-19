<%@page import="java.util.List"%>
<%@page import="ptithcm.entity.Ward"%>
<%@page import="ptithcm.entity.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ptithcm.entity.Province"%>
<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="resources/semantic/semantic.css">
<script src="resources/semantic/semantic.js" type="text/javascript"></script>

<form method="get" action="timkiem.htm" >		
	<div class="ui grid" style="padding-left:10%; backgroundz: white; border-radius: 5px; max-width: 120%; margin-top: 17px;">
			<div class="four wide column">
				<b>Tỉnh/Thành phố</b> <select class="fluid ui search selection dropdown" id="comboboxProvince" name="province">
					<option disabled="disabled">--Chọn tỉnh/thành phố--</option>
					<c:forEach var="p" items="${provinces}">
						<option value="${p.id}">${p.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="four wide column">
				<b>Quận/Huyện</b> 
				<select class="fluid ui search selection dropdown" id="comboboxDistrict" name="district">
					<option disabled="disabled">--Chọn huyện--</option></select>
			</div>
			<div class="four wide column">
				<b>Xã/Phường</b> 
				<select class="fluid ui search selection dropdown" id="comboboxWard" name="ward">
					<option disabled="disabled">--Chọn xã--</option></select>
			</div>
			<div class="four wide column">
				<b></b>
				<button class="ui left icon primary button" style="margin-top: 20px;"><i class="search icon"></i>Tìmkiếm</button>
			</div>
		</div>
	</form>
	<div class="row" style="padding-left:10%;">
		<div class="ui text container big breadcrumb">
			<h3>
				<c:if test="${province!=null}">
					<a href="khachthue/timkiem.htm?province=${province.id}">${province.name}</a>
					<c:if test="${district!=null}">
						<a href="khachthue/timkiem.htm?province=${province.id}&amp;district=${district.id}">, ${district.name}</a>
						<c:if test="${ward!=null}">
							<a href="khachthue/timkiem.htm?province=${province.id}&amp;district=${district.id}&amp;ward=${ward.id}">, ${ward.name} </a>
						</c:if>
					</c:if>
				</c:if>
			</h3>
		</div>
	</div>
	<script type="text/javascript">
	$('.ui.dropdown').dropdown();
	
let selected_province = "$('#comboboxProvince').find('option:selected')";
let selected_district = '';
let selected_ward = '';
let list_provinces = [];

init();
document.getElementById("comboboxProvince").selectedIndex = "0";

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
    let htmlWards = '<option disabled="disabled">--Chọn xã--</option>';
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
	$('#result').html("<a href='" + selected_province.name + ".htm'>"  + selected_province.name +  " / " + "<a href='" + selected_district.name + ".htm'>"  + selected_district.name +  " / " + "<a href='" + selected_ward.name + ".htm'>"  + selected_ward.name)
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
</script>