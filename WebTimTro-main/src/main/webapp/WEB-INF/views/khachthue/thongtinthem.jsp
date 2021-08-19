<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="ptithcm.entity.Province"%>
<%@page import="ptithcm.entity.Truong"%>
<%@page import="ptithcm.entity.Account"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<jsp:include page="header.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<body style='background: url("resources/images/background/background(1).png"); background-size: cover;'>
	<!-- Page Contents -->

		<div class="ui card" style="margin-left: 2%; position: fixed; border-radius: 10%">
			<div class="ui large image">
				<img src="resources/images/avatar/${sessionScope['username']}.png" style="border-radius: 10%" alt="">
			</div>
			<div class="content">
				<a class="header" href="account/${user.username}.htm">${user.username}</a>
				<div class="meta">
				<i class="user icon"></i>
				${user.hoTen}</div>
				<div class="meta">
				<i class="calendar alternate outline icon"></i>
				Tham gia: ${user.ngayDangKy}</div>
			</div>
		</div>
		<div class="ui grid" style="margin-left: 20%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column" style="background-color: white; border-radius: 5px">
			<h2 style="text-align: center; color: red; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black;">Thông tin thêm của bạn</h2>
			<table class="ui table" >
				<tbody>
					<tr>
						<td><h4>Họ tên: </h4></td>
						<td>${user.hoTen}</td>
						<td></td>
					</tr>
					<tr>
						<td><h4>Nơi bạn đang hoặc dự định sinh sống: </h4></td>
						<td>${user.khachThue.truong.province.name}</td>
						<td></td>
					</tr>
					<tr>
						<td><h4>Trường bạn theo học: </h4></td>
						<td>${user.khachThue.truong.ten}</td>
						<td></td>
					</tr>
					<tr>
						<td><h4>Năm Sinh: </h4></td>
						<td>${user.khachThue.namSinh}</td>
						<td></td>
					</tr>
					<tr>
						<td><h4>Giới tính: </h4></td>
						<td>${user.khachThue.gioiTinh?'Nữ':'Nam'}</td>
						<td></td>
					</tr>
					<tr>
						<td><h4>Quê của bạn: </h4></td>
						<td>${user.khachThue.queQuan}</td>
						<td></td>
					</tr>
				</tbody>
			</table>
			<div class="ui basic animated button" tabindex="0" onclick="$('.thongtinthem.modal').modal('show');">
  				<div class="visible content"> Chỉnh sửa </div>
 				<div class="hidden content">
 				<i class="icon teal edit"></i>
 				</div>
			</div>
			<div class="ui thongtinthem modal">
				<div class="header">Chỉnh sửa thông tin thêm</div>
				<div class="content">
				<form action="khachthue/thongtinthem.htm" method="post">
					<div class="ui form">
						<div class="two fields">
							<div class="field">
							<label>Nơi bạn đang hoặc dự định sinh sống: </label>
							<select class="ui search selection dropdown" id="comboboxProvince" name="province">
								<c:forEach var="p" items="${provinces}">
									<option  value="${p.id}" ${p.name==user.khachThue.truong.province.name?'selected':''}>${p.name}</option>
								</c:forEach>
							</select>
							</div>
							<div class="field">
							<label>Trường bạn theo học: </label>
							<select class="ui search selection dropdown" id="comboboxTruong" name="truong"></select>
							</div>
						</div>
						<div class="field">
							<label>Quê của bạn</label>
							<select class="ui search selection dropdown" id="comboboxQue" name="que">
								<c:forEach var="q" items="${ques}">
									<option value="${q.name}" ${q.name==user.khachThue.queQuan?'selected':''}>${q.name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="two fields">
							<div class="field">
							<label>Năm sinh: </label>
							<select class="ui fluid dropdown" name="namsinh">
								<c:forEach var="y" begin="1980" end="2020">
									<option value="${y}" ${user.khachThue.namSinh==y?'selected':''}>${y}</option>
								</c:forEach>
							</select>
							</div>
							<div class="field">
							<label>Giới tính: </label>
							<select class="ui fluid dropdown" name="gioitinh">
								<option value="0">Nam</option>
								<option value="1" ${user.khachThue.gioiTinh?'selected':''}>Nữ</option>
							</select>
							</div>
						</div>
						<div class="field">
							<button type="submit" class="ui positive right labeled icon button">
								Lưu thay đổi
								<i class="checkmark icon"></i>
							</button>
						</div>
						</div>
					</form>
						<div></div>	
					</div>
					
				</div>
			</div>
			
		</div>
	<script type="text/javascript">
		
		let selected_province = "$('#comboboxProvince').find('option:selected')";
		let selected_truong = '';
		let selected_que = "$('#comboboxQue').find('option:selected')";
		let list_provinces = [];
		let list_ques = [];

		init();
		document.getElementById("comboboxProvince").selectedIndex = "0";
		document.getElementById("comboboxQue").selectedIndex = "0";

		$('#comboboxProvince').change(function () {
		    var optionSelected = $(this).find("option:selected");
		    var valueSelected  = optionSelected.val();
		    var textSelected   = optionSelected.text();

		    selected_province = list_provinces.find((p)=>{
		    	return p.id == valueSelected;
		    })
		    console.log(selected_province);
		    let htmlTruongs = '<option disabled="disabled">--Chọn trường--</option>';
		    let truongs = selected_province.truongs;
		    for(let i = 0; i < truongs.length; i++) {
		    	htmlTruongs += '<option value="' + truongs[i].id + '" ${user.khachThue.truong.ten=='+ truongs[i].name +'?"selected":""}>' + truongs[i].name + '</option>';
			}
			$('#comboboxTruong').html(htmlTruongs);
			document.getElementById("comboboxTruong").selectedIndex = "0";
		    
		});
		
		$('#comboboxQue').change(function () {
			var optionSelected = $(this).find("option:selected");
			var valueSelected  = optionSelected.val();
		    var textSelected   = optionSelected.text();
		    selected_que = selected_que;
		    console.log(selected_que);
		})

		function init() {
			let province ={};
			let truong ={};
			let que = {};
			
			<%ArrayList<Province> provinces = (ArrayList) request.getAttribute("provinces");%>
			<%for (Province p : provinces) {%>
					province = {
							name : "<%=p.getName()%>",
							id : <%=p.getId()%>,
							truongs : []
					}
					
					<%List<Truong> truongs = (List) p.getTruongs();%>
					<%for (Truong t : truongs) {%>	
							truong = {
									name : "<%=t.getTen()%>",
									id : <%=t.getId()%>
							}
							province.truongs.push(truong);
						<%}%>
						list_provinces.push(province);
				<%}%>
					
			<%ArrayList<Province> ques = (ArrayList) request.getAttribute("ques");%>
			<%for (Province q : ques) {%>
					que = {
							name : "<%=q.getName()%>",
							id : <%=q.getId()%>,
					}
					list_ques.push(que);
			<%}%>
		}
	</script>
	
</body>
</html>