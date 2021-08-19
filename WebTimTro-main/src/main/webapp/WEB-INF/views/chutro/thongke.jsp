<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>
<script>

</script>
<body>
	<!-- Page Contents -->
	<div class="ui grid" style="margin-top: 17px; margin-left:2%; margin-right:2%; border-radius: 5px; color: #00ffff; border: 1px pink solid">
	<div class="row">
	<h2 style="padding-left: 40%; color: orange"> Trang thống kê </h2>
	<form class="right floated right aligned six wide column" action="chutro/thongkes.htm">
		<label> Ngày đầu </label>
		<input type="date" id="begin" name="begin"class="ui input" value="${begin}">
		<span> - </span>
		<label> Ngày đích </label>
		<input type="date" id="end" name="end" class="ui input" value="${end}">
		<span></span>
		<button type="submit" class="ui orange button"> Thống kê </button>
	</form>
	</div>
	<br>
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
<!-- 	<script src="resources/js/canvasjs.min.js"></script>
	<script src="resources/js/jquery.canvasjs.min.js"></script> -->
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
		        	strokeWidth: 5},
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
</body>
</html>