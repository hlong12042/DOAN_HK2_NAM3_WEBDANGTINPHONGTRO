<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="java.util.List"%>
<%@page import="ptithcm.entity.Ward"%>
<%@page import="ptithcm.entity.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ptithcm.entity.Province"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>
	<!-- Page Contents -->
	<div class="ui card" style="margin-left: 2%; position: fixed; background-color: white;">
			<div class="ui large image">
				<img src="resources/images/avatar/${sessionScope['username']}.png" style="border-radius: 5%" alt="">
			</div>
			<div class="content">
				<a class="header" href="chutro/index.htm">${user.username}</a>
				<div class="meta">
				<i class="user icon"></i>
				${user.hoTen}</div>
				<div class="meta">
				<i class="calendar alternate outline icon"></i>
				Tham gia: ${user.ngayDangKy}</div>
			</div>
		</div>
		<div class="ui grid" style="padding-left:20%; max-width: 150%; margin-top: 17px; border-radius: 5px" id="container">
		<div class="fourteen wide column" style="padding-left:2%; padding-right:2%; background-color: white; border-radius: 5px">
			<div class="ui divided items">
				<div class="item">
					<div class="ui large image">
						<div class="ui slide masked reveal image">
							<img src="resources/images/nhatro/${nhatro.id}_1.png" class="visible content">
							<img src="resources/images/nhatro/${nhatro.id}_2.png" class="hidden content">
						</div>
					</div>
					<div class="content">
						<div class="meta">
						<table class="ui celled table">
						<tbody>
						<tr>
							<td>ID: </td>
							<td>${nhatro.id} </td>
							<td></td>
						</tr>
						<tr>
							<td>Tiêu đề: </td>
							<td><h4><a class="header" href="chutro/nhatro/${nhatro.id}.htm" style="color: red">${nhatro.tieuDe}</a></h4> </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.tieude.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Địa chỉ: </td>
							<td>${nhatro.diachi.diaChi}, ${nhatro.diachi.ward.name}, ${nhatro.diachi.ward.district.name}, ${nhatro.diachi.ward.district.province.name}</td>
							<td><div class="basic circular ui icon button" onclick="$('.large.diachi.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Diện tích: </td>
							<td>${nhatro.dienTich} m2 </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.dientich.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Số người/phòng: </td>
							<td>${nhatro.soNguoiTrenPhong} </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.songuoitrenphong.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Tổng số phòng: </td>
							<td>${nhatro.soPhongChoThue} </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.sophongchothue.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Còn: </td>
							<td>${nhatro.soPhongCoSan} phòng </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.sophongcosan.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Giá thuê: </td>
							<td>${nhatro.tienThue} vnd </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.tienthue.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Tiền cọc: </td>
							<td>${nhatro.tienCoc} vnd </td>
							<td><div class="basic circular ui icon button" onclick="$('.large.tiencoc.modal').modal('show');"><i class="teal edit icon"></i></div></td>
						</tr>
						<tr>
							<td>Điểm đánh giá: </td>
							<td>${nhatro.diem} <i class="yellow star icon"></i> </td>
							<td></td>
						</tr>
						<tr>
							<td>Ngày đăng: </td>
							<td>${nhatro.ngayThem} </td>
							<td></td>
						</tr>
						<tr>
							<td>Tình trạng: </td>
							<td><c:choose>
								<c:when test="${nhatro.tinhtrang==-1}">
									<div class="ui red tag label"> Vi phạm </div>
								</c:when>
								<c:when test="${nhatro.tinhtrang==0}">
									<div class="ui orange tag label"> Chờ duyệt </div>
								</c:when>
								<c:otherwise>
									<div class="ui teal tag label"> Đã duyệt </div>
								</c:otherwise>
							</c:choose></td>
							<td></td>
						</tr>
						<tr>
							<td><i class="users icon"></i> </td>
							<td>${nhatro.soLuot} đã thuê </td>
							<td></td>
						</tr>
						</tbody>
						</table>
						</div>
						<div class="extra">
						<div class="ui icon buttons"> 
							<a class="ui teal labeled icon button" onclick="show()">
								 Chỉnh sửa mô tả
				 				<i class="icon edit"></i>
				 			</a>
							<c:if test="${nhatro.tinhtrang==1}">
							<div class="ui orange labeled icon button" onclick="$('.ui.basic.an.modal').modal('show');">
				 				<i class="icon eye slash"></i>
				 				 Ẩn bài đăng khỏi hệ thống tìm kiếm 
							</div>
							</c:if>
							<c:if test="${nhatro.tinhtrang<1}">
							<a class="ui orange labeled icon button" tabindex="0" href="chutro/nhatro/xinkichhoat/${nhatro.id}.htm">
				 				<i class="icon eye"></i>
				 				 Xin kích hoạt lại bài đăng 
							</a>
							</c:if>
							<div class="ui red labeled icon button" tabindex="0" onclick="$('.ui.basic.xoa.modal').modal('show');">
				 				<i class="icon trash"></i>
				 				 Xóa 
							</div>
						</div>
						</div>
					</div>
					<br>
				</div>
				<br>
				<div id="show" style="display: block;">
					${nhatro.moTa}
				</div>
				<div id="hide" style="display: none;">
				<form action="chutro/nhatro/doimota.htm" onsubmit="return kt()" method="post">
					<textarea id="mota" name="mota" cols="100" rows="200" placeholder="Nội dung">${nhatro.moTa}</textarea>
					<br>
					<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</form>
				</div>
			<br>
			</div>
	</div>
	</div>
	<div class="ui grid"style="padding-left:20%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="fourteen wide column" style="background-color:white; border-radius: 5px;">
			<div class="ui comments" id="write-comment" style="padding-left: 10px;">
			<div class="ui dividing header">Đánh giá</div>
			<c:forEach var="comment" items="${nhatro.comment}" begin="0" end="10">
				<div class="comment">
					<a class="avatar"><img src="resources/images/avatar/${comment.khachthue.account.username}.png"></a>
					<div class="content">
						<a class="author" href="account/khachthue.htm?username=${comment.khachthue.account.username}">${comment.khachthue.account.username}</a>
						<div class="metadata">
							<span class="date">${comment.thoiGian}</span>
							<div class="rating"><i class="star icon"></i>${comment.diem}</div>
						</div>	
						<div class="text">${comment.comment}</div>
					</div>
				</div>
				<br>
			</c:forEach>
			</div>
		</div>
	</div>
	<div class="ui basic xoa modal">
		<div class="ui icon header">
		   <i class="red trash icon"></i>
		   XÓA BÀI ĐĂNG
  		</div>
  		<div class="content">
			<p>Bạn chắc chắn muốn xóa bài đăng này?</p>
			<p>Một khi bài đăng được xóa, rất nhiều thông tin liên quan sẽ bị thay đổi nghiêm trọng (lịch hẹn, comment đánh giá)</p>
		</div>
		<div class="actions">
		  <div class="ui black basic cancel inverted button">
		    Thoát
		  </div>
		  <a class="ui red ok inverted button" href="chutro/nhatro/xoa/${nhatro.id}.htm">
		    Xóa
		  </a>
		  </div>
	</div>
	<c:if test="${nhatro.tinhtrang==1}">
	<div class="ui basic an modal">
		<div class="ui icon header">
		   <i class="orange eye slash icon"></i>
		   ẨN BÀI ĐĂNG
  		</div>
  		<div class="content">
			<p>Bạn thực sự muốn ẩn bài đăng này? Sau khi ẩn, chỉ có admin mới có quyền kích hoạt lại bài đăng</p>
		</div>
		<div class="actions">
		  <div class="ui black basic cancel inverted button">
		    Thoát
		  </div>
		  <a class="ui orange ok inverted button" href="chutro/nhatro/an/${nhatro.id}.htm">
		    Ẩn
		  </a>
		  </div>
	</div>
	</c:if>
	<div class="ui large diachi modal">
		<div class="header">Chỉnh sửa địa chỉ</div>
		<div class="content">
		<form action="chutro/nhatro/doidiachi.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Số nhà: </label>
					<input class="ui input" type="text" name="diachi" value="${nhatro.diachi.diaChi}">
				</div>
				<div class="three fields">
					<div class="field">
						<label> Tỉnh/Thành phố </label> 
						<select class="fluid ui search selection dropdown" id="comboboxProvince" name="province" required="required">
							<option disabled="disabled">--Chọn tỉnh--</option>
							<c:forEach var="p" items="${provinces}">
								<option value="${p.id}">${p.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="field">
						<label> Quận/Huyện </label> 
						<select class="fluid ui search selection dropdown" id="comboboxDistrict" name="district" required="required">
							<option disabled="disabled">--Chọn huyện--</option></select>
					</div>
					<div class="field">
						<label>Xã/Phường</label> 
						<select class="fluid ui search selection dropdown" id="comboboxWard" name="ward" required="required">
							<option disabled="disabled">--Chọn xã--</option></select>
					</div>
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}" required="required">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large dientich modal">
		<div class="header"> Chỉnh sửa diện tích </div>
		<div class="content">
		<form action="chutro/nhatro/doidientich.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>diện tích: </label>
					<input class="ui input" type="text" name="dienTich" value="${nhatro.dienTich}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large songuoitrenphong modal">
		<div class="header"> Chỉnh sửa số người/phòng </div>
		<div class="content">
		<form action="chutro/nhatro/doisonguoitrenphong.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Số người/phòng: </label>
					<input class="ui input" type="number" name="soNguoiTrenPhong" value="${nhatro.soNguoiTrenPhong}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large sophongchothue modal">
		<div class="header"> Chỉnh sửa Tổng số phòng </div>
		<div class="content">
		<form action="chutro/nhatro/doisophongchothue.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Tổng số phòng: </label>
					<input class="ui input" type="number" name="soPhongChoThue" value="${nhatro.soPhongChoThue}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large sophongcosan modal">
		<div class="header"> Chỉnh sửa số phòng cho thuê </div>
		<div class="content">
		<form action="chutro/nhatro/doisophongcosan.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Tổng số phòng: </label>
					<input class="ui input" type="number" name="soPhongCoSan" value="${nhatro.soPhongCoSan}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large tiencoc modal">
		<div class="header"> Chỉnh sửa tiền cọc </div>
		<div class="content">
		<form action="chutro/nhatro/doitiencoc.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Tiền cọc: </label>
					<input class="ui input" type="text" name="tienCoc" value="${nhatro.tienCoc}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large tienthue modal">
		<div class="header"> Chỉnh sửa Giá thuê </div>
		<div class="content">
		<form action="chutro/nhatro/doitienthue.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Giá thuê: </label>
					<input class="ui input" type="text" name="tienThue" value="${nhatro.tienThue}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<div class="ui large tieude modal">
		<div class="header"> Chỉnh sửa Tiêu đề </div>
		<div class="content">
		<form action="chutro/nhatro/doitieude.htm" method="post" onsubmit="return kt()">
			<div class="ui form">
				<div class="field">
					<label>Tiêu đề: </label>
					<input class="ui input" type="text" name="tieuDe" value="${nhatro.tieuDe}">
				</div>
				<div class="field">
				<input type="hidden" name="id" value="${nhatro.id}">
					<button type="submit" class="ui positive right labeled icon button">
						Lưu thay đổi
						<i class="checkmark icon"></i>
					</button>
				</div>
			</div>
		</form>
		</div>
	</div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({on : 'hover'});
		$('.star.rating').rating({initialRating: 5, maxRating: 5}).rating('enable');
		$(':radio').change(function() {console.log('New star rating: ' + this.value);});
		$('.ui.pointing.dientich.dropdown').dropdown();
		var ckeditor=CKEDITOR.replace('mota',{
			height: '700px',
		});
		CKFinder.setupCKEditor(ckeditor,'${pageContext.request.contextPath}/resources/ckfinder/');
		
		function show(){
			var x = document.getElementById("hide");
			var y = document.getElementById("show");
			  if (x.style.display === "none") {
			    x.style.display = "block";
			    y.style.display = "none";
			  } else {
			    x.style.display = "none";
			    y.style.display = "block";
			  }
		}
		
		
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
		return $('.ui.form').form('is valid')
	};
	</script>
</body>
</html>