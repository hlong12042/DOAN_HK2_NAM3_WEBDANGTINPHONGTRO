<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid">		
		<div class="right floated thirteen wide computer sixteen wide phone column" id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div class="fifteen wide computer sixteen wide phone centered column">
						<h2> <a href="admin/thongke.htm"> 
						<i class="chart area icon"></i> Thống kê </a> </h2>
						<div class="ui divider"></div>
						<form class="right aligned ten wide computer sixteen wide phone centered column" action="admin/thongke.htm">
						<div class="ui form" style="position: relative;">
						<div class="fluid inline field">
							<b> Ngày đầu </b>
							<input type="date" id="begin" name="begin" class="ui input" value="${begin}">
							<span> - </span>
							<b> Ngày đích </b>
							<input type="date" id="end" name="end" class="ui input" value="${end}">
							<span> - </span>
							<button type="submit" class="ui orange button"> Thống kê </button>
						</div>
						</div>
						</form>
						<div class="ui grid">
							<div class="ten wide computer sixteen wide phone centered column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
				<div id="chart_div" class="twelve wide computer twelve wide phone centered column"></div>
				</div>
				<br>
				<div class="row">
				<div id="chart_div1" class="twelve wide computer twelve wide phone centered column"></div>
				</div>
			</div>
		</div>
	</div>
	
</body>
<script src="resources/js/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawChart);
	
	function drawChart() {
		var table=[]; 
		var head=[];
		<c:forEach items="${taikhoans[0]}" var="head">
		head.push('${head}');
		</c:forEach>
		table.push(head);
		<c:forEach items="${taikhoans}" var="row" begin="1">
		while (true){
			var row = [];
			row.push('${row[0]}');
			<c:forEach items="${row}" var="column" begin="1">
			row.push(${column});
			</c:forEach>
			table.push(row);
			break;
		}
		</c:forEach>
		var table1=[]; 
		var head1=[];
		<c:forEach items="${nhatros[0]}" var="head">
		head1.push('${head}');
		</c:forEach>
		table1.push(head1);
		<c:forEach items="${nhatros}" var="row" begin="1">
		while (true){
			var row = [];
			row.push('${row[0]}');
			<c:forEach items="${row}" var="column" begin="1">
			row.push(${column});
			</c:forEach>
			table1.push(row);
			break;
		}
		</c:forEach>
		var data = google.visualization.arrayToDataTable(table);
		var data1 = google.visualization.arrayToDataTable(table1);
	 	var options = {
	   		title: 'THỐNG KÊ SỐ LƯỢNG TÀI KHOẢN ĐƯỢC TẠO TRONG THÁNG',
	   		titleTextStyle: {
	   		    color: '#FF0000',
	   			fontSize: 20,
	   	  		bold: true,
	   		},
	   		hAxis: {title: 'Tháng',  
	        	titleTextStyle: {color: 'white', bold: true,},
	        	textStyle: {color: 'white',},
	        },
	        vAxis: {title: 'Tài khoản',  
	        	titleTextStyle: {color: 'white', bold: true,}, 
	        	minValue: 0,
	        	textStyle: {color: 'white',},
	        },
	        legend: {
	        	textStyle: {color: 'white', fontName: 'Calibri'},
	        },
	    	animation:{
	    		"startup": true,
	            duration: 1000,
	        	easing: 'out',
	        },
	        backgroundColor: {fill: 'black', 
		       	stroke: 'purple', 
		       	strokeWidth: 5
		    },
	  	};
	 	var options1 = {
		   		title: 'THỐNG KÊ SỐ LƯỢNG BÀI ĐĂNG ĐƯỢC TẠO TRONG THÁNG',
		   		titleTextStyle: {
		   		    color: '#FF0000',
		   			fontSize: 20,
		   	  		bold: true,
		   		},
		   		hAxis: {title: 'Tháng',  
		        	titleTextStyle: {color: 'white', bold: true,},
		        	textStyle: {color: 'white',},
		        },
		        vAxis: {title: 'Tài khoản',  
		        	titleTextStyle: {color: 'white', bold: true,}, 
		        	minValue: 0,
		        	textStyle: {color: 'white',},
		        },
		        legend: {
		        	textStyle: {color: 'white', fontName: 'Calibri'},
		        },
		    	animation:{
		    		"startup": true,
		            duration: 1000,
		        	easing: 'out',
		        },
		        backgroundColor: {fill: 'black', 
			       	stroke: 'purple', 
			       	strokeWidth: 5
			    },
		  	};
	  	var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
	  	var chart1 = new google.visualization.AreaChart(document.getElementById('chart_div1'));
	  	chart.draw(data, options);
	  	chart1.draw(data1, options1);
	}
</script>
<jsp:include page="footer.jsp"></jsp:include>
</html>