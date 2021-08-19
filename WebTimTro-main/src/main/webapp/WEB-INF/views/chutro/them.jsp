<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="java.util.List"%>
<%@page import="ptithcm.entity.Ward"%>
<%@page import="ptithcm.entity.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ptithcm.entity.Province"%>
<jsp:include page="header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
	<!-- Page Contents -->
	<div class="ui grid" style="margin-left: 10%; margin-top: 17px; margin-right: 10%">
	<div class="twenty wide column" style="background-color: white;padding-left:2%; border-radius: 5px;">
	<form action="chutro/${featurename}.htm" method="post" name="form" onsubmit="return kt()" enctype="multipart/form-data">
	<div class="ui form"> 
		<input type="hidden" name="id" value="${nhatro.id}">
		<h1 class="ui dividing header" style="color: red">
		<c:choose>
			<c:when test="${featureid==0}"> Tạo bài đăng </c:when>
			<c:otherwise> Chỉnh sửa </c:otherwise>
		</c:choose>
		</h1>
		<div class="field">
			<label> Ảnh tiêu đề 1</label>
			<input name="anh1" type="file" value="resources/images/nhatro/${nhatro.id}_1.png">
			<label> Ảnh tiêu đề 2</label>
			<input name="anh2" type="file" value="resources/images/nhatro/${nhatro.id}_2.png">
		</div>
		<div class="field">
			<label> Tiêu đề </label>
			<input name="tieuDe" value="${nhatro.tieuDe}" placeholder="Tiêu đề">
		</div>	
		<div class="field">
				<label> Địa chỉ </label>
				<input name="diachi" value="${nhatro.diachi.diachi}" placeholder="Số nhà">
		</div>
		<div class="three fields">
			<div class="field">
				<label> Tỉnh/Thành phố </label> 
				<select class="fluid ui search selection dropdown" id="comboboxProvince" name="province">
					<option disabled="disabled">--Chọn tỉnh--</option>
					<c:forEach var="p" items="${provinces}">
						<option value="${p.id}">${p.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="field">
				<label> Quận/Huyện </label> 
				<select class="fluid ui search selection dropdown" id="comboboxDistrict" name="district">
					<option disabled="disabled">--Chọn huyện--</option></select>
			</div>
			<div class="field">
				<label>Xã/Phường</label> 
				<select class="fluid ui search selection dropdown" id="comboboxWard" name="ward">
					<option disabled="disabled">--Chọn xã--</option></select>
			</div>
		</div>
		<div class="three fields">
			<div class="field">
				<label> Tổng số phòng </label>
				<input name="soPhongChoThue" type="number" value="${nhatro.soPhongChoThue}" placeholder="1">
			</div>
			<div class="field">
				<label> Số người trên phòng </label>
				<input name="soNguoiTrenPhong" type="number" value="${nhatro.soNguoiTrenPhong}" placeholder="1">
			</div>
			<div class="field">
				<label> Số phòng cho thuê </label>
				<input name="soPhongCoSan" type="number" value="${nhatro.soPhongCoSan}" placeholder="1">
			</div>
		</div>
		<div class="three fields">
			<div class="field">
				<label> Diện tích (m2) </label>
				<input name="dienTich" value="${nhatro.dienTich}" placeholder="2">
			</div>
			<div class="field">
				<label> Tiền Cọc (vnd) </label>
				<input name="tienCoc" value="${nhatro.tienCoc}" placeholder="1000000">
			</div>
			<div class="field">
				<label> Giá thuê (vnd) </label>
				<input name="tienThue" value="${nhatro.tienThue}" placeholder="1000000">
			</div>
		</div>
		<div class="field">
			<label> Nội dung </label>
			<textarea id="mota" name="mota" cols="100" rows="200" placeholder="Nội dung">${nhatro.moTa}</textarea>
		</div>
		<button class="ui divided green labeled icon submit button" type="submit">
			<c:choose>
				<c:when test="${featureid==0}"> Tạo bài đăng </c:when>
				<c:otherwise> Chỉnh sửa </c:otherwise>
			</c:choose>
			<i class="check icon"></i>
		</button>
		<div class="ui error message"></div>
	</div>
	</form>
	</div>
	</div>
	
	<script type="text/javascript">
		var ckeditor=CKEDITOR.replace('mota');
		CKFinder.setupCKEditor(ckeditor,'${pageContext.request.contextPath}/resources/ckfinder/');
		
		$('.special.cards .image').dimmer({on : 'hover'});
		$('.star.rating').rating({initialRating: 5, maxRating: 5}).rating('enable');
		$(':radio').change(function() {console.log('New star rating: ' + this.value);});
		
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
		
	$('.ui.form').form({
		on: 'change',
		revalidate: 'true',
		transition:	'scale',
		delay:	'true',
		inline:	'true',
		fields: {
			tieuDe: {
				identifier: 'tieuDe',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống tiêu đề '
				}]
			},
			diachi: {
				identifier: 'diachi',
				rules:[{
					type	: 'empty',
					prompt	: ' Nhập đầy đủ địa chỉ '
				}]
			},
			province: {
				identifier: 'province',
				rules:[{
					type	: 'empty',
					prompt	: ' Nhập đầy đủ địa chỉ '
				}]
			},
			district: {
				identifier: 'district',
				rules:[{
					type	: 'empty',
					prompt	: ' Nhập đầy đủ địa chỉ '
				}]
			},
			ward	: {
				identifier: 'ward',
				rules:[{
					type	: 'empty',
					prompt	: ' Nhập đầy đủ địa chỉ '
				}]
			},
			soPhongChoThue: {
				identifier: 'soPhongChoThue',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống tổng số Phòng'
				},{
					type	: 'integer[1..100]',
					prompt : ' Nhập đúng định dạng số !'
				}]
			},
			soNguoiTrenPhong: {
				identifier: 'soNguoiTrenPhong',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống số người trên phòng '
				},{
					type	: 'integer[1..100]',
					prompt : ' Nhập đúng định dạng số !'
				}]
			},
			soPhongCoSan: {
				identifier: 'soPhongCoSan',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống số phòng có sẵn'
				},{
					type	: 'integer[1..100]',
					prompt : ' Nhập đúng định dạng số !'
				}]
			},
			dienTich: {
				identifier: 'dienTich',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống diện tích '
				},{
					type	: 'decimal',
					prompt : ' Nhập đúng định dạng !'
				}]
			},
			tienCoc: {
				identifier: 'tienCoc',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống tiền cọc'
				},{
					type	: 'decimal',
					prompt : ' Nhập đúng định dạng !'
				}]
			},
			tienThue: {
				identifier: 'tienThue',
				rules:[{
					type	: 'empty',
					prompt	: ' Không được để trống giá thuê'
				},{
					type	: 'decimal',
					prompt : ' Nhập đúng định dạng !'
				}]
			},
		}
	});
	function kt(){
		return $('.ui.form').form('is valid');
	};
	</script>
</body>
</html>