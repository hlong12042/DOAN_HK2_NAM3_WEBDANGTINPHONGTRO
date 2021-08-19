<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp"></jsp:include>
	<div class="ui grid" style='background: url(resources/images/background/background.png) repeat;'>
		<div class="right floated thirteen wide computer sixteen wide phone column" id="content">
			<div class="ui container grid" style="width: 100%; margin-top: 5px">
				<div class="row">
					<div class="fifteen wide computer sixteen wide phone centered column">
						<h2> <a href="admin/thongkechutro.htm?chutro=${chutro}"> 
						<i class="chart area icon"></i> Thống kê </a> </h2>
						<div class="ui divider"></div>
						<form class="right aligned ten wide computer sixteen wide phone centered column" action="admin/thongkechutro.htm">
						<div class="ui form">
						<div class="fluid inline field">
							<b> Chủ trọ </b>
							<select name="chutro" class="ui search dropdown">
							<c:forEach items="${chutros}" var="ct">
								<option value="${ct.id}" label="${ct.account.username}" ${chutro==ct.id?'selected':''}/>
							</c:forEach>
							</select>
							<span> - </span>
							<b> Ngày đầu </b>
							<input type="date" id="begin" name="begin" class="ui input" value="${begin}" class="ui input">
							<span> - </span>
							<b> Ngày đích </b>
							<input type="date" id="end" name="end" class="ui input" value="${end}" class="ui input">
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
					<div class="twelve wide column" style="padding-left: 15%">
						<div id="chart_div" style="width: 100%; height: 500px; position: relative;"></div>
						<br>
						<c:forEach items="${pie}" var="dong">
							<div id="piechart_3d_${dong[5]}" style="width: 100%; height: 500px; position: relative;"></div>
						<br>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
<script src="resources/js/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {'packages':['corechart']});
	google.charts.setOnLoadCallback(drawLineChart);
	function drawLineChart() {
		var datas = [];
		var head = [];
		<c:forEach items="${data[0]}" var="cot">
			head.push("${cot}");
		</c:forEach>
		datas.push(head);
		
		<c:forEach items="${data}" var="dong" begin="1">
		while (true){
			var row = [];
			row.push("${dong[0]}");
				<c:forEach items="${dong}" var="cot" begin="1">
					row.push(parseFloat("${cot}"));
				</c:forEach>
			datas.push(row);
			break;
		}
		</c:forEach>
		var data = google.visualization.arrayToDataTable(datas);
	    var options = {
	    	title: 'THỐNG KÊ ĐIỂM NHÀ TRỌ',
	    	titleTextStyle: {
	   		    color: '#FF0000',
	   		 	fontName: 'Calibri',
	   			fontSize: 25,
	   	  		bold: true,
	   		},
	        hAxis: {title: 'Tháng',  
	        	titleTextStyle: {color: 'white', bold: true, fontName: 'Calibri', fontSize: 20},
	        	textStyle: {color: 'white', fontName: 'Calibri'},
	        },
	        vAxis: {title: 'Điểm',  
	        	titleTextStyle: {color: 'white', bold: true, fontName: 'Calibri', fontSize: 20}, 
	        	minValue: 0,
	        	textStyle: {color: 'white', fontName: 'Calibri'},
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
	chart.draw(data, options);
	}
	<c:forEach items="${pie}" var="dong">
		google.charts.setOnLoadCallback(drawPieChart${dong[5]});
		function drawPieChart${dong[5]}() {
	        var data = google.visualization.arrayToDataTable([
	          ['Lịch hẹn', 'Số lượt'],
	          ['Đồng ý',     ${dong[1]}],
	          ['Thành Công',      ${dong[2]}],
	          ['Thất bại',  ${dong[3]}],
	          ['Chưa thành công', ${dong[4]}]
	        ]);
	
	        var options = {
	          	title: 'Thống kê lịch hẹn của ${dong[0]}',
	          	pieHole: 0.4,
	          	titleTextStyle: {
		   		    color: '#FF0000',
		   			fontSize: 20,
		   	  		bold: true,
		   		},
		   		legend: {
		        	textStyle: {color: 'white'},
		        },
		   		animation:{
		    		"startup": true,
		            duration: 1000,
		            easing: 'out',
		        },
		        backgroundColor: {fill: 'black', 
		        	stroke: 'purple', 
		        	strokeWidth: 5},
	        };
	
	        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d_${dong[5]}'));
	        chart.draw(data, options);
	      }
	</c:forEach>
</script>
<jsp:include page="footer.jsp"></jsp:include>
</html>